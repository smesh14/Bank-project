package egc.bankservice.service;

import egc.bankservice.entity.CardAuthInfo;
import egc.bankservice.entity.CardEntity;
import egc.bankservice.exception.BankException;
import egc.bankservice.model.*;
import egc.bankservice.repository.CardRepository;
import egc.bankservice.utils.PasswordHash;
import egc.bankservice.utils.RandomGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Slf4j
@Service
public class CardServiceBean implements CardService{

    @Autowired
    private final CardRepository cardRepository;

    @Autowired
    private AuthManager authManager;

    public CardServiceBean(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }


    @Override
    @Transactional
    public String addCard(AddCardRequest addCardRequest) {
        if(cardRepository.existsByCardNumber(addCardRequest.getCardNumber())) {
            log.info("card with number: "+addCardRequest.getCardNumber()+ "has already add");
            throw new BankException("card with number: "+addCardRequest.getCardNumber()+ "has already add");
        }
        String pin  = RandomGenerator.getRandomPin();
        CardEntity cardEntity = new CardEntity();
        cardEntity.setCardNumber(addCardRequest.getCardNumber());
        cardEntity.setAmount(addCardRequest.getAmount());
        cardEntity.setPin(PasswordHash.getMD5PasswordHash(pin));
        cardRepository.save(cardEntity);
        return pin;
    }

    @Override
    @Transactional
    public AuthResult authCard(AuthCardRequest authCard) {
        String cardNumber = authCard.getCardNumber();
        String pin = authCard.getPin();
        if(!cardRepository.existsByCardNumber(authCard.getCardNumber())) {
            log.info("card with number: "+ cardNumber + "is not valid");
            throw new BankException("card with number: "+ cardNumber + "is not valid");
        }
        CardEntity cardEntity = cardRepository.getByCardNumber(cardNumber);
        if(cardEntity.getAuthInfo().isBlocked()) {
            log.info("card is blocked");
            throw new BankException("card is blocked");
        }
        if(!passwordIsValid(cardEntity,pin)) {
            return handleInvalidPassword(cardEntity);
        }
        return getCardResponse(cardRepository.getByCardNumber(cardNumber),authCard);
    }


    private boolean passwordIsValid(CardEntity cardEntity, String pin) {
        return PasswordHash.validateMD5PasswordHash(cardEntity.getPin(),pin);
    }


    private AuthResult handleInvalidPassword(CardEntity cardEntity){
        AuthResult authResult = new AuthResult();
        log.info("pin is incorrect");

        // Update failedAttempts in db
        CardAuthInfo cardAuthInfo = cardEntity.getAuthInfo();
        int failedAttempts = cardAuthInfo.getFailedAttempts() + 1;
        cardAuthInfo.setFailedAttempts(failedAttempts);
        cardEntity.setAuthInfo(cardAuthInfo);
        cardRepository.save(cardEntity);

        //Construct error response
        AuthCardResponse addCardResponse = new AuthCardResponse();
        addCardResponse.setErrorMessage(ErrorMessage.PIN_IS_INCORRECT);
        authResult.setAuthCardResponse(addCardResponse);
        //Card will be blocked
        if(failedAttempts==3) {
            cardAuthInfo.setBlocked(true);
            cardEntity.setAuthInfo(cardAuthInfo);
            cardRepository.save(cardEntity);
            log.info("your card has been blocked");
            addCardResponse.setErrorMessage(ErrorMessage.CARD_IS_BLOCKED);
        }
        return  authResult;
    }


    private AuthResult getCardResponse(CardEntity cardEntity,AuthCardRequest authCard) {
        AuthResult authResult = new AuthResult();
        AuthCardResponse addCardResponse = new AuthCardResponse();
        addCardResponse.setCardNumber(cardEntity.getCardNumber());
        addCardResponse.setAmount(cardEntity.getAmount());
        addCardResponse.setId(cardEntity.getId());
        addCardResponse.setPin(cardEntity.getPin());
        addCardResponse.setBlocked(cardEntity.getAuthInfo().isBlocked());
        addCardResponse.setFailedAttempts(cardEntity.getAuthInfo().getFailedAttempts());

        authResult.setAuthCardResponse(addCardResponse);
        authResult.setJwt(authManager.createAccessToken(authCard));
        return authResult;
    }
}

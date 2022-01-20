package egc.bankservice.service;
import egc.bankservice.entity.CardEntity;
import egc.bankservice.entity.TransactionEntity;
import egc.bankservice.entity.TransactionType;
import egc.bankservice.exception.BankException;
import egc.bankservice.model.ErrorMessage;
import egc.bankservice.model.SuccessCode;
import egc.bankservice.model.TransactionResponse;
import egc.bankservice.repository.CardRepository;
import egc.bankservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class CardsOperationServiceBean implements CardsOperationService {

    @Autowired
    private AuthManager authManager;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public long checkBalance() {
        return cardRepository.getByCardNumber(authManager.getCardNumber()).getAmount();
    }

    @Override
    public TransactionResponse deposit(String cardNumber, long amount) {
        return doTransaction(cardNumber,amount,TransactionType.DEPOSIT);
    }

    @Override
    public TransactionResponse withdrawal(String cardNumber, long amount) {
        return doTransaction(cardNumber,amount, TransactionType.WITHDRAWAL);
    }

    private TransactionResponse doTransaction(String cardNumber, long amount, TransactionType type) {
        TransactionResponse transactionResponse = new TransactionResponse();
        CardEntity cardEntity = cardRepository.getByCardNumber(cardNumber);
        switch (type) {
            case WITHDRAWAL:
                withdrawal(amount,cardEntity,transactionResponse);
                break;
            case DEPOSIT:
                deposit(amount,cardEntity,transactionResponse);
                break;
            default: throw new BankException("invalid operation");
        }

        cardRepository.save(cardEntity);

        TransactionEntity transaction = new TransactionEntity();
        transaction.setTransactionType(type);
        transaction.setCard(cardRepository.save(cardEntity));
        transaction.setDate(new Date());
        transaction.setAmount(amount);
        transactionRepository.save(transaction);
        return transactionResponse;
    }

    private void withdrawal(long amount,CardEntity cardEntity,TransactionResponse transactionResponse){
        if(amount>=cardEntity.getAmount()){
            transactionResponse.setErrorMessage(ErrorMessage.NOT_ENOUGH_MONEY);
            transactionResponse.setCode(SuccessCode.ERROR);
        }else {
            transactionResponse.setCode(SuccessCode.OK);
            transactionResponse.setErrorMessage(ErrorMessage.SUCCESS);
            cardEntity.setAmount(cardEntity.getAmount() - amount);
        }
    }

    private void deposit(long amount, CardEntity cardEntity,TransactionResponse transactionResponse){
        transactionResponse.setCode(SuccessCode.OK);
        transactionResponse.setErrorMessage(ErrorMessage.SUCCESS);
        cardEntity.setAmount(cardEntity.getAmount() + amount);
    }

}

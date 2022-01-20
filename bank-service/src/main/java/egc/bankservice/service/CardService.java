package egc.bankservice.service;

import egc.bankservice.model.AddCardRequest;
import egc.bankservice.model.AuthCardResponse;
import egc.bankservice.model.AuthCardRequest;
import egc.bankservice.model.AuthResult;

public interface CardService {
    String addCard(AddCardRequest addCardRequest);

    AuthResult authCard(AuthCardRequest authCard);

}

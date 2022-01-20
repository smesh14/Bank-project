package egc.bankservice.controller;

import egc.bankservice.model.AddCardRequest;
import egc.bankservice.service.CardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Card Controller")
@RestController
public class CardControllerBean implements CardController{

    private final CardService cardService;

    public CardControllerBean(CardService cardService) {
        this.cardService = cardService;
    }

    @Override
    public String addCard(AddCardRequest addCardRequest) {
        return cardService.addCard(addCardRequest);
    }

}

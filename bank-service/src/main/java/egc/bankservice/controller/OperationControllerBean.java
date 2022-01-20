package egc.bankservice.controller;

import egc.bankservice.model.TransactionParam;
import egc.bankservice.model.TransactionResponse;
import egc.bankservice.service.CardsOperationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Operation Controller")
@RestController
public class OperationControllerBean implements OperationController{

    @Autowired
    private final CardsOperationService cardsOperationService;

    public OperationControllerBean(CardsOperationService cardsOperationService) {
        this.cardsOperationService = cardsOperationService;
    }


    @Override
    public long checkBalance() {
        return cardsOperationService.checkBalance();
    }

    @Override
    public TransactionResponse deposit(TransactionParam transactionParam) {
        return  cardsOperationService.deposit(transactionParam.getCardNumber(),transactionParam.getAmount());
    }

    @Override
    public TransactionResponse withdrawal(TransactionParam transactionParam) {
        return cardsOperationService.withdrawal(transactionParam.getCardNumber(),transactionParam.getAmount());
    }


}

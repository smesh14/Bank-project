package egc.bankservice.service;

import egc.bankservice.model.TransactionResponse;

public interface CardsOperationService {

    long checkBalance();

    TransactionResponse deposit(String cardNumber, long amount);

    TransactionResponse withdrawal(String cardNumber, long amount);
}

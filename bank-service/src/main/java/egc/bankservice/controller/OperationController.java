package egc.bankservice.controller;

import egc.bankservice.model.TransactionParam;
import egc.bankservice.model.TransactionResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@SecurityRequirement(name = "bearerAuth")
@RequestMapping(path="api/bank/operations")
public interface OperationController {

    @GetMapping("checkBalance")
    long checkBalance();

    @PostMapping("deposit")
    TransactionResponse deposit(@RequestBody TransactionParam transactionParam);

    @PostMapping("withdrawal")
    TransactionResponse withdrawal(@RequestBody TransactionParam transactionParam);


}

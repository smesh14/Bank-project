package egc.bankservice.controller;

import egc.bankservice.model.AddCardRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("addCard")
public interface CardController {

    @PostMapping("addCard")
    String addCard(@RequestBody AddCardRequest addCardRequest);

}

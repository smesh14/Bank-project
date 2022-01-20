package egc.bankservice.controller;

import egc.bankservice.model.AuthCardRequest;
import egc.bankservice.model.AuthResult;
import egc.bankservice.service.AuthManager;
import egc.bankservice.service.CardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth Controller")
@RestController
public class AuthControllerBean implements AuthController {

    @Autowired
    private  CardService cardService;



    @Override
    public ResponseEntity<AuthResult> auth(AuthCardRequest authCard) {
        return ResponseEntity.ok(cardService.authCard(authCard));
    }
}

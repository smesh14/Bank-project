package egc.bankservice.controller;

import egc.bankservice.model.AuthCardRequest;
import egc.bankservice.model.AuthResult;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Validated
@RequestMapping("auth")
public interface AuthController {
    @PostMapping("authCard")
    ResponseEntity<AuthResult> auth(@Valid @RequestBody AuthCardRequest authCard);
}

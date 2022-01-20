package egc.bankservice.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class AuthCardRequest {
    @NotBlank
    private String cardNumber;

    @NotBlank
    private String pin;
}

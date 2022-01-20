package egc.bankservice.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthCardResponse {
    private long id;

    private String CardNumber;

    private long amount;

    private String pin;

    private int failedAttempts;

    private boolean blocked;

    private ErrorMessage errorMessage;
}

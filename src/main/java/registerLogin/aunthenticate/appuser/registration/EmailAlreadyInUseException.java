package registerLogin.aunthenticate.appuser.registration;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public  class EmailAlreadyInUseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EmailAlreadyInUseException(String message) {
        super(message);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    public static EmailAlreadyInUseException emailAlreadyInUse(String email) {
        return new EmailAlreadyInUseException(String.format("Email address '%s' already in use", email));
    }
}
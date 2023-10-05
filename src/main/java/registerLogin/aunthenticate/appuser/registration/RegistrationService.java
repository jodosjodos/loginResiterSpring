package registerLogin.aunthenticate.appuser.registration;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import registerLogin.aunthenticate.appuser.AppUser;
import registerLogin.aunthenticate.appuser.AppUserRole;
import registerLogin.aunthenticate.appuser.AppUserService;
import registerLogin.aunthenticate.appuser.registration.token.ConfirmationToken;
import registerLogin.aunthenticate.appuser.registration.token.ConfirmationTokenService;
import registerLogin.aunthenticate.errors.*;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final EmailValidator emailValidator;
    private final AppUserService appUserService;
    private final ConfirmationTokenService confirmationTokenService;

    public String register(RegistrationRequest request) throws EmailNotValid, EmailAlreadyExist {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail) throw new EmailNotValid();
        var token = appUserService.signUpUser(new AppUser(request.getFirstName(), request.getLastName(), request.getEmail(), request.getPassword(), AppUserRole.USER

        ));
//
        return token;

    }

    @Transactional
    public String confirmToken(String token) throws TokenNotFoundException, EmailAlreadyConfirmedException, TokenExpiredException {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token).orElseThrow(TokenNotFoundException::new);
        if (confirmationToken.getConfirmedAt() != null) {
            throw new EmailAlreadyConfirmedException();
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new TokenExpiredException();
        }
        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(confirmationToken.getAppUser().getUsername());
        return "confirmed";
    }


}

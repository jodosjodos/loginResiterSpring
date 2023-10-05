package registerLogin.aunthenticate.appuser;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import registerLogin.aunthenticate.appuser.registration.token.ConfirmationToken;
import registerLogin.aunthenticate.appuser.registration.token.ConfirmationTokenService;
import registerLogin.aunthenticate.errors.EmailAlreadyExist;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
    private final static String USER_NOT_FOUND = " user with email %s not found";

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private  final ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository
                .findByEmail(email)
                .orElseThrow(
                        () ->
                                new UsernameNotFoundException(
                                        String.format(USER_NOT_FOUND, email)
                                )
                );
    }

    public String signUpUser(AppUser appUser) throws EmailAlreadyExist {

        boolean userExist = appUserRepository
                .findByEmail(appUser.getUsername())
                .isPresent();
        if (userExist) throw  new EmailAlreadyExist();
        String encodedPassword = bCryptPasswordEncoder
                .encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                appUser

        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        // TODO: 05/10/2023 send email 
        return token;
    }

    public int enableAppUser(String username) {
    return  appUserRepository.enableAppUser(username);
    }
}

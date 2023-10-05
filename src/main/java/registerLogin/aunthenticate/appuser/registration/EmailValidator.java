package registerLogin.aunthenticate.appuser.registration;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;
@Service
public class EmailValidator  implements Predicate<String> {
    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    @Override
    public boolean test(String email) {
//      regex
        return email != null && email.matches(EMAIL_REGEX);
    }
}

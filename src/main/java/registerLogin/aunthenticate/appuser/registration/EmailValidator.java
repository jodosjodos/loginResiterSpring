package registerLogin.aunthenticate.appuser.registration;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;
@Service
public class EmailValidator  implements Predicate<String> {
    @Override
    public boolean test(String s) {
//      regex
        return  true;
    }
}

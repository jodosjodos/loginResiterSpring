package registerLogin.aunthenticate.appuser.registration;

import lombok.RequiredArgsConstructor;
import registerLogin.aunthenticate.appuser.AppUser;
import registerLogin.aunthenticate.appuser.AppUserRepository;
@RequiredArgsConstructor
public class AuthenticationService {
    private  final AppUserRepository repository;

    public AppUser register(RegistrationRequest request){
        AppUser user = AppUser.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
         var savedUser =  repository.save(user);
        return savedUser;
    }
}

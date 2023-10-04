package registerLogin.aunthenticate.appuser.registration;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/registration")
@AllArgsConstructor
public class RegistrationController {
    private  RegistrationService registrationService;

//    @GetMapping(path = "/1")
//    public  String  draft(){
//        return  "jodos";
//    }
    @PostMapping("/")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request){
        return ResponseEntity.ok(registrationService.register(request));
    }
}
1
package registerLogin.aunthenticate.appuser.registration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.AllArgsConstructor;
import registerLogin.aunthenticate.errors.*;

@RestController
@RequestMapping(path = "/api/v1/registration")
@AllArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping()
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {
        try {
            String token = registrationService.register(request);
            return ResponseEntity.ok("Registration successful. Confirmation email sent. Token: " + token);
        } catch (EmailAlreadyExist e) {
            return ResponseEntity.badRequest().body("email already exists");
        }catch (EmailNotValid e){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid email , please provide valid email");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed");
        }
    }

    @GetMapping(path = "confirm")
    public ResponseEntity<?> confirm(@RequestParam("token") String token) {
        try {
            String result = registrationService.confirmToken(token);
            return ResponseEntity.ok(result);
        } catch (TokenNotFoundException e) {
            return ResponseEntity.badRequest().body("token not found");
        } catch (TokenExpiredException e) {
            return ResponseEntity.badRequest().body("Token has expired");
        } catch (EmailAlreadyConfirmedException e) {
            return ResponseEntity.badRequest().body("Email already confirmed");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Confirmation failed");
        }
    }
}

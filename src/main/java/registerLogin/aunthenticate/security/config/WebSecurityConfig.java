package registerLogin.aunthenticate.security.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import registerLogin.aunthenticate.appuser.AppUserService;

@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
@EnableWebSecurity

public class WebSecurityConfig {
    private final AppUserService appUserService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        XorCsrfTokenRequestAttributeHandler xor = new XorCsrfTokenRequestAttributeHandler();
        xor.setCsrfRequestAttributeName("_crsf");
        http

                .authorizeHttpRequests(
                        (authorize) ->
                                authorize
                                        .requestMatchers("/api/v1/registration/**")
                                        .permitAll()

                                        .anyRequest()
                                        .authenticated()

                ).formLogin(form->form.loginPage("/login"))
                .csrf(AbstractHttpConfigurer::disable).sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        return http.build();

    }


}

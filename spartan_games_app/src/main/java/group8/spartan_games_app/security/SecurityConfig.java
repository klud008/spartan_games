package group8.spartan_games_app.security;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disable CSRF for testing; enable in production with proper setup
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/sign-up", "/user/new-user", "/styles/**", "/js/**", "/images/**").permitAll() // Allow access to signup page and static resources
                        .requestMatchers("/user/admin/**", "/report/admin/**").hasAuthority("ADMIN")
                        .anyRequest().authenticated() // All other pages require normal authentication
                )
                .formLogin(form -> form
                        .loginPage("/login") // Custom login page URL
                        .loginProcessingUrl("/login") // The URL to submit the username and password
                        .defaultSuccessUrl("/games/all", true) // Redirect after successful login
                        .failureUrl("/login?error=true") // Redirect after login failure
                        .permitAll() // Allow access to the login page
                ).exceptionHandling((x) -> x.accessDeniedPage("/403"))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

package group8.spartan_games_app.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        String redirectURL = request.getContextPath();

        // Got from example Spring project provided by instructor. Kept for future reference for now.
        //if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
        //    redirectURL = "/ADMIN/users/all";
        //} else {
        //   redirectURL = "/articles/all";
        //}

        response.sendRedirect(redirectURL);
    }

}

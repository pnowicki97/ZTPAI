package nowicki.piotr.spring_boot_docker.config;
import jakarta.servlet.http.Cookie;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, HttpServletResponse response, Object handler) {
        String jwtToken = null;
        // Extract the JWT token from cookies
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("jwtToken".equals(cookie.getName())) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }
        }

        if (jwtToken != null) {
            // Attach the token to the request as a header (for downstream services)
            request.setAttribute("Authorization", "Bearer " + jwtToken);
        }

        return true; // Continue with the request
    }
}
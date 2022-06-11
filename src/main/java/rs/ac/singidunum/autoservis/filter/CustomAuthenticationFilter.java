package rs.ac.singidunum.autoservis.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import rs.ac.singidunum.autoservis.AppUtils;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

// Kontrolise login aplikacija
// Ugradjen sistem u Spring Boot-u
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager manager;

    public CustomAuthenticationFilter(AuthenticationManager manager) {
        this.manager = manager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Pokretanje procesa prijavljivanja korisnika
        // Interna logika poziva metod iz UserService#loadUserByUsernmae
        // takodje se cita hash passworda iz baze podataka
        return manager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    // Korisnikov username i password su pronadjeni u bazi pazi
    // Dobija dozvolu za generisanje JWT tokena
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication) throws IOException {
        // Kornsik u spring okruzenja
        User user = (User) authentication.getPrincipal();
        // Pribavlnja se instanca enkripcionog algoritma
        // U pitanju je HMAC 256bit algoritam
        Algorithm algorithm = Algorithm.HMAC256(AppUtils.CRYPTO_SECRET);

        // Builder JWT tokena
        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 100 * 60 * 1000)) // 100 minuta
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .sign(algorithm);
        String refreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 300 * 60 * 1000)) // 300 minuta
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);

        // Generisanje JSON odgovora
        // JSON u Javi se najbolje prikazuje kljuc vrednost parovima
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // Jako bitno kako bi se onemogucio CORS policy
        response.setHeader("Access-Control-Allow-Origin", "*");
        // Vris se ispis u output strean HTTP servlet respons-a
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}

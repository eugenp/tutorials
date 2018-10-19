package io.jsonwebtoken.jjwtfun.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.UUID;

public class JWTCsrfTokenRepository implements CsrfTokenRepository {

    private static final String DEFAULT_CSRF_TOKEN_ATTR_NAME = CSRFConfig.class.getName()
        .concat(".CSRF_TOKEN");

    private static final Logger log = LoggerFactory.getLogger(JWTCsrfTokenRepository.class);
    private byte[] secret;

    public JWTCsrfTokenRepository(byte[] secret) {
        this.secret = secret;
    }

    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        String id = UUID.randomUUID()
            .toString()
            .replace("-", "");

        Date now = new Date();
        Date exp = new Date(System.currentTimeMillis() + (1000 * 30)); // 30 seconds

        String token = Jwts.builder()
            .setId(id)
            .setIssuedAt(now)
            .setNotBefore(now)
            .setExpiration(exp)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact();

        return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", token);
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        if (token == null) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.removeAttribute(DEFAULT_CSRF_TOKEN_ATTR_NAME);
            }
        } else {
            HttpSession session = request.getSession();
            session.setAttribute(DEFAULT_CSRF_TOKEN_ATTR_NAME, token);
        }
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || "GET".equals(request.getMethod())) {
            return null;
        }
        return (CsrfToken) session.getAttribute(DEFAULT_CSRF_TOKEN_ATTR_NAME);
    }
}

package com.example.demo.user.Utils;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.example.demo.user.Model.*;

@Component
public class JwtUtil {


	private String secret_key;
	private long accessTokenValidity;
	private final JwtParser jwtParser;

	private final String TOKEN_HEADER = "Authorization";
	private final String TOKEN_PREFIX = "Bearer ";

	public JwtUtil(@Value("${security.jwt.secret-key}") String secret_key,
			@Value("${security.jwt.expiration-time}") Long accessTokenValidity) {
		this.accessTokenValidity = accessTokenValidity;
		this.secret_key = secret_key;
		this.jwtParser = Jwts.parser().setSigningKey(secret_key);
	}

	public String createToken(User user) {
		Claims claims = Jwts.claims().setSubject(user.getEmail());
		claims.put("firstName", user.getFirstName());
		claims.put("lastName", user.getLastName());
		Date tokenCreateTime = new Date();
		Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(accessTokenValidity));
		return Jwts.builder().setClaims(claims).setExpiration(tokenValidity)
				.signWith(SignatureAlgorithm.HS512, secret_key).compact();
	}

	public Claims parseJwtClaims(String token) {
		return jwtParser.parseClaimsJws(token).getBody();
	}

	public Claims resolveClaims(HttpServletRequest req) {
		try {
			String token = resolveToken(req);
			if (token != null) {
				return parseJwtClaims(token);
			}
			return null;
		} catch (ExpiredJwtException ex) {
			req.setAttribute("expired", ex.getMessage());
			throw ex;
		} catch (Exception ex) {
			req.setAttribute("invalid", ex.getMessage());
			throw ex;
		}
	}

	public String resolveToken(HttpServletRequest request) {

		String bearerToken = request.getHeader(TOKEN_HEADER);
		if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
			return bearerToken.substring(TOKEN_PREFIX.length());
		}
		return null;
	}

	public boolean validateClaims(Claims claims) throws AuthenticationException {
		try {
			return claims.getExpiration().before(new Date());
		} catch (Exception e) {
			throw e;
		}
	}

	public String getEmail(Claims claims) {
		return claims.getSubject();
	}

	private List<String> getRoles(Claims claims) {
		return (List<String>) claims.get("roles");
	}
	
    public boolean isTokenValid(Claims claims, UserDetails userDetails) {
        final String username = getEmail(claims);
        return (username.equals(userDetails.getUsername())) && !validateClaims(claims);
    }

}
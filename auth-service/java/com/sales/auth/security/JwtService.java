package com.sales.auth.security;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	@Value("${jwt.secret}")
	private String SECRET_KEY;
	
	
	private static final long EXPIRATION_TIME = 60*60*1000;
	
	private Key getSignInKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
	}
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public<T>T extractClaim(String toke, Function<Claims, T>claimsResolver){
		final Claims claims = extractAllClaims(toke);
		return claimsResolver.apply(claims);
	}
	
	
	private Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	public String generateToken(Map<String, Object>extraClaims,String username) {
		return Jwts.builder()
				.setClaims(extraClaims)
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ EXPIRATION_TIME))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
				
	}
	
	
	public boolean isTokenValid(String token, String username) {
		final String extractedUsername = extractUsername(token);
		return (extractedUsername.equals(username) && !isTokenExpired(token));
	}
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marcosinocencio.cursomc.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author Vinicius
 */

@Component
public class JWTUtil {
    
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration}")
    private long expiration;
    
    public String generateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }
    
    public boolean tokenValido(String token){
        Claims claims = getClaims(token);
        if(claims != null ){
            String username = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());
            
            if(username != null && expirationDate != null && now.before(expirationDate)){
                return true;
            }            
        }
        return false;
    }    

    private Claims getClaims(String token) {
        try{
            return Jwts.parser()
                    .setSigningKey(secret.getBytes())
                    .parseClaimsJws(token)
                    .getBody();        
        }
        catch(Exception e){
            return null;
        }
    }
    
    public String getUsername(String token){
        Claims claims = getClaims(token);
        if(claims != null ){
            return claims.getSubject();
        }
        return null;
    }
}

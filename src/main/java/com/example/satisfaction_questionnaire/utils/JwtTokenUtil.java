package com.example.satisfaction_questionnaire.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtTokenUtil {
  public String createToken(String userId, String authority) {
    return Jwts.builder()
      .setSubject("FC_SECKILL_USER")
      .setExpiration(new Date(System.currentTimeMillis() + 86400000000L))
      .claim("userId", userId)
      .claim("authority", authority)
      .signWith(SignatureAlgorithm.HS512, getKeyInstance())
      .compact();
  }
  
  private Key getKeyInstance() {
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    byte[] bytes = DatatypeConverter.parseBase64Binary("csust");
    return new SecretKeySpec(bytes, signatureAlgorithm.getJcaName());
  }
  
  public boolean checkToken(String token) {
    if (StringUtils.isEmpty(token))
      return false; 
    try {
      Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(token);
      return 
        !getClaims(token).getExpiration().before(new Date());
    } catch (Exception e) {
      return false;
    } 
  }
  
  private Claims getClaims(String token) {
    Jws<Claims> claimsJws = Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(token);
    return (Claims)claimsJws.getBody();
  }
  
  public String getUserId(String token) {
    return getClaims(token).get("userId").toString();
  }
  
  public String getAuthority(String token) {
    return (String)getClaims(token).get("authority");
  }
}

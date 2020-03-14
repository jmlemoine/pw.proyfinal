package modelo.EntityServices.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class TokenService {

    public static String createJWT(String id, String issuer, String subject, long ttlMillis) {

        //El algoritmo de firma JWT que usaremos para firmar el token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //Firmaremos nuestro JWT con nuestro secreto ApiKey
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary("2129");
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Vamos a establecer las reclamaciones JWT
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        //Si se ha especificado, agreguemos la caducidad
        if (ttlMillis > 0){
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Construye el JWT y lo serializa en un string compacta y segura para URL
        return builder.compact();

    }

    public static Claims decodeJWT(String jwt){

        //Esta línea arrojará una excepción si no es un JWS firmado como se esperaba.
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary("2129"))
                .parseClaimsJws(jwt).getBody();
        return claims;

    }

}

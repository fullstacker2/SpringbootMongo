package com.example.springbootmongo.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class TokenService {
    public static final String token_secret="df9hsdDSF324sa1fs6da"; // random string for token_secret

    // creating a token
    public  String createToken(ObjectId userId){
        try{
            Algorithm algo=Algorithm.HMAC256(token_secret); //HMAC256 is the algo to create the token
            String token= JWT.create().withClaim("userId", userId.toString()).
                    withClaim("createdAt", new Date()).sign(algo);
            //adding claim to the token, while decoding we can do getClaim
            return token;
        }catch (UnsupportedEncodingException | JWTCreationException e){
            e.printStackTrace();
        }
        return null;
    }

    //decoding the token
    public String getUserIdToken(String token){
        try {
            Algorithm algo=Algorithm.HMAC256(token_secret);

            // building te verifier with the HMAC256 algorithm
            JWTVerifier jwtVerifier = JWT.require(algo).build();

            // decoding the token using verifier
            DecodedJWT decodedJWT = jwtVerifier.verify(token);

            //using getClaim to get userId
            return  decodedJWT.getClaim("userId").asString();
        }catch (UnsupportedEncodingException |JWTCreationException e){
            e.printStackTrace();
        }
        return null;
    }

    //to check if token is valid
    public boolean isTokenValid(String token){
        String userId=this.getUserIdToken(token);
        return userId !=null; // true if userID is not null
    }
}
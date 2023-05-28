package com.haybble.ropc.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service


import java.util.*

@Service
 class TokenManager {
     val SECRET_KEY = "secret_is_not_yet_Key_Until_it_is_secret"
     val expiration = 6000000

    fun extractClaims(token: String) :Claims = Jwts.parser().setSigningKey(SECRET_KEY.toByteArray()).parseClaimsJwt(token).body


    fun extractUsername(token:String):String=extractClaims(token).subject

    fun getExpiration(token: String): Date =  extractClaims(token).expiration

    fun createToken(username:String): String =
        Jwts.builder()
        .setSubject(username)
        .setExpiration(Date(System.currentTimeMillis() + expiration))
        .signWith(SignatureAlgorithm.HS256,SECRET_KEY.toByteArray()).compact()

    fun validateToken(token: String,userDetails:UserDetails):Boolean{
        val username = extractUsername(token)
        return(username==(userDetails.username) && !isTokenExpired(token))
    }

     fun isTokenExpired(token: String): Boolean {
     return   getExpiration(token).before(Date(System.currentTimeMillis()))
    }


}
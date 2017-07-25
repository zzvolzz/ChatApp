/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chat.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author hoangnh
 */
public class TokenValidate {
    
    public String GenerateToken(String subject, String name, String scope) throws ParseException, UnsupportedEncodingException{
        String jwt = "";

        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(new Date()); // sets calendar time/date
        cal.add(Calendar.HOUR_OF_DAY, Common.hour); // adds one hour
        cal.getTime();
        Date date = cal.getTime();

        jwt = Jwts.builder()
            .setSubject(subject)
            .setExpiration(date)
            .claim("name", name)
            .claim("scope", scope)
            .signWith(
              SignatureAlgorithm.HS256,
              Common.private_key.getBytes("UTF-8")
        )
        .compact();
        return jwt;
    }
    
    public Boolean CheckToken(String jwt, String subject) throws UnsupportedEncodingException{
        Boolean status = true;
        Jws<Claims> claims = Jwts.parser()
            .setSigningKey(Common.private_key.getBytes("UTF-8"))
            .parseClaimsJws(jwt);
        System.out.print(claims);
        if(subject.equals(claims.getBody().get("sub"))){
            status = true;
        }
        return status;
    }
    
}

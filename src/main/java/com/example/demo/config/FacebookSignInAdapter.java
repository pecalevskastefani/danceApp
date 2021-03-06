package com.example.demo.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Arrays;

//koga ke se logiraat so fb site ovie useri ke imaat role= facebook_user
public class FacebookSignInAdapter implements SignInAdapter {
    @Override
    public String signIn(
            String localUserId,
            Connection<?> connection,
            NativeWebRequest request) {

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        connection.getDisplayName(), null,
                        Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
        return null;
    }
}
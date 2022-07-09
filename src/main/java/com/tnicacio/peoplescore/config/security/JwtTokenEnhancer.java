package com.tnicacio.peoplescore.config.security;

import com.tnicacio.peoplescore.user.model.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class JwtTokenEnhancer implements TokenEnhancer {

    private final UserDetailsService userDetailsService;

    public JwtTokenEnhancer(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        final UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userPrincipal.getUsername());
        final Map<String, Object> additionalInformationMap = new HashMap<>();
        additionalInformationMap.put("userName", userDetails.getUsername());
        additionalInformationMap.put("roles", userDetails.getAuthorities().stream()
                .map(Objects::toString)
                .collect(Collectors.joining(",")));

        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) accessToken;
        token.setAdditionalInformation(additionalInformationMap);

        return accessToken;
    }
}

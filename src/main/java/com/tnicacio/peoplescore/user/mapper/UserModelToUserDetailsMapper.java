package com.tnicacio.peoplescore.user.mapper;

import com.tnicacio.peoplescore.user.model.UserDetailsImpl;
import com.tnicacio.peoplescore.user.model.UserModel;
import com.tnicacio.peoplescore.util.mapper.Mapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserModelToUserDetailsMapper implements Mapper<UserModel, UserDetails> {

    @Override
    public UserDetails mapNonNull(UserModel model) {
        final List<GrantedAuthority> authorities = model.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toList());
        return new UserDetailsImpl(model.getId(),
                model.getUsername(),
                model.getPassword(),
                authorities);
    }

}

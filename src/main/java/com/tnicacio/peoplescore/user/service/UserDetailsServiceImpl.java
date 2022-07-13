package com.tnicacio.peoplescore.user.service;

import com.tnicacio.peoplescore.exception.factory.ExceptionFactory;
import com.tnicacio.peoplescore.user.model.UserModel;
import com.tnicacio.peoplescore.user.repository.UserRepository;
import com.tnicacio.peoplescore.util.mapper.Mapper;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDetailsServiceImpl implements UserDetailsService {

    ExceptionFactory exceptionFactory;
    UserRepository userRepository;
    Mapper<UserModel, UserDetails> userModelToUserDetailsMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserModel userModel = userRepository.findByUsername(username)
                .orElseThrow(() -> exceptionFactory.usernameNotFound("Usuário não encontrado: " + username));
        return userModelToUserDetailsMapper.mapNonNull(userModel);
    }

}

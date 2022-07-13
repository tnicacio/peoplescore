package com.tnicacio.peoplescore.user.service;

import com.tnicacio.peoplescore.exception.factory.ExceptionFactory;
import com.tnicacio.peoplescore.test.util.TestRandomUtils;
import com.tnicacio.peoplescore.user.model.UserDetailsImpl;
import com.tnicacio.peoplescore.user.model.UserModel;
import com.tnicacio.peoplescore.user.repository.UserRepository;
import com.tnicacio.peoplescore.util.mapper.Mapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class UserDetailsServiceImplTest {

    @Nested
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class LoadUserByUsernameTest {

        @InjectMocks
        UserDetailsServiceImpl userDetailsService;

        @Mock
        ExceptionFactory exceptionFactory;
        @Mock
        UserRepository userRepository;
        @Mock
        Mapper<UserModel, UserDetails> userModelToUserDetailsMapper;

        @BeforeEach
        void setUp() {
            openMocks(this);
        }

        @Test
        void shouldThrowNotFoundWhenScoreDescriptionNotFoundByScoreValue() {
            final String username = RandomStringUtils.randomAlphabetic(12);
            final String exceptionMessage = "Usuário não encontrado: " + username;
            final UsernameNotFoundException usernameNotFoundException = new UsernameNotFoundException(exceptionMessage);

            when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
            when(exceptionFactory.usernameNotFound(exceptionMessage)).thenReturn(usernameNotFoundException);

            assertThatThrownBy(() -> userDetailsService.loadUserByUsername(username))
                    .isInstanceOf(UsernameNotFoundException.class)
                    .hasMessage(exceptionMessage);
        }


        @Test
        void shouldLoadUserByUsername() {
            final String username = RandomStringUtils.randomAlphabetic(12);
            final UserModel userModelFound = TestRandomUtils.randomObject(UserModel.class);
            final UserDetails userDetails = TestRandomUtils.randomObject(UserDetailsImpl.class);

            when(userRepository.findByUsername(username)).thenReturn(Optional.of(userModelFound));
            when(userModelToUserDetailsMapper.mapNonNull(userModelFound)).thenReturn(userDetails);

            final UserDetails result = userDetailsService.loadUserByUsername(username);

            assertThat(result).extracting(UserDetails::getUsername).isEqualTo(userDetails.getUsername());
            assertThat(result).extracting(UserDetails::getPassword).isEqualTo(userDetails.getPassword());
            assertThat(result).extracting(UserDetails::getAuthorities).isEqualTo(userDetails.getAuthorities());
        }
    }

}
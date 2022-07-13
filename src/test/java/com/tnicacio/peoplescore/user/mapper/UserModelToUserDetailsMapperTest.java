package com.tnicacio.peoplescore.user.mapper;

import com.tnicacio.peoplescore.test.util.TestRandomUtils;
import com.tnicacio.peoplescore.user.model.UserModel;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.openMocks;

class UserModelToUserDetailsMapperTest {

    @Nested
    @FieldDefaults(level = AccessLevel.PRIVATE)
    class MapNonNull {

        @InjectMocks
        UserModelToUserDetailsMapper userModelToUserDetailsMapper;

        @BeforeEach
        void setUp() {
            openMocks(this);
        }

        @Test
        void shouldMapFromModelToUserDetails() {
            final UserModel userModel = TestRandomUtils.randomObject(UserModel.class);
            final List<GrantedAuthority> expectedAuthorities = userModel.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                    .collect(Collectors.toList());

            final UserDetails userDetails = userModelToUserDetailsMapper.mapNonNull(userModel);

            assertThat(userDetails)
                    .isNotNull()
                    .satisfies(user -> {
                        assertThat(user).extracting(UserDetails::getUsername).isEqualTo(userModel.getUsername());
                        assertThat(user).extracting(UserDetails::getPassword).isEqualTo(userModel.getPassword());
                        assertThat(user).extracting(UserDetails::getAuthorities).asList().hasSameElementsAs(expectedAuthorities);
                    });
        }

    }
}
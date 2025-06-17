package shop.ink3.api.user.user.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import shop.ink3.api.user.common.exception.InvalidPasswordException;
import shop.ink3.api.user.membership.entity.Membership;
import shop.ink3.api.user.membership.exception.MembershipNotFoundException;
import shop.ink3.api.user.membership.repository.MembershipRepository;
import shop.ink3.api.user.user.dto.UserAuthResponse;
import shop.ink3.api.user.user.dto.UserCreateRequest;
import shop.ink3.api.user.user.dto.UserDetailResponse;
import shop.ink3.api.user.user.dto.UserMembershipUpdateRequest;
import shop.ink3.api.user.user.dto.UserPasswordUpdateRequest;
import shop.ink3.api.user.user.dto.UserResponse;
import shop.ink3.api.user.user.dto.UserUpdateRequest;
import shop.ink3.api.user.user.entity.User;
import shop.ink3.api.user.user.entity.UserStatus;
import shop.ink3.api.user.user.exception.UserAuthNotFoundException;
import shop.ink3.api.user.user.exception.UserNotFoundException;
import shop.ink3.api.user.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @Mock
    MembershipRepository membershipRepository;

    @Spy
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @InjectMocks
    UserService userService;

    @Test
    void isLoginIdAvailable() {
        when(userRepository.existsByLoginId(anyString())).thenReturn(false);
        Assertions.assertTrue(userService.isLoginIdAvailable("loginId").available());
    }

    @Test
    void isLoginIdAvailableWithExists() {
        when(userRepository.existsByLoginId(anyString())).thenReturn(true);
        Assertions.assertFalse(userService.isLoginIdAvailable("loginId").available());
    }

    @Test
    void isEmailAvailable() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        Assertions.assertTrue(userService.isEmailAvailable("email@email.com").available());
    }

    @Test
    void isEmailAvailableWithExists() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);
        Assertions.assertFalse(userService.isEmailAvailable("email@email.com").available());
    }

    @Test
    void getUser() {
        User user = User.builder()
                .id(1L)
                .loginId("test")
                .password("test")
                .name("test")
                .email("test@test.com")
                .phone("010-1234-5678")
                .birthday(LocalDate.of(2025, 1, 1))
                .point(1000)
                .membership(Membership.builder().id(1L).build())
                .lastLoginAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserResponse response = userService.getUser(1L);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(UserResponse.from(user), response);
    }

    @Test
    void getUserWithNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUser(1L));
    }

    @Test
    void getUserDetail() {
        User user = User.builder()
                .id(1L)
                .loginId("test")
                .password("test")
                .name("test")
                .email("test@test.com")
                .phone("010-1234-5678")
                .birthday(LocalDate.of(2025, 1, 1))
                .point(1000)
                .membership(Membership.builder().id(1L).build())
                .lastLoginAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserDetailResponse response = userService.getUserDetail(1L);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(UserDetailResponse.from(user), response);
    }

    @Test
    void getUserDetailWithNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUserDetail(1L));
    }

    @Test
    void getUserAuth() {
        User user = User.builder()
                .id(1L)
                .loginId("test")
                .password("test")
                .name("test")
                .email("test@test.com")
                .phone("010-1234-5678")
                .birthday(LocalDate.of(2025, 1, 1))
                .point(1000)
                .membership(Membership.builder().id(1L).build())
                .lastLoginAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();
        when(userRepository.findByLoginId("test")).thenReturn(Optional.of(user));
        UserAuthResponse response = userService.getUserAuth("test");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(UserAuthResponse.from(user), response);
    }

    @Test
    void getUserAuthWithNotFound() {
        when(userRepository.findByLoginId("test")).thenReturn(Optional.empty());
        Assertions.assertThrows(UserAuthNotFoundException.class, () -> userService.getUserAuth("test"));
    }

    @Test
    void createUser() {
        Membership membership = Membership.builder().id(1L).build();
        User user = User.builder()
                .id(1L)
                .loginId("test")
                .password("test")
                .name("test")
                .email("test@test.com")
                .phone("010-1234-5678")
                .birthday(LocalDate.of(2025, 1, 1))
                .point(1000)
                .membership(membership)
                .lastLoginAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();
        when(membershipRepository.findByIsDefault(true)).thenReturn(Optional.of(membership));
        when(userRepository.save(any())).thenReturn(user);

        UserCreateRequest request = new UserCreateRequest(
                "test",
                "test",
                "test",
                "test@test.com",
                "010-1234-5678",
                LocalDate.now()
        );

        UserResponse response = userService.createUser(request);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(UserResponse.from(user), response);
    }

    @Test
    void createUserWithoutDefaultMembership() {
        UserCreateRequest request = new UserCreateRequest(
                "test",
                "test",
                "test",
                "test@test.com",
                "010-1234-5678",
                LocalDate.now()
        );
        when(membershipRepository.findByIsDefault(true)).thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalStateException.class, () -> userService.createUser(request));
    }

    @Test
    void updateUser() {
        User user = User.builder()
                .id(1L)
                .loginId("test")
                .password("test")
                .name("test")
                .email("test@test.com")
                .phone("010-1234-5678")
                .birthday(LocalDate.of(2025, 1, 1))
                .point(1000)
                .membership(Membership.builder().id(1L).build())
                .lastLoginAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();
        UserUpdateRequest request = new UserUpdateRequest(
                "new",
                "new@new.com",
                "010-5150-5150",
                LocalDate.of(2025, 1, 2)
        );
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        UserResponse userResponse = userService.updateUser(1L, request);
        Assertions.assertNotNull(userResponse);
        Assertions.assertEquals("new", userResponse.name());
        Assertions.assertEquals("new@new.com", userResponse.email());
        Assertions.assertEquals("010-5150-5150", userResponse.phone());
        Assertions.assertEquals(LocalDate.of(2025, 1, 2), userResponse.birthday());
    }

    @Test
    void updateUserWithNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUserDetail(1L));
    }

    @Test
    void updateUserPassword() {
        User user = User.builder().password(passwordEncoder.encode("old")).build();
        UserPasswordUpdateRequest request = new UserPasswordUpdateRequest("old", "new");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        userService.updateUserPassword(1L, request);
        Assertions.assertTrue(passwordEncoder.matches(request.newPassword(), user.getPassword()));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void updateUserPasswordWithInvalidPassword() {
        User user = User.builder().password(passwordEncoder.encode("old")).build();
        UserPasswordUpdateRequest request = new UserPasswordUpdateRequest("invalid", "new");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Assertions.assertThrows(InvalidPasswordException.class, () -> userService.updateUserPassword(1L, request));
    }

    @Test
    void updateUserPasswordWithNotFound() {
        UserPasswordUpdateRequest request = new UserPasswordUpdateRequest("old", "new");
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.updateUserPassword(1L, request));
    }

    @Test
    void activateUser() {
        User user = User.builder().id(1L).status(UserStatus.DORMANT).build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        userService.activateUser(1L);
        Assertions.assertEquals(UserStatus.ACTIVE, user.getStatus());
        verify(userRepository).save(user);
    }

    @Test
    void activateUserWithNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUserDetail(1L));
    }

    @Test
    void markAsDormantUser() {
        User user = User.builder().id(1L).status(UserStatus.ACTIVE).build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        userService.markAsDormantUser(1L);
        Assertions.assertEquals(UserStatus.DORMANT, user.getStatus());
        verify(userRepository).save(user);
    }

    @Test
    void markAsDormantUserWithNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUserDetail(1L));
    }

    @Test
    void withdrawUser() {
        User user = User.builder().id(1L).status(UserStatus.ACTIVE).build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        userService.withdrawUser(1L);
        Assertions.assertEquals(UserStatus.WITHDRAWN, user.getStatus());
        verify(userRepository).save(user);
    }

    @Test
    void withdrawUserWithNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUserDetail(1L));
    }

    @Test
    void updateMembership() {
        Membership membership = Membership.builder().id(2L).build();
        User user = User.builder().id(1L).membership(Membership.builder().id(1L).build()).build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(membershipRepository.findById(2L)).thenReturn(Optional.of(membership));
        userService.updateMembership(1L, new UserMembershipUpdateRequest(2L));
        Assertions.assertEquals(membership, user.getMembership());
        verify(userRepository).save(user);
    }

    @Test
    void updateMembershipWithUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUserDetail(1L));
    }

    @Test
    void updateMembershipWithMembershipNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(User.builder().build()));
        when(membershipRepository.findById(1L)).thenReturn(Optional.empty());
        UserMembershipUpdateRequest request = new UserMembershipUpdateRequest(1L);
        Assertions.assertThrows(MembershipNotFoundException.class, () -> userService.updateMembership(1L, request));
    }

    @Test
    void updateLastLoginAt() {
        User user = User.builder().id(1L).build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        userService.updateLastLoginAt(1L);
        Assertions.assertNotNull(user.getLastLoginAt());
        verify(userRepository).save(user);
    }

    @Test
    void updateLastLoginAtWithNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.updateLastLoginAt(1L));
    }

    @Test
    void deleteUser() {
        User user = User.builder().id(1L).build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        userService.deleteUser(1L);
        verify(userRepository).delete(user);
    }

    @Test
    void deleteUserWithNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.deleteUser(1L));
    }
}

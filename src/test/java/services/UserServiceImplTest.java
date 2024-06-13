package services;


import static org.mockito.Mockito.*;

import dao.UserDAO;
import dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.Impl.UserServiceImpl;
import service.UserService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    private UserService userService;
    private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        userDAO = mock(UserDAO.class);
        userService = new UserServiceImpl(userDAO);
    }

    @Test
    void testGetAllUsers() {
        List<UserDTO> users = Arrays.asList(
                new UserDTO(1L, "user1@example.com", "password1"),
                new UserDTO(2L, "user2@example.com", "password2")
        );

        when(userDAO.getAllUsers()).thenReturn(users);

        List<UserDTO> result = userService.getAllUsers();
        assertEquals(2, result.size());
        verify(userDAO, times(1)).getAllUsers();
    }

    @Test
    void testCreateUser() {
        UserDTO user = new UserDTO(1L, "user1@example.com", "password1");

        userService.createUser(user);

        verify(userDAO, times(1)).createUser(user);
    }

    @Test
    void testUpdateUser() {
        UserDTO user = new UserDTO(1L, "user1@example.com", "password1");

        userService.updateUser(user);

        verify(userDAO, times(1)).updateUser(user);
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;

        userService.deleteUser(userId);

        verify(userDAO, times(1)).deleteUser(userId);
    }

    @Test
    void testGetUserById() {
        Long userId = 1L;
        UserDTO user = new UserDTO(userId, "user1@example.com", "password1");

        when(userDAO.getUserById(userId)).thenReturn(user);

        UserDTO result = userService.getUserById(userId);
        assertEquals(user, result);
        verify(userDAO, times(1)).getUserById(userId);
    }
}

package service;

import dto.UserDTO;
import java.util.List;

public interface UserService {
    void createUser(UserDTO user);
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
    void updateUser(UserDTO user);
    void deleteUser(Long id);
}


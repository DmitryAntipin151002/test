package service.Impl;

import dao.UserDAO;
import dto.UserDTO;
import service.UserService;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void createUser(UserDTO user) {
        userDAO.createUser(user);
    }

    @Override
    public UserDTO getUserById(Long id) {
        return userDAO.getUserById(id);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    public void updateUser(UserDTO user) {
        userDAO.updateUser(user);
    }

    @Override
    public void deleteUser(Long id) {
        userDAO.deleteUser(id);
    }
}

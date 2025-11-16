package services;

import interfaces.repo.IUserRepository;
import interfaces.serv.IUserService;
import modules.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;

public class UserService implements IUserService {

    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Map<String, String> validateAndRegister(String name, String email, String password) {
        Map<String, String> errors = new HashMap<>();

        // Валидация
        if (name == null || name.trim().isEmpty()) {
            errors.put("name", "Name is required.");
        }
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.put("email", "Valid email is required.");
        }
        if (password == null || password.length() < 6) {
            errors.put("password", "Password must be at least 6 characters.");
        }

        // Проверка уникальности email
        if (errors.isEmpty() && userRepository.findByEmail(email) != null) {
            errors.put("email", "Email is already registered.");
        }

        if (!errors.isEmpty()) {
            return errors;
        }

        // Хеширование пароля и сохранение
        String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(email, passwordHash, name.trim());
        userRepository.save(user);

        return null;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User authenticate(String email, String password) {
        if (email == null || password == null) {
            return null;
        }

        User user = userRepository.findByEmail(email);
        if (user != null && BCrypt.checkpw(password, user.getPasswordHash())) {
            return user;
        }
        return null;
    }

    @Override
    public User getUserById(Long userId) {
        if (userId == null) {
            return null;
        }
        return userRepository.findById(userId);
    }
}
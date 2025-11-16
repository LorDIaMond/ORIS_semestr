package interfaces.serv;

import modules.User;
import java.util.Map;

public interface IUserService {
    Map<String, String> validateAndRegister(String name, String email, String password);
    User findByEmail(String email);
    User authenticate(String email, String password);
    User getUserById(Long userId);

}

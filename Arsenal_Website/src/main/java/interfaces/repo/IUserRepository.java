package interfaces.repo;

import modules.User;

public interface IUserRepository extends ICrudRepository<User, Long> {
    User findByEmail(String email);
}

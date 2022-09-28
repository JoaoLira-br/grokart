package users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUsingId(int userID);

    @Transactional
    void deleteUsingId(int userID);

    //To find user's preferred store
    User findStore(String store);

    //To find what privilege does user owns
    User whatPrivilege(String privilege);
}
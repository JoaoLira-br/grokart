package groKart_app.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findById(int id);

    User findByUserName(String userName);

    User findByUserNameAndPassword(String userName, String password);

    User findByPrivilegeAndPreferredStore(int privilege, String preferredStore);

    boolean existsByUserName(String userName);

    List<User> findAllByPreferredStore(String storeName);

    List<User> findAllByPrivilege(int privilege);

    @Transactional
    void deleteByUserName(String userName);
}

package groKart_app.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
    
    User findById(int id);

    User findByUserName(String userName);

    boolean existsByUserName(String userName);

    @Transactional
    void deleteByUserName(String userName);
}

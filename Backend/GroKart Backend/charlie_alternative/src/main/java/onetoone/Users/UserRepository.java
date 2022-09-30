package onetoone.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
    
    User findById(int id);

    User findByUserName(String userName);

    @Transactional
    void deleteById(int id);

    User findByStore_Id(int id);
}

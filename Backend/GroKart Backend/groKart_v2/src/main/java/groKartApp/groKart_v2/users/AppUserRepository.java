package groKartApp.groKart_v2.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findUsingId(int userID, String password);


    @Transactional
    void deleteUsingId(int userID);

    //To find user's preferred store
    AppUser findStore(String store);

    //To find what privilege does user owns
    AppUser whatPrivilege(String privilege);
}

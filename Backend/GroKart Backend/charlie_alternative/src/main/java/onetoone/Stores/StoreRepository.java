package onetoone.Stores;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


public interface StoreRepository extends JpaRepository<Store, Long> {
    Store findById(int id);

    @Transactional
    void deleteById(int id);
}

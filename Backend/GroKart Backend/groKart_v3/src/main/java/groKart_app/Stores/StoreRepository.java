package groKart_app.Stores;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface StoreRepository extends JpaRepository<Store, Long>{
    Store findById(int storeId);

    Store findByStoreName(String storeName);

    boolean existsByStoreName(String storeName);

    @Transactional
    void deleteByStoreName(String storeName);
}




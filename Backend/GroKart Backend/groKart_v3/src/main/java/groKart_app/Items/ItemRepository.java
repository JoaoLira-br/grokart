package groKart_app.Items;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

//import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findById(int id);
    Item findByStoreNameAndName(String storeName, String itemName);

    boolean existsByStoreNameAndName(String storeName, String itemName);
    @Transactional
    void deleteByStoreNameAndName(String storeName, String itemName);
}

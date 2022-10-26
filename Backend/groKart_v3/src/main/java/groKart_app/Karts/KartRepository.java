package groKart_app.Karts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface KartRepository extends JpaRepository<Kart, Long> {

    boolean existsByKartName(String kartName);
    Kart findByKartName(String kartName);

    @Transactional
    void deleteByKartName(String kartName);
}

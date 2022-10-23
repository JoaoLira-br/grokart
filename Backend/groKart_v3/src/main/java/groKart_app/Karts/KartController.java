package groKart_app.Karts;

import groKart_app.Items.ItemRepository;
import groKart_app.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class KartController {
    @Autowired
    KartRepository kartRepository;
//    @Autowired
//    UserRepository userRepository;
    @Autowired
    ItemRepository itemRepository;

    /**
     * GET ALL KARTS IN THE DB
     *
     * @return
     */
    @GetMapping(path = "/karts")
    List<Kart> getAllKarts() {
        return kartRepository.findAll();
    }

}

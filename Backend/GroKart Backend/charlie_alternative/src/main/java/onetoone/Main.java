package onetoone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import onetoone.Stores.Store;
import onetoone.Stores.StoreRepository;
import onetoone.Users.User;
import onetoone.Users.UserRepository;

@SpringBootApplication
//@EnableJpaRepositories
class Main {
    private static int BASE_USER = 0;
    private static int STORE_ADMIN = 1;
    private static int APP_ADMIN = 2;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    // Create 3 admin stores and their admin users
    /**
     *
     * @param userRepository repository for the User entity
     * @param storeRepository repository for the Store entity
     * Creates a commandLine runner to enter dummy data into the database
     * As mentioned in User.java just associating the Store object with the User will save it into the database because of the CascadeType
     */
    @Bean
    CommandLineRunner initUser(UserRepository userRepository, StoreRepository storeRepository) {
        return args -> {
            User user1 = new User("fareway_customer", "customer@mail.com", "ex_pass", "Ex Customer", BASE_USER);
            User user2 = new User("goodwill_admin", "gw@mail.com", "gw_pass", "Goodwill Admin", STORE_ADMIN);
            User user3 = new User("app_admin", "superuser@mail.com", "super_strong_pass", "App Admin", APP_ADMIN);
            Store store1 = new Store("Fareway", "123");
            Store store2 = new Store("Goodwill", "456");
            Store store3 = new Store("Roy's", "789");
            user1.setStore(store1);
            user2.setStore(store2);
            user3.setStore(store3);
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
        };
    }

}

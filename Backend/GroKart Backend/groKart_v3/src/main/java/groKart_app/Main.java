package groKart_app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import groKart_app.Users.UserRepository;

@SpringBootApplication
@EnableJpaRepositories
class Main {
    private static int BASE_USER = 0;
    private static int STORE_ADMIN = 1;
    private static int APP_ADMIN = 2;


    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    /**
     *
     * @param userRepository repository for the User entity
     * Creates a commandLine runner to enter dummy data into the database
     * As mentioned in User.java just associating the Store object with the User will save it into the database because of the CascadeType
     */
    @Bean
    CommandLineRunner initUser(UserRepository userRepository) {
        return args -> {
//            User user1 = new User("fareway_customer", "customer@mail.com", "ex_pass", "Ex Customer", BASE_USER);
//            User user2 = new User("goodwill_admin", "gw@mail.com", "gw_pass", "Goodwill Admin", STORE_ADMIN);
//            User user3 = new User("app_admin", "superuser@mail.com", "super_strong_pass", "App Admin", APP_ADMIN);
//            userRepository.save(user1);
//            userRepository.save(user2);
//            userRepository.save(user3);
        };
    }

}

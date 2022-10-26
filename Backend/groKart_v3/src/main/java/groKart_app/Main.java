package groKart_app;

import groKart_app.Items.ItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import groKart_app.Users.UserRepository;
import groKart_app.Items.ItemRepository;

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
    CommandLineRunner initUser(UserRepository userRepository, ItemRepository itemRepository) {
        return args -> {

        };
    }

}

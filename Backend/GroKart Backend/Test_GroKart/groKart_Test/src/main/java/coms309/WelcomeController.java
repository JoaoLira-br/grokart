package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple Hello World Controller to display the string returned - Test Purpose
 *
 * @author Baganesra Bhaskaran
 */

@RestController
class WelcomeController {

    @GetMapping("/home")
    public String welcome() {
        return "Hello succesfully loaded the Application";
    }
}

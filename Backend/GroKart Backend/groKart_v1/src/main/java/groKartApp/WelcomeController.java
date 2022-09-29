package groKartApp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
class WelcomeController {
    @GetMapping("/home")
    public String welcome() {
        return "Hello and welcome to COMS 309, this is Your Friend speaking here, I have successfully entered the mainframe";
    }

}

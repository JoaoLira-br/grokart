package coms309;

/**
 * Controller used to showcase what happens when an exception is thrown
 *
 * @author Baganesra Bhaskaran
 */

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ExceptionController {

    @RequestMapping(method = RequestMethod.GET, path = "/exception")
    public String triggerException() {
        throw new RuntimeException("Exception Detected! Debug the code!");
    }

}

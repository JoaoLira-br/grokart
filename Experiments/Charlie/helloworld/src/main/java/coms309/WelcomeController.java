package coms309;

import java.net.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "Hello and welcome to COMS 309";
    }

    @GetMapping("/{op1}/{operation}/{op2}")
    public String welcome(@PathVariable int op1, @PathVariable String operation, @PathVariable int op2) {
        String ret = "Hello and welcome to COMS 309";
        if (operation.equals("plus")) {
            System.out.println("user hit '+' endpoint.");
            ret = String.format("%d + %d = %d", op1, op2, op1 + op2);
        } else if (operation.equals("minus")) {
            System.out.println("user hit '-' endpoint.");
            ret = String.format("%d - %d = %d", op1, op2, op1 - op2);
        } else if (operation.equals("times")) {
            System.out.println("user hit '*' endpoint.");
            ret = String.format("%d * %d = %d", op1, op2, op1 * op2);
        } else if (operation.equals("by")) {
            System.out.println("user hit '/' endpoint.");
            ret = String.format("%d - %d = %d", op1, op2, op1 / op2);
        } else if (operation.equals("power")) {
            System.out.println("user hit '^' endpoint.");
            ret = String.format("%d^%d = %d", op1, op2, (int) Math.pow(op1, op2));
        }
            return ret;
    }
}

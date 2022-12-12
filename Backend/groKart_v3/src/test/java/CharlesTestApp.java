import groKart_app.Items.ItemRepository;
import groKart_app.Main;
import groKart_app.Reports.ReportRepository;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {Main.class})
public class CharlesTestApp {

    @Autowired
    ItemRepository itemRepository;

    //String checking messages
    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @Before
    public void setUp(){
        RestAssured.port = 8080;
        RestAssured.baseURI = "http://coms-309-011.class.las.iastate.edu";
    }


    @Test
    public void itemExistsTest(){
        //Send report status request and receive a response of null or string
        RestAssured.given().log().all().pathParam("storeName","Fareway")
                .when()
                .get("http://coms-309-011.class.las.iastate.edu/items/{storeName}/{itemName}", "apple")
                .then().assertThat().statusCode(200);
    }

    @Test
    public void createLoginDeleteUser(){
        //Send the post request
        Map<String, String> request = new HashMap<>();
        request.put("userName", "charlieTestUser");
        request.put("emailAdd", "ctu@g.c");
        request.put("password", "ctupw");
        request.put("displayName", "CTU");
        request.put("privilege", "0");

        Response response = RestAssured.given().log().all()
                .header("Content-Type", "application/json")
                .body(request)
                .when()
                .post("http://coms-309-011.class.las.iastate.edu/users");

        //Check status code
        int statusCode = response.getStatusCode();
        System.out.println(response.getBody());
        assertEquals(200, statusCode);

        RestAssured.given().log().all().pathParam("userName","charlieTestUser")
                .when()
                .get("http://coms-309-011.class.las.iastate.edu/users/{userName}/{password}", "ctupw")
                .then().assertThat().statusCode(200);

        RestAssured.given().log().all().pathParam("userName","charlieTestUser")
                .when()
                .delete("http://coms-309-011.class.las.iastate.edu/users/{userName}", "testCharlieApple")
                .then().assertThat().statusCode(200);
    }

    @Test
    public void createDeleteItem(){
        //Send the post request
        Map<String, String> request = new HashMap<>();
        request.put("name", "testCharlieApple");
        request.put("price", "2.00");
        request.put("storeName", "Target");
        request.put("quantity", "100");

        Response response = RestAssured.given().log().all()
                .header("Content-Type", "application/json")
                .body(request)
                .when()
                .post("http://coms-309-011.class.las.iastate.edu/items");

        //Check status code
        int statusCode = response.getStatusCode();
        System.out.println(response.getBody());
        assertEquals(200, statusCode);

        RestAssured.given().log().all().pathParam("storeName","Target")
                .when()
                .delete("http://coms-309-011.class.las.iastate.edu/items/{storeName}/{itemName}", "testCharlieApple")
                .then().assertThat().statusCode(200);
    }

    //Test to Create A Report
    @Test
    public void createDeleteKart() {
        Response response = RestAssured.given().log().all().pathParams("userName", "ex_user", "kartName", "ex_kart")
                .header("Content-Type", "application/json")
                .when()
                .post("http://coms-309-011.class.las.iastate.edu/karts/{userName}/{kartName}");

        //Check status code
        int statusCode = response.getStatusCode();
        System.out.println(response.getBody());
        assertEquals(200, statusCode);

        //Checking response back
        String returnString = response.getBody().asString();
        try{
            assertEquals(returnString,"0");
        }catch(Exception e){
            e.printStackTrace();
        }

        RestAssured.given().log().all().pathParam("kartName","ex_kart")
                .when()
                .delete("http://coms-309-011.class.las.iastate.edu/karts/{kartName}")
                .then().assertThat().statusCode(200);
    }
}

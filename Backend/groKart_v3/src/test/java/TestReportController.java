import static io.restassured.RestAssured.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import groKart_app.Reports.Report;
import groKart_app.Reports.ReportController;
import groKart_app.Reports.ReportRepository;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@RunWith(SpringRunner.class)
public class TestReportController {

    @Before
    public void setUp(){
        RestAssured.port = 8080;
        RestAssured.baseURI = "http://coms-309-011.class.las.iastate.edu";
    }

    @Test
    public void reportStatusTest(){
        //Send report status request and receive a response of null or string
        RestAssured.given().log().all().pathParam("reportTitle","abcd")
                .when()
                .get("http://coms-309-011.class.las.iastate.edu/reports/{reportTitle}/{storeName}/status","Walmart")
                .then()
                .log().all();
    }

}

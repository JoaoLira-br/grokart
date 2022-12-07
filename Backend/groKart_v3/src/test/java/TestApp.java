import static org.junit.jupiter.api.Assertions.assertEquals;
import groKart_app.*;

import groKart_app.Reports.Report;
import groKart_app.Reports.ReportController;
import groKart_app.Reports.ReportRepository;
import io.restassured.RestAssured;

import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {Main.class})
public class TestApp {

    @Autowired
    ReportRepository reportRepository;

    //String checking messages
    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @Before
    public void setUp(){
        RestAssured.port = 8080;
        RestAssured.baseURI = "http://coms-309-011.class.las.iastate.edu";
    }

    //Base Controller Testing for Reports
    @Test
    public void reportStatusTest(){
        //Send report status request and receive a response of null or string
        RestAssured.given().log().all().pathParam("reportTitle","Test 2")
                .when()
                .get("http://coms-309-011.class.las.iastate.edu/reports/{reportTitle}/{storeName}/status","HyVee")
                .then().assertThat().statusCode(200);
    }

    //TEST for Altering Report Status
    @Test
    public void statusUpdateTest(){
        //Send request and receive the response
        Response response = RestAssured.given().log().all()
                .pathParams("reportTitle", "abcd", "storeName", "Walmart", "status", "Declined")
                .when()
                .put("http://coms-309-011.class.las.iastate.edu/reports/{reportTitle}/{storeName}/status/{status}");

        //Check Status Code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        //Checking response body for the correct response
        String returnString = response.getBody().asString();
        try{
            assertEquals(returnString,reportRepository.findByReportTitleAndStoreName("abcd","Walmart").getReportStatus());
        }catch(Exception e){
            e.printStackTrace();
        }

        //Test for altering a report that doesn't exist
        Response response1 = RestAssured.given().log().all()
                .pathParams("reportTitle", "abcdef", "storeName", "HyVee", "status", "Declined")
                .when()
                .put("http://coms-309-011.class.las.iastate.edu/reports/{reportTitle}/{storeName}/status/{status}");

        //Check Status Code
        statusCode = response1.getStatusCode();
        assertEquals(200, statusCode);

        //Checking response back
        returnString = response1.getBody().asString();
        try{
            assertEquals(returnString,failure);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //Test to Create A Report
    @Test
    public void createReportTest(){
        
    }




}

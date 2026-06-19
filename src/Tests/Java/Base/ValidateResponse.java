package Java.Base;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ValidateResponse {

    String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI1NDExMyIsImVudGl0eVR5cGUiOiJJRCIsInJvbGVMaXN0IjpbIlJPTEVfQ1VTVE9NRVIiXSwiaXNzIjoiY29tLnRydWVtZWRzLmF1dGhfc2VydmljZSIsImV4cCI6MTc4MTkzMTcwNywiaWF0IjoxNzgxODQ1MzA3fQ.rqouqo0UI31EMSR8wogjftsDDJDHPbLT68GHmiEYIKEoES-E7rh9ERK5fJbcpNhIygUmz4K9qmS0VAodxwqH1L_vTXuRWLY5QSDEq_3QSYAKsNmZes5fPnUqvkeEqz8T7sqSPR9-LQciDooZStreOgNMp6AIatsddVufqaNx9e3cNQh-HVIbsQ_6vtjHT1m0yIHsLyO_WbUdOiUi0pQUgDvBwWFiPtli_2bSrw3Jg3YnHaOxI8BFZRQ8iosVJIoJCzD42oomvbLhr7mq6a15x8k2yBmiU5uSK2N6iAEVuaf5CdwOpMxry6r87-H0FyOYIej0CVky4zovH5ZOX-K3wg";

    @BeforeClass // basically I will execute these block of code before every test
    public void Setup(){
        RestAssured.baseURI = "https://stage-dev.truemedsapi.in";
        RestAssured.basePath = "/CustomerService";
    }

    @Test
    public void Validate(){

               given()
                .header("Authorization","Bearer " + token)
                .header("Accept","application/json")
                .param("orderId","1868899")
                //.param("customerId","") // No need for custid as backend only need the Order id
                .when()
                .get("/fetchOrderStatusDetails")

                .then()
                .statusCode(200)
                       .and()
                       .body("responseData.orderId", equalTo(1868899));

    }
}

package Java.Base;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class GetRequest {

    String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI1NDExMyIsImVudGl0eVR5cGUiOiJJRCIsInJvbGVMaXN0IjpbIlJPTEVfQ1VTVE9NRVIiXSwiaXNzIjoiY29tLnRydWVtZWRzLmF1dGhfc2VydmljZSIsImV4cCI6MTc4MTkzMTcwNywiaWF0IjoxNzgxODQ1MzA3fQ.rqouqo0UI31EMSR8wogjftsDDJDHPbLT68GHmiEYIKEoES-E7rh9ERK5fJbcpNhIygUmz4K9qmS0VAodxwqH1L_vTXuRWLY5QSDEq_3QSYAKsNmZes5fPnUqvkeEqz8T7sqSPR9-LQciDooZStreOgNMp6AIatsddVufqaNx9e3cNQh-HVIbsQ_6vtjHT1m0yIHsLyO_WbUdOiUi0pQUgDvBwWFiPtli_2bSrw3Jg3YnHaOxI8BFZRQ8iosVJIoJCzD42oomvbLhr7mq6a15x8k2yBmiU5uSK2N6iAEVuaf5CdwOpMxry6r87-H0FyOYIej0CVky4zovH5ZOX-K3wg";

    @BeforeClass
    public  static void Setup(){
        RestAssured.baseURI = "https://gorest.co.in";
        RestAssured.basePath = "/public";

    }


   @Test
    public void StatusCode(){
        given()
                .header("Bearer","8dd257c7bd81cac870732d4ca9c75d90445cd0e3b380754aa0fdf7e38d0e912f")
                .header("Accept","application/json")
                .when()
               .get("/v2/users")
       .then()
                .statusCode(200);

    }
}

package Java.Base;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class GetRequest {

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

package Java.Base;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;


public class GetRequest {

    String token ="eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI1NDExMyIsImVudGl0" +
            "eVR5cGUiOiJJRCIsInJvbGVMaXN0IjpbIlJPTEVfQ1VTVE9NRVIiXSwiaXNzIjoiY29tLnRyd" +
            "WVtZWRzLmF1dGhfc2VydmljZSIsImV4cCI6MTc4MjEyMjExNiwiaWF0IjoxNzgyMDM1NzE2fQ." +
            "f2WyMO47YtLQotNWWeptoMe61daHoxfLI76gxX2Nd7Z6LIrGhpp4tzjpDmBTmkn2gq7P8647-C03T0" +
            "-3VJO7eBgR-H0wO2eK37Knbdf-Aae-pN4vzFcPppY6x-n7ZR6J_9to0IFxOo9p64VVc7bchk0VJSXhQJA" +
            "iMLu1sBOstGiFppJjH7Oz8OjDAy74TAF2COB2KrLi0QNLt1UO1z0WPFx37PUs6N1U2pySTLfqf" +
            "5U0qQcuk0JcbGH2eVYVSQR3bBFJP1tGKHYZ1CcdfrV3ZGytzxbRn0hoQaPkUZst81ldjqufCBI1jvOtEnlHierL2GztxJ9kxgkatet8Esy4ew";

    @BeforeClass
    public  static void Setup(){
        RestAssured.baseURI = "https://stage-dev.truemedsapi.in";
        RestAssured.basePath = "/CustomerService";
    }


   @Test(enabled = false)
    public void StatusCode(){
        given()
                .header("Authorization","Bearer " + token)
                .header("Accept","application/json")
                .param("orderId","1868899")
                .param("customerId","")
                .when()
               .get("/fetchOrderStatusDetails")
       .then()
                .statusCode(200);
    }

    @Test
    public void VerifyResponseBody(){
        // Response is an class datatype of RestAssured library, respone returned by the .get is an object of Response
        Response res =
        given()
                .header("Authorization","Bearer " + token)
                .header("Accept","application/json")
                .param("orderId","1868899")
                .param("customerId","")
                .when()
                .get("/fetchOrderStatusDetails");



        //System.out.println(res.asString());
        //res.prettyPrint();

    }
}

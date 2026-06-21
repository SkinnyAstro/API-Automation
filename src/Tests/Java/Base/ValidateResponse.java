package Java.Base;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class ValidateResponse {

    String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWI" +
            "iOiI1NDExMyIsImVudGl0eVR5cGUiOiJJRCIsInJvbGVMaXN0IjpbIl" +
            "JPTEVfQ1VTVE9NRVIiXSwiaXNzIjoiY29tLnRydWVtZWRzLmF1dGhfc2VydmljZ" +
            "SIsImV4cCI6MTc4MjEyMjExNiwiaWF0IjoxNzgyMDM1NzE2fQ.f2WyMO47YtLQo" +
            "tNWWeptoMe61daHoxfLI76gxX2Nd7Z6LIrGhpp4tzjpDmBTmkn2gq7P8647-C03T0" +
            "-3VJO7eBgR-H0wO2eK37Knbdf-Aae-pN4vzFcPppY6x-n7ZR6J_9to0IFxOo9p64VVc7bchk0V" +
            "JSXhQJAiMLu1sBOstGiFppJjH7Oz8OjDAy74TAF2COB2KrLi0QNLt1UO1z0WPFx37PUs6N1U2pySTLfqf" +
            "5U0qQcuk0JcbGH2eVYVSQR3bBFJP1tGKHYZ1CcdfrV3ZGytzxbRn0hoQaPkUZst81ldjqufCBI1jvOtEnlHierL2GztxJ9kxgkatet8Esy4ew";

    @BeforeClass // basically I will execute these block of code before every test
    public void Setup() {
        RestAssured.baseURI = "https://stage-dev.truemedsapi.in";
        RestAssured.basePath = "/CustomerService";
    }

    @Test
    public void Validate() {
        Response res =
                given()
                        .header("Authorization", "Bearer " + token)
                        .header("Accept", "application/json")
                        .param("orderId", 1868899)
                        .when()
                .get("/fetchOrderStatusDetails")
                .then()
                .statusCode(200)
                .body("responseData.orderId", equalTo(1868899))
                .body("responseData.orderStatusDetailsList[0].header", equalTo("Order placed"))
                        .extract().response();
        res.prettyPrint();
    }
}

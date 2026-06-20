package Java.Base;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class ValidateResponse {

    String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI1NDExMyIsImVudGl0eVR5cGUiOiJJRCIsInJvbGVMaXN0IjpbIlJPTEVfQ1VTVE9NRVIiXSwiaXNzIjoiY29tLnRydWVtZWRzLmF1dGhfc2VydmljZSIsImV4cCI6MTc4MjAxODg0OCwiaWF0IjoxNzgxOTMyNDQ4fQ.eQtiTZj5vpBZXV1rj7rMWgN85oBCQempJTSLNe5CVT7cp1ssNJFtgYR9-fnyY1CkxiXC1n-crC2Tye4ffCjahxG_Tsk13Tyj1uM2QFdG3FQ_fuuCbOx-vk2XXtrbaCLu3dQJAmqwjCBCIaguI_z91CeyK6hqeB_-FFeh9GMTyhkFGxD6lbVI_2C4R3gC2q4H7zSmGUZrVviZ4u03D2KiL4I1Bz0UXigEIjyFvrMcg7s_gMpkNHXnP67tSgd5KR1-7BWrOZZLwFmhFyvfdlWsCrsoTcf5Ofdzh3JlUrEuOGmOylehD2jTQUoy6BR5V8bI-M0eXOVI7M9eqjDkJoS5Gw";

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
    }
}

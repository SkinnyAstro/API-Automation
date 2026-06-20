package Java.Base;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;


public class GetRequest {

    String token ="eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI1NDExMyIsImVudGl0eVR5cGUiOiJJRCIsInJvbGVMaXN0IjpbIlJPTEVfQ1VTVE9NRVIiXSwiaXNzIjoiY29tLnRydWVtZWRzLmF1dGhfc2VydmljZSIsImV4cCI6MTc4MjAxODg0OCwiaWF0IjoxNzgxOTMyNDQ4fQ.eQtiTZj5vpBZXV1rj7rMWgN85oBCQempJTSLNe5CVT7cp1ssNJFtgYR9-fnyY1CkxiXC1n-crC2Tye4ffCjahxG_Tsk13Tyj1uM2QFdG3FQ_fuuCbOx-vk2XXtrbaCLu3dQJAmqwjCBCIaguI_z91CeyK6hqeB_-FFeh9GMTyhkFGxD6lbVI_2C4R3gC2q4H7zSmGUZrVviZ4u03D2KiL4I1Bz0UXigEIjyFvrMcg7s_gMpkNHXnP67tSgd5KR1-7BWrOZZLwFmhFyvfdlWsCrsoTcf5Ofdzh3JlUrEuOGmOylehD2jTQUoy6BR5V8bI-M0eXOVI7M9eqjDkJoS5Gw";

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

package Java.Base;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PostRequest {
    String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI1NDExMyIsImVudGl0eVR5cGUiOiJJRCIsInJvbGVMaXN0IjpbIlJPTEVfQ1VTVE9NRVIiXSwiaXNzIjoiY29tLnRydWVtZWRzLmF1dGhfc2VydmljZSIsImV4cCI6MTc4MjAxODg0OCwiaWF0IjoxNzgxOTMyNDQ4fQ.eQtiTZj5vpBZXV1rj7rMWgN85oBCQempJTSLNe5CVT7cp1ssNJFtgYR9-fnyY1CkxiXC1n-crC2Tye4ffCjahxG_Tsk13Tyj1uM2QFdG3FQ_fuuCbOx-vk2XXtrbaCLu3dQJAmqwjCBCIaguI_z91CeyK6hqeB_-FFeh9GMTyhkFGxD6lbVI_2C4R3gC2q4H7zSmGUZrVviZ4u03D2KiL4I1Bz0UXigEIjyFvrMcg7s_gMpkNHXnP67tSgd5KR1-7BWrOZZLwFmhFyvfdlWsCrsoTcf5Ofdzh3JlUrEuOGmOylehD2jTQUoy6BR5V8bI-M0eXOVI7M9eqjDkJoS5Gw";


    @BeforeClass // basically I will execute these block of code before every test
    public void Setup() {
        RestAssured.baseURI = "https://stage-dev.truemedsapi.in";
        RestAssured.basePath = "/CustomerService";
    }

    @Test
    public void AddingAddress() {
        Response res =
                given()
                        .header("Authorization", "Bearer " + token)
                        .header("Accept", "application/json") // this line means in what format we accept the data
                        .contentType("application/json") // It means we are sending the JSON type of data to the server
                        .queryParam("customerId", 54113)
                        .body("{\n" +
                                "    \"addressId\": null,\n" +
                                "    \"addressType\": \"Home\",\n" +
                                "    \"addressline1\": \"Test ADDRESS\",\n" +
                                "    \"addressline2\": \"Karnik road\",\n" +
                                "    \"cityName\": \"Mumbai\",\n" +
                                "    \"landmark\": \"\",\n" +
                                "    \"pincode\": \"421301\",\n" +
                                "    \"stateName\": \"MAHARASHTRA\",\n" +
                                "    \"receiverName\": \"\",\n" +
                                "    \"receiverMobileNo\": \"8850843264\",\n" +
                                "    \"receiverFirstName\": \"Pregabalinn\",\n" +
                                "    \"receiverLastName\": \"\",\n" +
                                "    \"latitude\": null,\n" +
                                "    \"longitude\": null,\n" +
                                "    \"placeId\": null,\n" +
                                "    \"recalcLocation\": true\n" +
                                "}")
                        .when()
                        .post("/v1/saveAddress")
                        .then()
                        .statusCode(200)
                        .extract().response();
                res.prettyPrint();

    }
}

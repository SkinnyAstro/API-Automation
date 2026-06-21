package Java.Base;

import Models.Addaddress;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.checkerframework.checker.units.qual.A;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class PostRequest {
    String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI1NDExMyIsImVudGl0eVR5cGUiOiJJRCIsInJvbGVMaXN0IjpbIlJPTEV" +
            "fQ1VTVE9NRVIiXSwiaXNzIjoiY29tLnRydWVtZWRzLmF1dGhfc2VydmljZSIsImV4cCI6MTc4MjEyMjExNiwiaWF0IjoxNzgyMDM1NzE2fQ.f2WyM" +
            "O47YtLQotNWWeptoMe61daHoxfLI76gxX2Nd7Z6LIrGhpp4tzjpDmBTmkn2gq7P8647-C03T0-3VJO7eBgR-H0wO2eK37Knbdf-" +
            "Aae-pN4vzFcPppY6x-n7ZR6J_9to0IFxOo9p64VVc7bchk0VJSXhQJAiMLu1sBOstGiFppJjH7Oz8OjDAy74TAF2COB2KrLi0QNLt1UO1z0WPFx37PUs6N1U2pyS" +
            "TLfqf5U0qQcuk0JcbGH2eVYVSQR3bBFJP1tGKHYZ1CcdfrV3ZGytzxbRn0hoQaPkUZst81ldjqufCBI1jvOtEnlHierL2GztxJ9kxgkatet8Esy4ew";


    @BeforeClass // basically I will execute these block of code before every test
    public void Setup() {
        RestAssured.baseURI = "https://stage-dev.truemedsapi.in";
        RestAssured.basePath = "/CustomerService";
    }

    @Test
    public void AddingAddress() {

        // Created a POJO class to store the data and creating an object of that class
        Addaddress address = new Addaddress();
        address.setAddressline1("A/701 Nakshatra");
        address.setAddressline2("Test address");
        address.setCityName("Kalyan");
        address.setPincode(421301);
        address.setRecalcLocation(false);
        address.setLandmark("Axis Bank");
        address.setAddresstype("Home");

        Response res =
                given()
                        .header("Authorization", "Bearer " + token)
                        .header("Accept", "application/json") // this line means in what format we accept the data
                        .contentType("application/json") // It means we are sending the JSON type of data to the server
                        .queryParam("customerId", 54113)
                        .body(address)
                        .when()
                        .post("/v1/saveAddress")
                        .then()
                        .statusCode(200)
                        .body("responseData.addressId",notNullValue())
                        .extract().response();
        res.prettyPrint();



    }
}

package Java.Base;

import Models.Patientdetails;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.ToString;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class PostRequestPatient {
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
    public void addpatient(){
        Patientdetails details  = new Patientdetails();
        details.setAge(50);
        details.setGender(8);
        details.setFirstName("Cristiano");
        details.setLastName("Ronaldo");
        details.setRelationId(8);

        Response res =

        given()
                .header("Authorization", "Bearer " + token)
                .header("Accept","application/json")
                .contentType("application/json")
                .queryParam("customerId",54113)
                .body(details)
                .when()
                .post("/v1/addPatient")
                .then()
                .statusCode(200)
                .body("responseData.patientId", notNullValue())
                .extract().response();

        res.prettyPrint();

    }
}

package Java.Base;

import Models.Patientdetails;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.ToString;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class PostRequestPatient {
    String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI1NDExMyIsImVudGl0eVR5cGUiOiJJRCIsInJvbGVMaXN0I" +
            "jpbIlJPTEVfQ1VTVE9NRVIiXSwiaXNzIjoiY29tLnRydWVtZWRzLmF1dGhfc2VydmljZSIsImV4cCI6MTc4MjIyMDY0NCwiaWF0IjoxNz" +
            "gyMTM0MjQ0fQ.fMV8kMn2KnihNsldUS8utFiGQNktVwbqtv9J1z1xcGpRMxzP8MohRH_MvsnCxU9vvt9oZzHDCzTcKhpLQgwuNZG3gZvlkA4uQ" +
            "AlizqE5o0Mv8t7jnO-zTypUmY-juEDf8YhIoxR8ZanfPcx1KEvlX8PskNMhG9ay-V-sNKOkyb49Qgyr9LRmcTxxQqDOOma6b9pr6decZG8SCKQjr" +
            "0O4aB38BFYCjtoeeFa9EqvpIUSEw1Lwl4nSlbiRbPyMtgbGB8jGO7" +
            "apjaEtyBLjxGzqGd5A5lYJKSvW_K817cXOs2ekm2heR3XVPOBnu7EkT3LCp-aKmw0nC_Ss553IrLkQIQ";


    @BeforeClass // basically I will execute these block of code before every test
    public void Setup() {
        RestAssured.baseURI = "https://stage-dev.truemedsapi.in";
        RestAssured.basePath = "/CustomerService";
    }

    // Gender id for male is 8 and Gender id for female is 9

    @Test(description = "Adding a male patient")
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

    @Test(description = "Adding female patient")
    public void AddingFemalepatient(){
        Patientdetails details  = new Patientdetails();
        details.setAge(50);
        details.setGender(9);
        details.setFirstName("Sofia");
        details.setLastName("Test");
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
        //int patientid = res.path("responseData.patientId");
        //System.out.println(patientid);


        // Converting the response object as a string
        String responseString = res.asString();
        System.out.println(responseString);
        JsonPath json = new JsonPath(responseString);

        int patientid = json.get("responseData.patientId");
        System.out.println(patientid);

    }


}

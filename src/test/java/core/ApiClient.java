package core;

import io.restassured.filter.Filter;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ApiClient {
    private String accesstoken;
    public ApiClient(String accesstoken){
        this.accesstoken = accesstoken;
    }

    protected RequestSpecification getRequestSpec(){
        return given()
                .header("Authorization","Bearer " + accesstoken)
                .contentType("application/json")
                .filter(logFailedResponses())
                .log().ifValidationFails();
    }

    // For negative tests that must send no Authorization header at all
    protected RequestSpecification getRequestSpecWithoutAuth(){
        return given()
                .contentType("application/json")
                .filter(logFailedResponses())
                .log().ifValidationFails();
    }

    // Records 4xx/5xx responses; FailedResponseLogListener prints them only if the test fails
    private Filter logFailedResponses(){
        return FailedResponseLog.recordingFilter();
    }
}

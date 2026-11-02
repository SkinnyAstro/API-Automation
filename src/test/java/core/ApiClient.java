package core;

import io.restassured.filter.Filter;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

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

    // Prints status and body of any 4xx/5xx response so CI logs show why a call failed
    private Filter logFailedResponses(){
        // Body only: response headers expose internal hostnames and the repo is public
        return new ResponseLoggingFilter(LogDetail.BODY, true, System.out, greaterThanOrEqualTo(400));
    }
}

package Java.Base.Base;

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
                .log().ifValidationFails();
    }
}

package Java.Base.Helper;

import POJO.CustomerDetailsResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Customerhelper {

   private String accesstoken;

    public Customerhelper(String accesstoken){
       this.accesstoken = accesstoken;
    }

    public CustomerDetailsResponse getCustomerDetails(){
        return given()
                .header("Authorization","Bearer " + accesstoken)
                .contentType("application/json")
                .log().ifValidationFails()
                .when()
                .post("/CustomerService/getCustomerDetails")
                .then()
                .statusCode(200)
                .extract().as(CustomerDetailsResponse.class);

    }


}

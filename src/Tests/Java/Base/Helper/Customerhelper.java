package Java.Base.Helper;

import Java.Base.Base.ApiClient;
import POJO.CustomerDetailsResponse;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Customerhelper extends ApiClient {

    public Customerhelper(String accesstoken){
       super(accesstoken);
    }

    public CustomerDetailsResponse getCustomerDetails(){
        return getRequestSpec()
                .when()
                .post("/CustomerService/getCustomerDetails")
                .then()
                .statusCode(200)
                .extract().as(CustomerDetailsResponse.class);

    }


}

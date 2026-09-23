package base.Helper;

import base.Base.ApiClient;
import POJO.CustomerDetailsResponse;

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

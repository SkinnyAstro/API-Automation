package base.Service;

import base.Base.ApiClient;
import POJO.CustomerDetailsResponse;

import static io.restassured.RestAssured.given;

public class CustomerService extends ApiClient {

    public CustomerService(String accesstoken){
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

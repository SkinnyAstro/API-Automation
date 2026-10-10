package domain.customer;

import core.ApiClient;
import core.ConfigManager;
import domain.customer.pojo.Addaddress;
import domain.customer.pojo.CustomerDetailsResponse;
import io.restassured.response.Response;

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

    public Response fetchAlladdress(){
        return getRequestSpec()
                .when()
                .get("/CustomerService/v1/fetchAllAddress")
                .then()
                .log().ifValidationFails()
                .extract().response();
    }

    public Response addAddress(Addaddress addaddress){
        return getRequestSpec()
                .body(addaddress)
                .queryParam("customerId", ConfigManager.get("test.customer.id"))
                .when()
                .post("/CustomerService/v1/saveAddress")
                .then()
                .log().ifValidationFails()
                .extract().response();
    }

    public Response deleteAddress(int addressId){
        return getRequestSpec()
                .queryParam("addressId",addressId)
                .queryParam("customerId",ConfigManager.get("test.customer.id"))
                .when()
                .post("/CustomerService/v1/deleteAddress")
                .then()
                .log().ifValidationFails()
                .extract().response();

    }




}

package Java.Base;

import Models.Addaddress;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

public class AddressFlow {

    String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI1NDE2OSIsImVudGl0eVR5cGUiOiJJRCIsInJvbGVMaXN0IjpbI" +
            "lJPTEVfQ1VTVE9NRVIiXSwiaXNzIjoiY29tLnRydWVtZWRzLmF1dGhfc2VydmljZSIsImV4cCI6MTc4MjI4MDUxMywiaWF0IjoxNzgyMTk" +
            "0MTEzfQ.D6G3z2UPh1EleieLJ16Z5fsZXBBcVJoWNyILfp1KdpCgAxWd7cMq6o8xQBeoFtYMf2BzYPtKzl2rporRAlK0IhNxpKpXmODtfBVz" +
            "-QlDKjEt5HY686hajjZgGdeyQCysdh2YSya3VuxJtl26r2prxy5GEdCICT5S58YolpyPl28-JUH_xjBnZp5jV6UcvL6fKCw72UWk7d74wYU2a" +
            "HDnyNEKz3WYqc3hQtj9CH5ljQHOv3ywupqC3P4iFLFxNLKdwBnMnN8mTPfRKgBq6yW4IkOteL0dLa9wi16hEdIpVrYsE1RtyJ9GIjjBvN3BZY4MC" +
            "-zuPo91LkEWwzmTFekofA";

    Integer addressID;


    @BeforeClass // basically I will execute these block of code before every test
    public void Setup() {
        RestAssured.baseURI = "https://stage-dev.truemedsapi.in";
        RestAssured.basePath = "/CustomerService";
    }

    @Test (description = "Adding a new address")
    public void NewAddress(){
        Addaddress addaddress = new Addaddress();
        addaddress.setAddressType("Work");
        addaddress.setAddressline1("A/701 Nakshatra");
        addaddress.setCityName("Kalyan");
        addaddress.setPincode(421301);
        addaddress.setRecalcLocation(false);
        addaddress.setLandmark("Axis Bank");

        Response res =

        given()
                //.log().all()
                .header("Authorization","Bearer "+ token)
                .header("Accept","application/json")
                .contentType("application/json")
                //.queryParam("customerId",54169)
                .body(addaddress)
                .when()
                .post("/v1/saveAddress")
                .then()
                .statusCode(200)
                .body("message",equalTo("Address Saved Successfully"))
                .extract().response();


        addressID = res.path("responseData.addressId");
        if (addressID == null){
           System.out.println("Address ID may not be generated due to some issue");
       }else {

           System.out.println(addressID);
       }

    }

    @Test(dependsOnMethods = {"NewAddress"})
    public void EditAddress()throws com.fasterxml.jackson.core.JsonProcessingException{
        Addaddress addaddress = new Addaddress();
        addaddress.setAddressId(addressID);
        addaddress.setAddressType("Work");
        addaddress.setAddressline1("Om shivam Krupa");
        addaddress.setAddressline2("Test address");
        addaddress.setCityName("Kalyan");
        addaddress.setPincode(421301);
        addaddress.setRecalcLocation(false);
        addaddress.setLandmark("Axis Bank");

        System.out.println(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(addaddress));

        Response res =

                given()
                        .header("Authorization","Bearer "+ token)
                        .header("Accept","application/json")
                        .contentType("application/json")
                        .queryParam("customerId",54169)
                        .body(addaddress)
                        .when()
                        .post("/v1/saveAddress")
                        .then()
                        //.statusCode(200)
                        //.body("responseData.successMsg",equalTo("Address updated"))
                        .extract().response();

        System.out.println(addressID);


    }

//    @Test(dependsOnMethods = {"EditAddress"})
//    public void DeleteAddress(){
//        Response res =
//
//        given()
//                .header("Authorization", "Bearer "+token)
//                .header("Accept","application/json")
//                .queryParam("customerId",54113)
//                .queryParam("addressId",addressID)
//                .when()
//                        .post("v1/deleteAddress")
//                        .then()
//                        .statusCode(200)
//                        .body("message",equalTo("Address Deleted"))
//                        .extract().response();
//        res.prettyPrint();
//
//    }

}

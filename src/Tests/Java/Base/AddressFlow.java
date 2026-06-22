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

    String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI1NDExMyIsImVudGl0eVR5" +
            "cGUiOiJJRCIsInJvbGVMaXN0IjpbIlJPTEVfQ1VTVE9NRVIiXSwiaXNzIjoiY29tLnRydWVtZWRzLmF" +
            "1dGhfc2VydmljZSIsImV4cCI6MTc4MjIyMTc1NSwiaWF0IjoxNzgyMTM1MzU1fQ.G9zuDo0Wa0nZUJpcdw" +
            "BihRoy8C6iGwd8E6SOXnBFIH0zjuJfAf8g0RMH1e87avJa0Qivdbn0fHNYucEXQK4SzUQwy9uGbuDkrQbCzWP" +
            "xKhkwbclGvQI-OWbTdHO9INKqXSst8nyK4h5nHuqBsYS7_WbB3OPw27mmEP2dZDYLvI4f_l6UJ-BnkvryNGxUO02p" +
            "gUO3FIZRa47p0HRDI52_NcAFilx-o8rSaA0dvcYKimswwW-_ONp6VykvaK28HOEYGMEoOV9SO5b-aoJhR_j-OM-NKhQ" +
            "ykVeGlclGRSOixpetjL0BlLOrbGvT3p9fmlwJjQ28h8eiOPHnf6WmCBT2qg";

    Integer addressID;


    @BeforeClass // basically I will execute these block of code before every test
    public void Setup() {
        RestAssured.baseURI = "https://stage-dev.truemedsapi.in";
        RestAssured.basePath = "/CustomerService";
    }

    @Test (description = "Adding a new address")
    public void NewAddress(){
        Addaddress addaddress = new Addaddress();
        addaddress.setAddresstype("Work");
        addaddress.setAddressline1("A/701 Nakshatra");
        //addaddress.setAddressline2("Test address");
        addaddress.setCityName("Kalyan");
        addaddress.setPincode(421301);
        addaddress.setRecalcLocation(false);
        addaddress.setLandmark("Axis Bank");

        Response res =

        given()
                .header("Authorization","Bearer "+ token)
                .header("Accept","application/json")
                .contentType("application/json")
                .queryParam("customerId",54113)
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
        addaddress.setAddresstype("Work");
        addaddress.setAddressline1("A/701 Nakshatra");
        addaddress.setAddressline2("Test address");
        addaddress.setCityName("Kalyan");
        addaddress.setPincode(421301);
        addaddress.setRecalcLocation(false);
        addaddress.setLandmark("Axis Bank");
        addaddress.setAddressId(addressID);
        System.out.println(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(addaddress));

        Response res =

                given()
                        .header("Authorization","Bearer "+ token)
                        .header("Accept","application/json")
                        .contentType("application/json")
                        .queryParam("customerId",54113)
                        .body(addaddress)
                        .when()
                        .post("/v1/saveAddress")
                        .then()
                        .statusCode(200)
                        .body("message",equalTo("Address Saved Successfully"))
                        .extract().response();
        res.prettyPrint();

    }

    @Test(dependsOnMethods = {"EditAddress"})
    public void DeleteAddress(){
        Response res =

        given()
                .header("Authorization", "Bearer "+token)
                .header("Accept","application/json")
                .queryParam("customerId",54113)
                .queryParam("addressId",addressID)
                .when()
                        .post("v1/deleteAddress")
                        .then()
                        .statusCode(200)
                        .body("message",equalTo("Address Deleted"))
                        .extract().response();

    }

}

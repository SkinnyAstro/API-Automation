package Java.Base;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;

public class OrdertStatus  {

    String accesstoken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI1NDI5NSIsImVudGl0eVR5cGUiOiJJRCIsInJvbGVMaXN0IjpbIlJPTEVfQ1VTVE9NRVIiXSwiaXNzIjoiY29tLnRydWVtZWRzLmF1dGhfc2VydmljZSIsImV4cCI6MTc4NDYxMDIxMiwiaWF0IjoxNzg0NTIzODEyfQ.aFdzOnPK1VOM26nqamkwCEHDV7JIYhYYTLx60RX6WweFvxFsA4o-JKYt2vZbXjjGKGi3h0JfeBjLWqp58Zouk9td3FGAvl-pOBmuCW7ENAraccKDLGSZl85ogOal1ixxdtt_ujnqebB9Vt_6J9N2K1mw6bs3nKaFiB_x3Horot79EViykgsj2fsagzNvmZ8efnyiUJTsc-p4QUMKR8aXUAI2qN7fdPhgvk52uZZ--nBbX30Y8Bd0DT4An1O5QkgqTwGQptIOjpVkJixW2bCdP01nKP4BaWXodCeJllYKG9ZpM-XAH5Y8f9RBxdYAQ79dGdazNKE5bUG5P1BUJ6B-RQ";

    @BeforeClass
    public void SetUp(){
        RestAssured.baseURI = "https://stage-rng.truemedsapi.in";
        RestAssured.basePath= "/CustomerService";
    }

    @Test
    public void OrderstatusDetails(){
        int orderplaceid = OrderFlow.orderplaceid; // directly referencing the Static variable created in some other class
        Response res =
        given()
                .log().all()
                .header("Authorization" , "Bearer " + accesstoken)
                //.queryParam("customerId",54295)
                .queryParam("orderId",orderplaceid)
                .contentType("application/json")
                .when()
                .get("/fetchOrderStatusDetails")
                .then()
                .statusCode(200)
                .log().all()
                .extract().response();

        SoftAssert softAssert = new SoftAssert();


        softAssert.assertEquals(res.jsonPath().getInt("responseData.orderId"),orderplaceid,"Incorrect order id");
        softAssert.assertNotNull(res.jsonPath().get("responseData.deliveryBy"),"Delivery date is missing");
        softAssert.assertNotNull(res.jsonPath().get("responseData.orderStatusTitle"), "Status title is missing");
        softAssert.assertEquals(res.jsonPath().getString("responseData.pageTitle"),"Order #"+orderplaceid,"Page title mismatch");
        softAssert.assertNotNull(res.jsonPath().get("responseData.orderStatusTitle"),"Order Status title is missing");
        softAssert.assertNotNull(res.jsonPath().get("responseData.deliveryDate"),"Delivery date is empty");
        softAssert.assertNotNull(res.jsonPath().get("responseData.orderDate"),"Order date is empty");

        softAssert.assertAll();
    }

    @Test(alwaysRun = true)
    public void Invalidorderid(){
        Response res =
                given()
                        .log().all()
                        .header("Authorization", "Bearer " + accesstoken)
                        .contentType("application/josn")
                        .queryParam("orderId",12212090)
                        .when()
                        .get("/fetchOrderStatusDetails")
                        .then()
                        //.statusCode(400)
                        .extract().response();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(res.jsonPath().get("message"),"Invalid access for the order.");
        softAssert.assertEquals(res.jsonPath().getInt("statusCode"),400);
        softAssert.assertAll();
    }

    @Test(alwaysRun = true)
    public void NoAccessToken(){
        int orderplaceid = OrderFlow.orderplaceid;
        Response res =
                given()
                        .contentType("application/json")
                        .queryParam("orderId",orderplaceid)
                        .when()
                        .get("/fetchOrderStatusDetails")
                        .then()
                        .statusCode(401)
                        .extract().response();

    }
}

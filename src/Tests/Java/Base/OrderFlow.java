package Java.Base;

import Models.MedicineData;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderFlow {

    String accesstoken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI1NDI5NSIsImVudGl0eVR5cGUiOiJJRCIsInJvbGVMa" +
            "XN0IjpbIlJPTEVfQ1VTVE9NRVIiXSwiaXNzIjoiY29tLnRydWVtZWRzLmF1dGhfc2VydmljZSIsImV4cCI6MTc4NDYxMDIxMiwiaWF0I" +
            "joxNzg0NTIzODEyfQ.aFdzOnPK1VOM26nqamkwCEHDV7JIYhYYTLx60RX6WweFvxFsA4o-JKYt2vZbXjjGKGi3h0JfeBjLWqp58Zouk9td" +
            "3FGAvl-pOBmuCW7ENAraccKDLGSZl85ogOal1ixxdtt_ujnqebB9Vt_6J9N2K1mw6bs3nKaFiB_x3Horot79EViykgsj2fsagzNvmZ8efny" +
            "iUJTsc-p4QUMKR8aXUAI2qN7fdPhgvk52uZZ--nBbX30Y8Bd0DT4An1O5QkgqTwGQptIOjpVkJixW2bCdP01nKP4BaWXodCeJllYKG9ZpM-" +
            "XAH5Y8f9RBxdYAQ79dGdazNKE5bUG5P1BUJ6B-RQ";

public static int orderplaceid;


    @BeforeClass
    public void Setup(){
        RestAssured.baseURI = "https://stage-ims.truemedsapi.in";
    }

    @Test(description = "Adding medicine in cart")
    public void AddMedicine(){
        RestAssured.basePath="/OrderManagementService/v1/";
        MedicineData data = new MedicineData();
        data.setMedicineName("Zandu Balm 8Ml");
        data.setProductCode("TM-BAGE1-000046");
        data.setCxAcceptedSubs(false);
        data.setCxOrgAdded(true);
        data.setKeepOrg(false);
        data.setQuantity(2);

        // Wrapping the data in list as server expects the data in lits
        List<MedicineData> requestbody = new ArrayList<>();
        requestbody.add(data); // adding the data

        Response res =
        given()
                //.log().all()
                .header("Authorization","Bearer " +accesstoken )
                .contentType("application/json")
                .queryParam("customerId",54295)
                .queryParam("pincode",400079)
                .queryParam("addressId",5277128)
                .queryParam("orderId",0)
                .body(requestbody)
                .when()
                .post("saveMedsAndCreateOrder")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

         orderplaceid = res.jsonPath().get("responseData.orderId");
        System.out.println(orderplaceid);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(res.jsonPath().get("responseData.orderId"),"OrderID is not generated");
        softAssert.assertNotNull(res.jsonPath().get("responseData.subOrderId"),"SubOrderid is missing");
        softAssert.assertAll();
    }

    @Test(description = "Placing the order of added medicine")
    public void OrderPlacement(){
        RestAssured.basePath = "CustomerService/";
        Response res =
        given()
                .log().all()
                .header("Authorization","Bearer " + accesstoken)
                .contentType("application/json")
                .queryParam("orderId" , orderplaceid)
                .queryParam("paymentId",17)
                .queryParam("offerId",0)
                .body("{\n" +
                        "    \"source\": \"WEBSITE\",\n" +
                        "    \"version\": \"TM_WEBSITE_V_4.4.1\",\n" +
                        "    \"type\": \"Order\",\n" +
                        "    \"entityId\": " + orderplaceid + ",\n" +
                        "    \"accepted\": true,\n" +
                        "    \"policy\": \"Communication policy\"\n" +
                        "}")
                .when()
                .post("v2/confirmOrder")
                .then()
                .log().all()
                .statusCode(500)
                .extract().response();
    }
}

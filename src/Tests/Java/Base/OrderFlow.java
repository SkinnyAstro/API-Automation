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

    String accesstoken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI1NDI5NSIsImVudGl0eVR5cGUiOiJJRCIsInJvbGVMaXN0IjpbIl" +
            "JPTEVfQ1VTVE9NRVIiXSwiaXNzIjoiY29tLnRydWVtZWRzLmF1dGhfc2VydmljZSIsImV4cCI6MTc4NDU1ODQ3MiwiaWF0IjoxNzg0NDcyMDcyfQ.AI" +
            "__XjmovmCs0UPIUOBp39kB0l7MpTkd-k_Ht8ZUQyAQ3aGRdEGVniLc-tVIgmVYhHM_xQun0B4rdvh_Dhr_dcAkA0Bt-wouk0XyBYR0DJ30bVLDab9WgJyv" +
            "-MDf9XB9Nb7giS4lBDQLujC0e506_b-kQgR32mgGia_LWEQ7IkdbbV4GJfX6mGsNJObiYJK4ij4xuvn7TSdgE25mYsAamLdwjH6F5Fo1sp7uLvIa2zJDGro" +
            "7AcOxkKwQVqWlw1OYXy2ri_IRvZO3f1eeQBWI19Rx_pAG-SbUx71CWIjYg_GmXJvIpZOLBICIHLnGvI9G84Yv2cLqanoXrN6gmWjKgw";

    int orderplaceid;

    @BeforeClass
    public void Setup(){
        RestAssured.baseURI = "https://stage-ims.truemedsapi.in";
    }

    /*
   Save meds is called when adding medicine, Query param required could be customer id, pincode, addressID
   cxAcceptedSubs: false
   cxOrgAdded: false
   isKeepOrg: false
   medicineName: "Zandu Balm 8Ml"
   productCode: "TM-BAGE1-000046"
   quantity: 1
     */

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

    @Test(description = "Placing the order of added medicine",dependsOnMethods = "AddMedicine")
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
                .statusCode(200)
                .extract().response();
    }
}

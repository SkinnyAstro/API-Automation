package Java.Base;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.RestAssured.given;

public class OrdertStatus {

    String accesstoken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWI" +
            "iOiI1NDI5NSIsImVudGl0eVR5cGUiOiJJRCIsInJvbGVMaXN0IjpbIlJPTEV" +
            "fQ1VTVE9NRVIiXSwiaXNzIjoiY29tLnRydWVtZWRzLmF1dGhfc2VydmljZSIsImV" +
            "4cCI6MTc4NDQ3MjE1MCwiaWF0IjoxNzg0Mzg1NzUwfQ.hWLvKwosxfStGQYssJIrUZ68" +
            "VyLS6NiC61uoa-9rkMGrls4emWZ5N9BW42ewoSUdCC7WAns2ejhiNc7l3a84N0usHgzss" +
            "D5h8Xam1uZzUeav-gcyLSzVMNHwGej4pu4CE4NOQQ1KpFlAEOVFZ_Dpfoin9Vas" +
            "ZZ9QMRfmfR4iOV_Dx6yVEJyiKM71oJ-W1lE-t-XCmymC4hNEVLcvSOF5qSoeX" +
            "4K237HjIAMasA3psmXwitoMF48TmvY9Hhsar274OteyhOXwrKoZqoY3ZduW_" +
            "LxmZ7OCSvODm4MrxhGvGAtS8vKsdx-1WBRSQiatz_AzzZ2jdRl8kD50tztw2jpRPA";

    @BeforeClass
    public void SetUp(){
        RestAssured.baseURI = "https://stage-rng.truemedsapi.in";
        RestAssured.basePath= "/CustomerService";
    }

    @Test
    public void OrderstatusDetails(){
        Response res =
        given()
                .log().all()
                .header("Authorization" , "Bearer " + accesstoken)
                //.queryParam("customerId",54295)
                .queryParam("orderId",1877514)
                .contentType("application/json")
                .when()
                .get("/fetchOrderStatusDetails")
                .then()
                .statusCode(200)
                //.log().all()
                .extract().response();

        SoftAssert softAssert = new SoftAssert();


        softAssert.assertEquals(res.jsonPath().getInt("responseData.orderId"),1877514,"Incorrect order id");
        softAssert.assertNotNull(res.jsonPath().get("responseData.deliveryBy"),"Delivery date is missing");
        softAssert.assertNotNull(res.jsonPath().get("responseData.orderStatusTitle"), "Status title is missing");
        softAssert.assertAll();



    }
}

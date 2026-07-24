package Java.Base;

import Java.Base.Base.BaseTest;
import Java.Base.Helper.Medicinehelper;
import POJO.MedicineData;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderFlow extends BaseTest {

   private Medicinehelper medicinehelper;
    //private int orderplaceid;

public static int orderplaceid;

// Option 1 using the addMedicine()
    @BeforeClass(alwaysRun = true)
   public void initHelpers(){
        medicinehelper = new Medicinehelper(accesstoken);
    }

    @Test
    public void addMedicine(){
        Response res = medicinehelper.addMedicine("Zandu Balm 8ml","TM-BAGE1-000046",54295,400079,5277128);
        res.then().statusCode(200);
        orderplaceid = res.jsonPath().getInt("responseData.orderId");
        System.out.println("Order ID " + orderplaceid);
    }




//    @Test(description = "Adding medicine in cart")
//    public void AddMedicine(){
//        RestAssured.basePath="/OrderManagementService/v1/";
//        MedicineData data = new MedicineData();
//        data.setMedicineName("Zandu Balm 8Ml");
//        data.setProductCode("TM-BAGE1-000046");
//        data.setCxAcceptedSubs(false);
//        data.setCxOrgAdded(true);
//        data.setKeepOrg(false);
//        data.setQuantity(2);
//
//        // Wrapping the data in list as server expects the data in lits
//        List<MedicineData> requestbody = new ArrayList<>();
//        requestbody.add(data); // adding the data
//
//        Response res =
//        given()
//                //.log().all()
//                .header("Authorization","Bearer " +accesstoken )
//                .contentType("application/json")
//                .queryParam("customerId",54295)
//                .queryParam("pincode",400079)
//                .queryParam("addressId",5277128)
//                .queryParam("orderId",0)
//                .body(requestbody)
//                .when()
//                .post("saveMedsAndCreateOrder")
//                .then()
//                .log().all()
//                .statusCode(200)
//                .extract().response();
//
//         orderplaceid = res.jsonPath().get("responseData.orderId");
//        System.out.println(orderplaceid);
//
//        SoftAssert softAssert = new SoftAssert();
//        softAssert.assertNotNull(res.jsonPath().get("responseData.orderId"),"OrderID is not generated");
//        softAssert.assertNotNull(res.jsonPath().get("responseData.subOrderId"),"SubOrderid is missing");
//        softAssert.assertAll();
//    }
//
//    @Test(description = "Placing the order of added medicine")
//    public void OrderPlacement(){
//        RestAssured.basePath = "CustomerService/";
//        Response res =
//        given()
//                .log().all()
//                .header("Authorization","Bearer " + accesstoken)
//                .contentType("application/json")
//                .queryParam("orderId" , orderplaceid)
//                .queryParam("paymentId",17)
//                .queryParam("offerId",0)
//                .body("{\n" +
//                        "    \"source\": \"WEBSITE\",\n" +
//                        "    \"version\": \"TM_WEBSITE_V_4.4.1\",\n" +
//                        "    \"type\": \"Order\",\n" +
//                        "    \"entityId\": " + orderplaceid + ",\n" +
//                        "    \"accepted\": true,\n" +
//                        "    \"policy\": \"Communication policy\"\n" +
//                        "}")
//                .when()
//                .post("v2/confirmOrder")
//                .then()
//                .log().all()
//                .statusCode(500)
//                .extract().response();
//    }
}

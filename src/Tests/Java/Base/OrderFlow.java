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

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;

public class OrderFlow extends BaseTest {

   private Medicinehelper medicinehelper;
    //private int orderplaceid;

    int orderId;

public static int orderplaceid;

// Option 1 using the addMedicine()
    @BeforeClass(alwaysRun = true)
   public void initHelpers(){
        medicinehelper = new Medicinehelper(accesstoken);
    }

//    @Test
//    public void addMedicine(){
//        Response res = medicinehelper.addMedicine("Zandu Balm 8ml","TM-BAGE1-000046",54295,400079,5277128);
//        res.then().statusCode(200);
//        orderplaceid = res.jsonPath().getInt("responseData.orderId");
//        System.out.println("Order ID " + orderplaceid);
//    }

    @Test(description = "Collecting the orderid")
    public void GetOrderId(){
        orderId  = medicinehelper.GetOrderid("Zandu Balm 8ml","TM-BAGE1-000046",54295,400079,5277128);
        System.out.println("Order Id " + orderId);
    }

    @Test(dependsOnMethods = "GetOrderId")
    public void Placement(){
       Response res =  medicinehelper.OrderPlace();
        RestAssured.expect().statusCode(200);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(res.jsonPath().get("message"),"Order confirmed successfully for orderId :" + orderId);

    }

    @Test(dependsOnMethods = "Placement")
    public void OrderStatusVerification(){
        Response res = medicinehelper.GetOrderStatus(orderId);
        RestAssured.expect().statusCode(200);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(res.jsonPath().get("responseData.deliveryBy"),"Delivery date is missing");
        softAssert.assertNotNull(res.jsonPath().get("responseData.orderStatusTitle"), "Status title is missing");
        softAssert.assertEquals(res.jsonPath().getString("responseData.pageTitle"),"Order #"+orderId,"Page title mismatch");
        softAssert.assertNotNull(res.jsonPath().get("responseData.deliveryDate"),"Delivery date is empty");
        softAssert.assertNotNull(res.jsonPath().get("responseData.orderDate"),"Order date is empty");
        softAssert.assertAll();


    }

}

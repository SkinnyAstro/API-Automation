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

//    @Test
//    public void addMedicine(){
//        Response res = medicinehelper.addMedicine("Zandu Balm 8ml","TM-BAGE1-000046",54295,400079,5277128);
//        res.then().statusCode(200);
//        orderplaceid = res.jsonPath().getInt("responseData.orderId");
//        System.out.println("Order ID " + orderplaceid);
//    }

    @Test
    public void GetOrderId(){
       int orderId  = medicinehelper.GetOrderid("Zandu Balm 8ml","TM-BAGE1-000046",54295,400079,5277128);
        System.out.println("Order Id " + orderId);
    }

}

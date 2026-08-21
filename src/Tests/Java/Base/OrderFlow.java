package Java.Base;

import Java.Base.Base.BaseTest;
import Java.Base.Helper.Customerhelper;
import Java.Base.Helper.Medicinehelper;
import POJO.MedicineData;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.SkipException;


import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class OrderFlow extends BaseTest {

    private Medicinehelper medicinehelper;
    private Customerhelper customerhelper;

    int orderId;
    float tmcash;
    public static int orderplaceid;
    double rewardsBeforePlacement;


    @BeforeClass(alwaysRun = true)
    public void initHelpers() {
        customerhelper = new Customerhelper(accesstoken);
        medicinehelper = new Medicinehelper(accesstoken);
    }

    @Test(description = "Collecting the orderid")
    public void GetOrderId() {
        orderId = medicinehelper.GetOrderid("Zandu Balm 8ml", "TM-BAGE1-000046", 54685, 421301, 5277926);
        System.out.println("Order Id " + orderId);
    }

    @Test(description = "Applying rewards on the order",dependsOnMethods = "GetOrderId")
    public void applyRewardsonOrder(){

        Response customerdetails = customerhelper.getCustomerDetails();
        tmcash = customerdetails.jsonPath().getFloat("TmCash");
        System.out.println("Customer has : " + tmcash);

        if (tmcash <=0){
            throw new SkipException("Customer has no tm-cash, skipping reward related test cases");
        }

        Response res = medicinehelper.applyTmcash(orderId,true);
        res.then()
                .log().all()
                .statusCode(200)
                .body("statusCode", equalTo(200), "statusValue", equalTo("OK"))
                .body("responseData.calculateTmRewards", equalTo(true));

    }

    @Test(description = "See if Rewards is visible in Bill details",dependsOnMethods = "applyRewardsonOrder")
    public void Rewardsverificationinbilldetails() throws InterruptedException {
        Response rewardsdetails = medicinehelper.getBilldetails(orderId);
        rewardsBeforePlacement = rewardsdetails.jsonPath().getDouble("responseData.tmCash");
        double sellingPrice = rewardsdetails.jsonPath().getDouble("responseData.sellingPrice");
        double expectedrewards = sellingPrice * 0.10;

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(rewardsBeforePlacement > 0);
        softAssert.assertEquals(rewardsBeforePlacement,expectedrewards,0.01,"Expected reward should be that 10% of selling price, we expected " + expectedrewards + "but we got" + rewardsBeforePlacement);

        softAssert.assertAll();

    }

    @Test(dependsOnMethods = "Rewardsverificationinbilldetails",alwaysRun = true)
    public void Placement() {
        Response res = medicinehelper.OrderPlace();;
        res.then().statusCode(200);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(res.jsonPath().get("message"), "Order confirmed successfully for orderId :" + orderId);

    }

    @Test(dependsOnMethods = "Placement")
    public void OrderStatusVerification() {
        Response res = medicinehelper.GetOrderStatus(orderId);

        //RestAssured.expect().statusCode(400);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(res.jsonPath().get("responseData.deliveryBy"), "Delivery date is missing");
        softAssert.assertNotNull(res.jsonPath().get("responseData.orderStatusTitle"), "Status title is missing");
        softAssert.assertEquals(res.jsonPath().getString("responseData.pageTitle"), "Order #" + orderId, "Page title mismatch");
        softAssert.assertNotNull(res.jsonPath().get("responseData.deliveryDate"), "Delivery date is empty");
        softAssert.assertNotNull(res.jsonPath().get("responseData.orderDate"), "Order date is empty");
        softAssert.assertAll();


    }

    @Test(description = "Verify Rewards after placement",dependsOnMethods = {"OrderStatusVerification","Rewardsverificationinbilldetails"})
    public void checkRewardsPostPlacement (){

        Response postplacementrewards = medicinehelper.getOrderDetails(orderId);
        double rewardsAfterPlacement = postplacementrewards.jsonPath().getDouble("finalCalcAmt.tmCash");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(rewardsBeforePlacement,rewardsAfterPlacement);

        softAssert.assertAll();

    }

}

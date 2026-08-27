package Java.Base;

import Java.Base.Base.BaseTest;
import Java.Base.Config.ConfigManager;
import Java.Base.DataProviders.MedicineDataProvider;
import Java.Base.Helper.Customerhelper;
import Java.Base.Helper.Medicinehelper;
import POJO.CustomerDetailsResponse;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.SkipException;

import java.io.ObjectInputFilter;

import static org.hamcrest.Matchers.equalTo;


@Slf4j
public class OrderFlow extends BaseTest {

    private Medicinehelper medicinehelper;
    private Customerhelper customerhelper;

    int orderId;
    double tmcash;
    double rewardsBeforePlacement;


    @BeforeClass(alwaysRun = true)
    public void initHelpers() {
        customerhelper = new Customerhelper(accesstoken);
        medicinehelper = new Medicinehelper(accesstoken);
    }

    @Test(description = "Collecting the orderid")
    public void GetOrderId() {
        orderId = medicinehelper.GetOrderid(
                ConfigManager.get("default.medicine.name"),
                ConfigManager.get("default.product.code"),
                Integer.parseInt(ConfigManager.get("test.customer.id")),
                Integer.parseInt(ConfigManager.get("default.pincode")),
                Integer.parseInt(ConfigManager.get("default.address.id")));
        System.out.println("Order Id " + orderId);
    }

    @Test(description = "Applying rewards on the order",dependsOnMethods = "GetOrderId")
    public void applyRewardsonOrder(){

        CustomerDetailsResponse customerdetails = customerhelper.getCustomerDetails();
        tmcash =customerdetails.getTmCash();
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
    public void Rewardsverificationinbilldetails()  {

        System.out.println("Rewards verification started");
        System.out.println(orderId);

        Response rewardsdetails = medicinehelper.getBilldetails(orderId);
        rewardsBeforePlacement = rewardsdetails.jsonPath().getDouble("responseData.tmCash");
        System.out.println(rewardsBeforePlacement);
        double sellingPrice = rewardsdetails.jsonPath().getDouble("responseData.sellingPrice");
        double expectedrewards = sellingPrice * 0.10;

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(rewardsBeforePlacement > 0);
        softAssert.assertEquals(rewardsBeforePlacement,expectedrewards,0.01,"Expected reward should be that 10% of selling price, we expected " + expectedrewards + "but we got" + rewardsBeforePlacement);

        softAssert.assertAll();

    }

    @Test(description = "Removing rewards post application", dependsOnMethods = "Rewardsverificationinbilldetails")
    public void removeRewards(){
        Response res = medicinehelper.applyTmcash(orderId,false);
        res.then()
                .statusCode(200)
                .body("responseData.calculateTmRewards",equalTo(false));

        Response billdetails = medicinehelper.getBilldetails(orderId);
        double rewardsafterremoval = billdetails.jsonPath().getDouble("responseData.tmCash");
        Assert.assertEquals(rewardsafterremoval,0.0,0.1,"We expected rewards after removal to be 0, but we got " + rewardsafterremoval);


    }

    @Test(description = "Reapplying rewards after removal", dependsOnMethods = "removeRewards")
    public void reapplyRewards(){

        Response res = medicinehelper.applyTmcash(orderId,true);
        res.then()
                .statusCode(200)
                .body("responseData.calculateTmRewards" , equalTo(true));
        Response rewardsdetails = medicinehelper.getBilldetails(orderId);
        rewardsBeforePlacement = rewardsdetails.jsonPath().getDouble("responseData.tmCash");

    }

    @Test(dependsOnMethods = "reapplyRewards",alwaysRun = true)
    public void Placement() {
        Response res = medicinehelper.OrderPlace(orderId);
        res.then().statusCode(200);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(res.jsonPath().get("message"), "Order confirmed successfully for orderId :" + orderId);

    }



    @Test(dependsOnMethods = "Placement")
    public void OrderStatusVerification() {
        Response res = medicinehelper.GetOrderStatus(orderId);
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
        softAssert.assertEquals(rewardsBeforePlacement,rewardsAfterPlacement,0.01,"We expected rewards to be " + rewardsBeforePlacement + " but we ended up getting " + rewardsAfterPlacement);

        softAssert.assertAll();

    }


    @Test(dataProvider = "MedicineData", description =  " Verify placing an order with multiple medicine",dependsOnMethods = "checkRewardsPostPlacement",dataProviderClass = MedicineDataProvider.class)
    public void VerifyOrderCreationMultiplemedicine(String medicinename, String medicinecode){
        System.out.println("verifyOrderCreationForMultipleMedicines STARTED for" + medicinename);
        int newOrderid = medicinehelper.GetOrderid(
                medicinename,
                medicinecode,
                Integer.parseInt(ConfigManager.get("test.customer.id")),
                Integer.parseInt(ConfigManager.get("default.pincode")),
                Integer.parseInt(ConfigManager.get("default.address.id")));

            Assert.assertTrue(newOrderid > 0,"Expected valid order id to be create for medicine " + medicinename);
    }

}

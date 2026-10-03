package flows;

import core.BaseTest;
import core.ConfigManager;
import domain.cart.dataproviders.MedicineDataProvider;
import domain.cart.CartService;
import domain.customer.CustomerService;
import domain.order.OrderService;
import domain.cart.pojo.ApplyRewardsResponse;
import domain.cart.pojo.BillDetailsResponse;
import domain.customer.pojo.CustomerDetailsResponse;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.SkipException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;


public class OrderFlow extends BaseTest {

    private CartService cartService;
    private OrderService orderService;
    private CustomerService customerService;

    int orderId;
    double tmcash;
    double rewardsBeforePlacement;


    @BeforeClass(alwaysRun = true)
    public void initHelpers() {
        customerService = new CustomerService(accesstoken);
        cartService = new CartService(accesstoken);
        orderService = new OrderService(accesstoken);
    }

    @Test(description = "Collecting the orderid")
    public void GetOrderId() {
        orderId = cartService.GetOrderid(
                ConfigManager.get("default.medicine.name"),
                ConfigManager.get("default.product.code"),
                Integer.parseInt(ConfigManager.get("test.customer.id")),
                Integer.parseInt(ConfigManager.get("default.pincode")),
                Integer.parseInt(ConfigManager.get("default.address.id")));
        System.out.println("Order Id " + orderId);
    }

    @Test(description = "Applying rewards on the order",dependsOnMethods = "GetOrderId")
    public void applyRewardsonOrder(){

        CustomerDetailsResponse customerdetails = customerService.getCustomerDetails();
        tmcash =customerdetails.getTmCash();
        System.out.println("Customer has : " + tmcash);

        if (tmcash <=0){
            throw new SkipException("Customer has no tm-cash, skipping reward related test cases");
        }

        ApplyRewardsResponse res = cartService.applyTmcash(orderId,true);

                assertThat(res.getStatusCode()).isEqualTo(200);
                assertThat(res.getStatusValue()).isEqualTo("OK");
                assertThat(res.getResponseData().isCalculateTmRewards()).isTrue();


    }

    @Test(description = "See if Rewards is visible in Bill details",dependsOnMethods = "applyRewardsonOrder")
    public void Rewardsverificationinbilldetails()  {

        System.out.println("Rewards verification started");
        System.out.println(orderId);

        BillDetailsResponse rewardsdetails = cartService.getBilldetails(orderId);
        rewardsBeforePlacement = rewardsdetails.getResponseData().getTmCash();
        System.out.println(rewardsBeforePlacement);
        double sellingPrice = rewardsdetails.getResponseData().getSellingPrice();
        double expectedrewards = sellingPrice * 0.10;

        SoftAssertions.assertSoftly(softly ->{
            softly.assertThat(rewardsBeforePlacement).isGreaterThan(0);
            softly.assertThat(rewardsBeforePlacement).isCloseTo(expectedrewards,within(0.01))
                    .withFailMessage("expected rewards to be 10% of selling price , we expected " + expectedrewards + "but we got " + rewardsBeforePlacement);
        });

    }

    @Test(description = "Removing rewards post application", dependsOnMethods = "Rewardsverificationinbilldetails")
    public void removeRewards(){
        ApplyRewardsResponse res = cartService.applyTmcash(orderId,false);

        assertThat(res.getStatusCode()).isEqualTo(200);
        assertThat(res.getResponseData().isCalculateTmRewards()).isFalse();


        BillDetailsResponse billdetails = cartService.getBilldetails(orderId);
        double rewardsafterremoval = billdetails.getResponseData().getTmCash();
        Assert.assertEquals(rewardsafterremoval,0.0,0.1,"We expected rewards after removal to be 0, but we got " + rewardsafterremoval);
        assertThat(rewardsafterremoval).isCloseTo(0.0,within(0.1))
                .withFailMessage("Expected rewards after removal to be 0");


    }

    @Test(description = "Reapplying rewards after removal", dependsOnMethods = "removeRewards")
    public void reapplyRewards(){

        ApplyRewardsResponse res = cartService.applyTmcash(orderId,true);
        assertThat(res.getStatusCode()).isEqualTo(200);
        assertThat(res.getResponseData().isCalculateTmRewards()).isTrue();

        BillDetailsResponse rewardsdetails = cartService.getBilldetails(orderId);
        rewardsBeforePlacement = rewardsdetails.getResponseData().getTmCash();

    }

    @Test(dependsOnMethods = "reapplyRewards",alwaysRun = true)
    public void Placement() {
        Response res = orderService.OrderPlace(orderId);
        res.then().statusCode(200);
        assertThat(res.jsonPath().getString("message")).isNotBlank();

    }



    @Test(dependsOnMethods = "Placement")
    public void OrderStatusVerification() {
        Response res = orderService.GetOrderStatus(orderId);
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat((Object) res.jsonPath().get("responseData.deliveryBy"))
                    .withFailMessage("Delivery date is missing")
                    .isNotNull();
            softly.assertThat((Object) res.jsonPath().get("responseData.orderStatusTitle"))
                    .withFailMessage("Status title is missing")
                    .isNotNull();
            softly.assertThat(res.jsonPath().getString("responseData.pageTitle"))
                    .withFailMessage("Page title mismatch")
                    .isEqualTo("Order #" + orderId);
            softly.assertThat((Object) res.jsonPath().get("responseData.deliveryDate"))
                    .withFailMessage("Delivery date is empty")
                    .isNotNull();
            softly.assertThat((Object) res.jsonPath().get("responseData.orderDate"))
                    .withFailMessage("Order date is empty")
                    .isNotNull();
        });


    }

    @Test(description = "Verify Rewards after placement",dependsOnMethods = {"OrderStatusVerification","Rewardsverificationinbilldetails"})
    public void checkRewardsPostPlacement (){

        Response postplacementrewards = orderService.getOrderDetails(orderId);
        double rewardsAfterPlacement = postplacementrewards.jsonPath().getDouble("finalCalcAmt.tmCash");

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(rewardsAfterPlacement)
                    .isCloseTo(rewardsBeforePlacement, within(0.01))
                    .withFailMessage("Expected rewards to be %s but got %s",
                            rewardsBeforePlacement, rewardsAfterPlacement);
        });

    }


    @Test(dataProvider = "MedicineData", description =  " Verify placing an order with multiple medicine",dependsOnMethods = "checkRewardsPostPlacement",dataProviderClass = MedicineDataProvider.class)
    public void VerifyOrderCreationMultiplemedicine(String medicinename, String medicinecode){
        System.out.println("verifyOrderCreationForMultipleMedicines STARTED for" + medicinename);
        int newOrderid = cartService.GetOrderid(
                medicinename,
                medicinecode,
                Integer.parseInt(ConfigManager.get("test.customer.id")),
                Integer.parseInt(ConfigManager.get("default.pincode")),
                Integer.parseInt(ConfigManager.get("default.address.id")));

            Assert.assertTrue(newOrderid > 0,"Expected valid order id to be create for medicine " + medicinename);
    }

}

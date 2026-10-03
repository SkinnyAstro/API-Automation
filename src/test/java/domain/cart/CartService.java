package domain.cart;

import core.ApiClient;
import domain.cart.pojo.ApplyRewardsResponse;
import domain.cart.pojo.BillDetailsResponse;
import domain.cart.pojo.MedicineData;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import java.util.ArrayList;
import java.util.List;



public class CartService extends ApiClient {


    public CartService(String accesstoken){
        super(accesstoken); // this access token is called up from the ApiClient flile
    }

    public Response addMedicine(String medicine,
                                String productCode,
                                int customerId,
                                int pincode,
                                int addressId){
        //RestAssured.basePath = "/OrderManagementService/v1/";

        // Building POJO object
        MedicineData data = new MedicineData();
        data.setMedicineName(medicine);
        data.setProductCode(productCode);
        data.setQuantity(1);
        data.setKeepOrg(false);
        data.setCxAcceptedSubs(false);
        data.setCxOrgAdded(true);

        // Wrap POJO in a list
        List<MedicineData> requestBody = new ArrayList<>();
        requestBody.add(data);

        return getRequestSpec()
                .queryParam("customerId",customerId)
                .queryParam("pincode",pincode)
                .queryParam("addressId",addressId)
                .queryParam("orderId",0)
                .body(requestBody)
                .when()
                .post("/OrderManagementService/v1/saveMedsAndCreateOrder")
                .then()
                .log().ifValidationFails()
                .extract().response();



    }

    // Getorder id Method ()
    public int GetOrderid(String medicine,
                          String productCode,
                          int customerId,
                          int pincode,
                          int addressId){
        Response res = addMedicine(medicine,productCode,customerId,pincode,addressId);

        // Extracting the orderID from the response

         int orderId = res.jsonPath().getInt("responseData.orderId");
        System.out.println("Order Id captured " + orderId);
        return orderId;
    }

    public ApplyRewardsResponse applyTmcash(int orderId, boolean calculateTmRewards){
        return getRequestSpec()
                .queryParam("orderId",orderId)
                .queryParam("calculateTmRewards",calculateTmRewards)
                .queryParam("versionName","9.7.0")
                .when()
                .post("/CustomerService/calculateTmRewards")
                .then()
                .log().ifValidationFails()
                .extract().as(ApplyRewardsResponse.class);
    }

    public BillDetailsResponse getBilldetails(int orderId){
        return getRequestSpec()
                .queryParam("orderId",orderId)
                .when()
                .get("/CustomerService/v1/cart/calculateBillDetailsforApp")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .extract().as(BillDetailsResponse.class);
    }


}

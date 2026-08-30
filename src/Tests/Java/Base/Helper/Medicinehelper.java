package Java.Base.Helper;

import Java.Base.Config.ConfigManager;
import POJO.ApplyRewardsResponse;
import POJO.BillDetailsResponse;
import POJO.ConfirmOrderRequest;
import POJO.MedicineData;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import java.util.ArrayList;
import java.util.List;



public class Medicinehelper {

    private  String accesstoken; // stores the token from test class

    //int customerId;

    public Medicinehelper(String accesstoken){
        this.accesstoken = accesstoken; // this.accesstoken is class variable and accesstoken is value passed from testclass
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

        return given()
                .log().ifValidationFails()
                .header("Authorization","Bearer " + accesstoken)
                .contentType("application/json")
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
        return given()
                .log().ifValidationFails()
                .header("Authorization","Bearer "+ accesstoken)
                .contentType("application/json")
                .queryParam("orderId",orderId)
                .queryParam("calculateTmRewards",calculateTmRewards)
                .queryParam("versionName","9.7.0")
                .when()
                .post("/CustomerService/calculateTmRewards")
                .then()
                .log().ifValidationFails()
                .extract().as(ApplyRewardsResponse.class);
    }

    public Response  OrderPlace(int placementorderId){
        ConfirmOrderRequest requestbody = new ConfirmOrderRequest();
        requestbody.setEntityId(placementorderId);


        return given()
                .log().ifValidationFails()
                .header("Authorization","Bearer " + accesstoken)
                .contentType("application/json")
                .queryParam("orderId",placementorderId)
                .queryParam("paymentId", ConfigManager.get("payment.id"))
                .queryParam("offerId",0)
                .queryParam("customerId",ConfigManager.get("test.customer.id"))
                .queryParam("paymentMethod", ConfigManager.get("payment.method"))
                .queryParam("paymentMethodId", ConfigManager.get("payment.method.id"))
                .queryParam("orderConfirmSrc","IOS")
                .queryParam("sourceVersion","v3.0.4")
                .queryParam("checkAutoConfirmEligibility",true)
                .queryParam("pageName","order_summary")
                .queryParam("versionName","3.0.4")
                .body(requestbody)
                .when()
                .post("CustomerService/v2/confirmOrder")
                .then()
                .log().ifValidationFails()
                .extract().response();
    }

    public Response GetOrderStatus(int orderId){

        //RestAssured.basePath = "CustomerService/";
        return given()
                .log().ifValidationFails()
                .header("Authorization","Bearer " + accesstoken)
                .contentType("application/json")
                .queryParam("orderId",orderId)
                .when()
                .get("CustomerService/fetchOrderStatusDetails")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .extract().response();
    }

    public BillDetailsResponse getBilldetails(int orderId){
        return given()
                .log().ifValidationFails()
                .header("Authorization","Bearer " + accesstoken)
                .contentType("application/json")
                .queryParam("orderId",orderId)
                .when()
                .get("/CustomerService/v1/cart/calculateBillDetailsforApp")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .extract().as(BillDetailsResponse.class);
    }

    public Response getOrderDetails(int orderId){
        return given()
                .log().ifValidationFails()
                .header("Authorization","Bearer " + accesstoken)
                .contentType("application/json")
                .queryParam("orderId",orderId)
                .queryParam("customerId",ConfigManager.get("test.customer.id"))
                .when()
                .get("/CustomerService/getOrderDetails")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .extract().response();
    }


}

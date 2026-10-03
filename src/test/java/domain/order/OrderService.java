package domain.order;

import core.ApiClient;
import core.ConfigManager;
import domain.order.pojo.ConfirmOrderRequest;
import io.restassured.response.Response;

public class OrderService extends ApiClient {

    public OrderService(String accesstoken){
        super(accesstoken);
    }

    public Response  OrderPlace(int placementorderId){
        ConfirmOrderRequest requestbody = new ConfirmOrderRequest();
        requestbody.setEntityId(placementorderId);


        return getRequestSpec()
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
        return getRequestSpec()
                .queryParam("orderId",orderId)
                .when()
                .get("CustomerService/fetchOrderStatusDetails")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .extract().response();
    }

    public Response getOrderDetails(int orderId){
        return getRequestSpec()
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

package Java.Base.Helper;

import POJO.MedicineData;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import java.util.ArrayList;
import java.util.List;



public class Medicinehelper {

    private  String accesstoken; // stores the token from test class

    public Medicinehelper(String accesstoken){
        this.accesstoken = accesstoken; // this.accesstoken is class variable and accesstoken is value passed from testclass
    }

    public Response addMedicine(String medicine,
                                String productCode,
                                int customerId,
                                int pincode,
                                int addressId){
        RestAssured.basePath = "/OrderManagementService/v1/";

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
                .log().all()
                .header("Authorization","Bearer " + accesstoken)
                .contentType("application/json")
                .queryParam("customerId",customerId)
                .queryParam("pincode",pincode)
                .queryParam("addressId",addressId)
                .queryParam("orderId",0)
                .body(requestBody)
                .when()
                .post("saveMedsAndCreateOrder")
                .then()
                .log().all()
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


}

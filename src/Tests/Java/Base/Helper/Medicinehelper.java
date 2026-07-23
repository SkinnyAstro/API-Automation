package Java.Base.Helper;

import POJO.MedicineData;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import org.joda.time.chrono.EthiopicChronology;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.path.json.JsonPath.given;

public class Medicinehelper {

    private  String accesstoken; // stores the token from test class

    public Medicinehelper(String accesstoken){
        this.accesstoken = accesstoken; // this.accesstoken is class variable and accesstoken is value passed from testclass
    }

    public Response addMedicine(String medicine,
                                String productCode,
                                int CustomerId,
                                int pincode,
                                int addressId){
        RestAssured.basePath = "";
        MedicineData data = new MedicineData();
        data.setMedicineName(medicine);
        data.setProductCode(productCode);
        data.setQuantity(1);
        data.setKeepOrg(false);
        data.setCxAcceptedSubs(false);
        data.setCxOrgAdded(true);

        List<MedicineData> requestBody = new ArrayList<>();
        requestBody.add(data);

        return given()
                .log().all()
                .header("Authorization","Bearer " + accesstoken)
                .contentType("application/json")
                .
    }


}

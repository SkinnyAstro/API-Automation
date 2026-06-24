package Java.Base;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class SearchSuggestion {

    String BrandName = "Telma";
    String CompanyName = "Glenmark";

    @BeforeClass
    public void SetUp(){
        RestAssured.baseURI = "https://stage-dev.truemedsapi.in";
        RestAssured.basePath="/SearchService";
    }

    @Test(description = "Verifying Search Result data via Brand keyword")
    public void BrandSearch(){

        Response res =
        given()
                .queryParam("searchString",BrandName)
                .when()
                .get("/getSearchSuggestion")
                .then()
                .statusCode(200)
                .body("responseData.productList[0].product.skuName",equalTo("Telma 40 Tablet 30"))
                .extract().response();

        List<String> skuNames = res.jsonPath().getList("responseData.productList.product.skuName");
        System.out.println(skuNames);

        //String SearchKeyword = "Telma";

        /*
        For this type of for loop we assigning all the SkuName in the response the value of 'sku'
        and after that we are converting the sku and the SearchKeyword in to lowercase and using the assert
        statement we are cross checking whether the sku contains the keyword
        */

        for (String sku: skuNames){
            Assert.assertTrue(sku.toLowerCase().contains(BrandName.toLowerCase()),
            "SKU does not contain the keyword" + BrandName + ":" + sku );
        }
    }
    
    @Test(description = "Searching via Company name")
    public void CompanyNameSearch(){
        Response res =
                given()
                        .queryParam("searchString",CompanyName)
                        .when()
                        .get("/getSearchSuggestion")
                        .then()
                        .statusCode(200)
                        .extract().response();
        List<String> Names = res.jsonPath().getList("responseData.productList.product.manufacturerName");
        System.out.println(Names);



        for (String sku : Names){
            Assert.assertTrue(sku.toLowerCase().contains(CompanyName.toLowerCase()),
            "SKU does not contain the keyword " + CompanyName + ":" + sku);
        }
    }

    @Test(description = "MRP and Selling price should always be greater than 0")
    public void VerifyMRPandSellingprice(){
        Response res =
                given()
                        .queryParam("searchString",BrandName)
                        .when()
                        .get("/getSearchSuggestion")
                        .then()
                        .statusCode(200)
                        .extract().response();

        List<String>MRP = res.jsonPath().getList("responseData.productList.product.mrp");
        System.out.println(MRP);



    }

}

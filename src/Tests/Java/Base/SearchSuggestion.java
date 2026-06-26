package Java.Base;

import Models.UserDataProvider;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class SearchSuggestion {

    String BrandName = "Telma";
    String CompanyName = "Glenmark";

    @BeforeClass
    public void SetUp(){
        RestAssured.baseURI = "https://stage-rng.truemedsapi.in";
        RestAssured.basePath="/SearchService";
    }

    @Test(description = "Verifying Search Result data via Brand keyword", dataProvider ="searchData",dataProviderClass = UserDataProvider.class)
    public void BrandSearch(String value, String path){

        Response res =
        given()
                .queryParam("searchString",value)
                .when()
                .get("/getSearchSuggestion")
                .then()
                .statusCode(200)
                .extract().response();

        List<String> skuNames = res.jsonPath().getList(path);
        System.out.println(skuNames);

        //String SearchKeyword = "Telma";

        /*
        For this type of for loop we assigning all the SkuName in the response the value of 'sku'
        and after that we are converting the sku and the SearchKeyword in to lowercase and using the assert
        statement we are cross checking whether the sku contains the keyword
        */

        for (String sku: skuNames){
            Assert.assertTrue(sku.toLowerCase().contains(value.toLowerCase()),
            "SKU does not contain the keyword" + value + ":" + sku );
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

        List<Number>MRP = res.jsonPath().getList("responseData.productList.product.mrp");
        System.out.println(MRP);

        for (Number mrp: MRP){
           if (mrp.floatValue()>0){
               System.out.println("Valid MRP is displayed");
           }else {
               System.out.println("Invalid MRP" + mrp);
           }
        }


       }

    }



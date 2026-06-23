package Java.Base;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class SearchSuggestion {

    @BeforeClass
    public void SetUp(){
        RestAssured.baseURI = "https://stage-dev.truemedsapi.in";
        RestAssured.basePath="/SearchService";
    }

    @Test(description = "Verifying Search Result data")
    public void SearchResultData(){

        Response res =
        given()
                .queryParam("searchString","Telma")
                .when()
                .get("/getSearchSuggestion")
                .then()
                .statusCode(200)
                .body("responseData.productList[0].product.skuName",equalTo("Telma 40 Tablet 30"))
                .extract().response();

        List<String> skuNames = res.jsonPath().getList("responseData.productList.product.skuName");
        System.out.println(skuNames);
        //res.prettyPrint();

    }

}

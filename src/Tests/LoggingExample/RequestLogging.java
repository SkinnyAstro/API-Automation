package LoggingExample;
import Models.UserDataProvider;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RequestLogging {

    @BeforeClass
    public void Setup(){
        RestAssured.baseURI = "https://stage-rng.truemedsapi.in";
        RestAssured.basePath = "/SearchService";
    }

    @Test(description = "Logging the request",dataProvider = "searchData",dataProviderClass = UserDataProvider.class)
    public void Search(String value , String path){

        given()
                .log().parameters() // log when used after given is known as request logging
                .queryParam("searchString",value)
                .when()
                .get("/getSearchSuggestion")
                .then()
                .log().body() // log used after then statement is used for response logging
                .statusCode(200);

    }

}

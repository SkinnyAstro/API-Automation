package core;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected static String accesstoken = ConfigManager.get("access.token");

    @BeforeClass
    public void Setup(){
        RestAssured.baseURI = ConfigManager.get("base.url");
    }
}

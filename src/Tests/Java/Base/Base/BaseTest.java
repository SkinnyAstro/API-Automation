package Java.Base.Base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import Java.Base.Config.ConfigManager;

public class BaseTest {

    protected static String accesstoken = ConfigManager.get("access.token");

    @BeforeClass
    public void Setup(){
        RestAssured.baseURI = ConfigManager.get("base.url");
    }
}

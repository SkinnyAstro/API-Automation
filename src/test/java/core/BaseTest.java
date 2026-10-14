package core;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected static String accesstoken = ConfigManager.get("access.token");

    @BeforeClass
    public void Setup(){
        RestAssured.baseURI = ConfigManager.get("base.url");

        // keep the access token out of request logs printed on failures
        RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig().blacklistHeader("Authorization"));
    }
}

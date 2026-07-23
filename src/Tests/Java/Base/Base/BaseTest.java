package Java.Base.Base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected static String accesstoken = "";

    @BeforeClass
    public void Setup(){
        RestAssured.baseURI = "https://www.stage-dev.truemeds.in";
    }
}

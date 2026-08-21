package Java.Base.Base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected static String accesstoken = "eyJhbGciOiJIUzUxMiIsInppcCI6IkRFRiJ9.eNqqViouTVKyUrI0MDCxNDI0MjJT0lEqzi8tSk4Fiob4xjv6uQT5e7rEh1nqmesZACWL8nNSfTKLS5SsopWC_H" +
            "1c451Dg0P8fV2DlGJ1lFIrCpSsDC0MLSyMDY1MDXSUMhOBCg3NLcyNLE2BArUAAAAA__8.s99GJLj3Rf_-HrozV_3uBZPhOF-iFLEU-E9PJzGu-QXjpY5_GdXLTaoJUpvYyPbKTK3UBFYRIJtPEgxgolbNxw";

    @BeforeClass
    public void Setup(){
        RestAssured.baseURI = "https://stage-dev.truemedsapi.in";
    }
}

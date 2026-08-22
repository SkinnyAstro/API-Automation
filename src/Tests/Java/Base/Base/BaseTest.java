package Java.Base.Base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected static String accesstoken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI1NDY4NSIsImVudGl0eVR5cGUiOiJJRCIsInJvbGVMaXN0IjpbIlJPTEVfQ" +
            "1VTVE9NRVIiXSwiaXNzIjoiY29tLnRydWVtZWRzLmF1dGhfc2VydmljZSIsImV4cCI6MTc4NzQ2ODE0NSwiaWF0IjoxNzg3MzgxNzQ1fQ.XPjtsDkvKlLEhhb3dVtLIFCqa_4rv-jqZpr" +
            "iaznMwWbHHRapJiF9UOPPpJ_P3_Sz20K-_-sTeFZ320FQBaybtpGrGOEO6gpyPeQnydX7vLbTrR2lINnXciaVl_W3x7asXAerdszMLcRRHilg-8X2hO32hGUtiBKSERigiTfxdm1v6qIRZbsu6" +
            "pfBAMHq8PwxiklpwtrGlhUZM1Q0V6H65QUAscxJZ-Ah9c9yd7awaCHkOWbBs4wsec38wF163plgXJxGxGhdqIuq0u0FglqOgLFcXoZzx_ndiLjznRUpbN4mGc-sUW2jX8_2XInmqypU7qL_D64XyMnEPnSgKM2uqw";

    @BeforeClass
    public void Setup(){
        RestAssured.baseURI = "https://stage-dev.truemedsapi.in";
    }
}

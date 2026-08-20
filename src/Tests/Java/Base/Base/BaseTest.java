package Java.Base.Base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected static String accesstoken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI1NDMxMyIsImVudGl0eVR5cGUiOiJJRCIsInJvbGVMaX" +
            "N0IjpbIlJPTEVfQ1VTVE9NRVIiXSwiaXNzIjoiY29tLnRydWVtZWRzLmF1dGhfc2VydmljZSIsImV4cCI6MTc4NzI5NTI2MSwiaWF0IjoxNzg3MjA4ODYxfQ.LR5AX-9" +
            "lZm3BHaXNwMOc7Ar88vrToomr3S3Be0zeyTQocpcAbSmQOgq53bEWlWLS8Il_Kotj_PVPkY0gQzTyYC1e9RKQ5f6qSU3Q76_VIjCjcjFZwq3GaxOwGxZyYn4w7_SjKCbtxq8xHx" +
            "YOU8xU8lud4ClP0GPDwegX3fEen8WBlPJxoJq-Y_n-cf-4ucUtaWkGpkM6vj4HpVx65burnvdk76Ys2P1Xz3L_lKzZr96Z0vSach3BVmX2X4w9jdqmmrw6N384DNYz8VzvWVfRdCZovQzS" +
            "ePwAS00Q7ea2pcmIfrvtMdZPr33MRkcV8fowkLulXHvclKdFtWs-KWkq9g";

    @BeforeClass
    public void Setup(){
        RestAssured.baseURI = "https://stage-dev.truemedsapi.in";
    }
}

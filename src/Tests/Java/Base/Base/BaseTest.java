package Java.Base.Base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected static String accesstoken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI1NDI5NSIsImVudGl0eVR5cGUiOiJJRCIsInJvbGVMaXN0IjpbIlJPT" +
            "EVfQ1VTVE9NRVIiXSwiaXNzIjoiY29tLnRydWVtZWRzLmF1dGhfc2VydmljZSIsImV4cCI6MTc4NTQyNjUxOCwiaWF0IjoxNzg1MzQwMTE4fQ.lDw_OWare3c-ISBkc7IhC23qmQ6" +
            "2LgWxy03O3HzMcpz5TNZaAN0u4GkeBLRlgl6LBz2fFvCBt109vpjinNakz6dGAXR_zKwNkUgz5hHrfthwx1aN269oHwUinb4y_N7czWxGzlvMtl21po8i2Bdhn5kwEItjBmBGzYyQruI9" +
            "Lh4aWirlJxBUuEFH6EJrPiykuNLogCuuYB3gSgt-jHlVmL22omPg0zt6dglI3c4HVdp2QLqZRnySzVASpGZ6dx5fErSkc9VUPEbuZ02NfoiEiI6jD7hx5jt6IxqO6ZNdKbJfptR4E84YyBU6Xr" +
            "1IpOHPLXU9JGmKZVMTqBWSs41mRw";

    @BeforeClass
    public void Setup(){
        RestAssured.baseURI = "https://stage-portal.truemedsapi.in";
    }
}

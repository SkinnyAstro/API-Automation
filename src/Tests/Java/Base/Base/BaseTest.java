package Java.Base.Base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected static String accesstoken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI1NDI5NSIsImVudGl0eVR5cGUiOiJ" +
            "JRCIsInJvbGVMaXN0IjpbIlJPTEVfQ1VTVE9NRVIiXSwiaXNzIjoiY29tLnRydWVtZWRzLmF1dGhfc2VydmljZSIsImV4cCI6MTc4NDk3MTQxMiwiaWF0IjoxNzg0ODg1MDEyfQ.geapoCcYehqTCKdvoLcU4gxtaTaF352URTdWLAN9_GLaxSd836pOEyKt1SbMITXp0sMMQO5OxxujjvFKq-LQzUEbDekHowgduQ0EaKkMnaP2T_XUEXqRNL2yqlGHwAmFydq4-JjnPCqI7aSCGL5YbOLGQ7QiiDj7933b9ATwpP7tFJiakHABrto9IKQq0-2svTs97IvCSdgT1xKDbFW_9ulObccRZZCK9Ghf5x0BvXjX3v-" +
            "Qbj8BVSMQ2Xt1ImGyoVHIc16iRFWx-Zi-V4L7mAb6z5kv-CUo9KfJUCWiBlI5x1fcFOESccRHzeY2WS54qams50MIkvykFnE1ZO5sOA";

    @BeforeClass
    public void Setup(){
        RestAssured.baseURI = "https://stage-dev.truemedsapi.in";
    }
}

package Java.Base.Base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected static String accesstoken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI1NDI5NSIsImVudGl0eVR5cGUiOiJJRCIsInJvbGVMaXN0IjpbIlJPTEVfQ1VTVE9NRVIiXSwiaXNzIjoiY29tLnRydWVtZWRzLmF1dGh" +
            "fc2VydmljZSIsImV4cCI6MTc4NjE2NDIyMCwiaWF0IjoxNzg2MDc3ODIwfQ.J_e4KbQIid9fvZ0q29EjajVTkVFdk5Z5RM704YHWHfOiUD4W2DaNUcL" +
            "HFmUmA7MH8QT2-wqgVHJJWD_KY8cOqiQ56gSRF3LGGqAC_-8blRZ5vMQissK4b3itrZXibbR9jmnVkEsny9Opyl1swAc2G6QKjcrU2DE8pIj8gGP-z8frZro" +
            "Z-IWHU3quheal-jY00EXd0mA7MLYrBX8EDwxTSXS2A_YUO-F0BNlrYaOjgv-cUexsBn9M6wsgrs40ISQjdV6dWnWiNB40pracVWCqT-TypmJ3pvd4NCO30eL" +
            "bEdfwWFw392SrN5cJVWVesKeZW6lfvSE01BtfM1Qlm41yHw";

    @BeforeClass
    public void Setup(){
        RestAssured.baseURI = "https://stage-portal.truemedsapi.in";
    }
}

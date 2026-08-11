package Java.Base.Base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected static String accesstoken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI1NDI5NSIsImVudGl0eVR5cGUiOiJJRCIsInJvbGVMaXN0IjpbIlJPTEVfQ1VTVE9NRVIiXSwia" +
            "XNzIjoiY29tLnRydWVtZWRzLmF1dGhfc2VydmljZSIsImV4cCI6MTc4NjUyOTQ4NCwiaWF0IjoxNzg2NDQzMDg0fQ.nsAqBA1wialCE6LltTiKKePLq7zNP3e_Gt7y5bPSlDrTwMMzFEScU7C3Xr5KTp0COQq" +
            "71i8UdeJmDN2AnMX89UjXYF7hMOOw1u24PnMEkjojSls563ZgF69KvCeeTsNZncNa5Sd_ZCEz19B5hFl8xEvpw6EDdq7oLP3-EykzUH4CkacqLsY31HqmXZs3C425DuI10Al0ZOPnyGWY_LfuQBdGEaAQUeXqHoi" +
            "5S7MJspWA9idaVFpLe1oAmy1t_NxU4g9rKv0Op1byO2lcugkNLLxHcH3PBEMTbhSgExz_mCj86knTevMbl2VxiGP3FvI45LLmT1LTgZllzbzWGh_V8A";

    @BeforeClass
    public void Setup(){
        RestAssured.baseURI = "https://stage-portal.truemedsapi.in";
    }
}

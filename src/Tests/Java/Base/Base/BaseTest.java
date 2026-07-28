package Java.Base.Base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected static String accesstoken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI1NDI5NSIsImVudGl0eVR5cGUiOiJJRCIsInJvbGVMaXN0IjpbIlJPTEVfQ1VTVE9NRVIiXSwiaXNz" +
            "IjoiY29tLnRydWVtZWRzLmF1dGhfc2VydmljZSIsImV4cCI6MTc4NTMzODg4OSwiaWF0IjoxNzg1MjUyNDg5fQ.tBq4nwP0PMDiDabL93_wiKsNgwhtAuf6MO4GlPl-CsbxlqX1m1IrXq1Gs3bmFs7DNNz" +
            "TGzNR_8BNQpXVkWdipNbiPUJJ_7DAl_y1CvCsWMJ53rei_4e3hS-tCZoBFEF2-bwiNdDg8kOZV_2D0TXvompmNVjXAqdei-PPMstV7kdJ1uKGgGOyX88msGGnyamGhcVsrCIWQeBayhkAwuD9rhsZ3P2BGFXOn" +
            "ZzETNjWP-_GKLCPl3dPydh5SqpdKFktuMhBNQUr5PQMpLigXqc2mgzjoyAwJvANBrAmj-noAXm4djZczicO2oowTGjvKxR0m7Ho1lzuzrrGXGWVuwMSXA";

    @BeforeClass
    public void Setup(){
        RestAssured.baseURI = "https://stage-portal.truemedsapi.in";
    }
}

package Java.Base.Base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected static String accesstoken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI1NDY4NSIsImVudGl0eVR5cGUiOiJJRCIsInJvbGVMaXN0IjpbIlJPTEVfQ1VTVE9NRVIiXSwiaXNzIjoiY29tLnRydWVtZWRzLmF1dGhfc2VydmljZSIsImV4cCI6MTc4NzMzMDQzMSwiaWF0IjoxNzg3MjQ0MDMxfQ.Tcl8UrhPsaS8CIky8R6g4sbBXLLKK7vUkBjw6OLr3yyS-G3g2egXbadexF5Xl5EYVNF2k7MjykqbLhC49FYEyhTN_OX5GetGxlhsurIIkEKDSJ-eJL4-taI_-qgR8Ms0Ogfi47p-yCiESR3OmDNqrb2MS9xm9LTAIltF6pcRJg2eAiHz6FqweGoCextWM61M1JD-HmfYJ9RkmVYC7YLwCXReEXqA41jXVK9XrkxO33jzdjVfD0PiD5oVmR56klw8cc0pAQX8FcmMf7dzzTKJY2i5um8hCWqGtogGdINx8dypR9VuHK9tSZGVtitBtsmkZp6iqJhMKbJLOvEL37fZZA";

    @BeforeClass
    public void Setup(){
        RestAssured.baseURI = "https://stage-dev.truemedsapi.in";
    }
}

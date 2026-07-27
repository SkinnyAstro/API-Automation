package Java.Base.Base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected static String accesstoken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI1NDU0OCIsImVudGl0eVR5cGUiOiJJRCIsInJvbGVMaXN0IjpbIlJP" +
            "TEVfQ1VTVE9NRVIiXSwiaXNzIjoiY29tLnRydWVtZWRzLmF1dGhfc2VydmljZSIsImV4cCI6MTc4NTIyMDUwMiwiaWF0IjoxNzg1MTM0MTAyfQ.NTwTMCsEX4ORG2LGY_XIer" +
            "zHsL9CAOWsjbBK_q5XNgx7q2Ik3IoKuxLNN8xCx5zg7LAq1p22T6sb3PVjH1eVF45-bMNx-funkPbCnbUigyaS8VwI2fFvdrECxWQiERWkAmkb95MeB7B73R2t4sg01PjRb4Fg" +
            "79xJUmqT9vt4q3EFfrXV94klaudIdot3STwNq9tEgF6IdDhriuNccqE4sR5iJxuk3iX891Ni_ijrWkCG9G8kxpqb52qWmjAuBxncaf59Atr4BprjegD-SwjQpR49XIWkRNi-7YNIeuyrzJt943ysubOFmfFqR9XfTEcjzZ5fVTJvwvMuZ7DBukCWPg";

    @BeforeClass
    public void Setup(){
        RestAssured.baseURI = "https://stage-portal.truemedsapi.in";
    }
}

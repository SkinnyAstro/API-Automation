package Java.Base.Base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected static String accesstoken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI1NDI5NSIsImVudGl0eVR5cGUiOiJJRCIsInJvbGVMaXN0IjpbIl" +
            "JPTEVfQ1VTVE9NRVIiXSwiaXNzIjoiY29tLnRydWVtZWRzLmF1dGhfc2VydmljZSIsImV4cCI6MTc4NTIzMjc0NiwiaWF0IjoxNzg1MTQ2MzQ2fQ.0uIbJUHAhatFJ7TXQ" +
            "g6VsVIMy3XAojFUWOAYfs4BgOMAzR7rAZdscaHe6LkV1PlklrIYJ3OBAzT4va4g4EcudE5n9x7IpFm5fXtB5o-i4r-FAZUUxR5exRvotA8ikhVqbloUpCLBQWWsqLjXYiL9R" +
            "ZGRqme8hPzclZT6Y0PT5NgozkEoXuwlaic5c09CCIn-2JnP1eoiUGADKrUGF-5sNn2sX45AOWbqjLiX8MfBrqtId4w5HepMQhCtRmkpkIZV5dZobKLlHH_eHM23dm3ZWfCP-3iJ" +
            "O3k_Pl3XCFDF7SE-ubtzp9oD1REbZKp-glUR0Ym5-m2fZbG4am4Ygm6vRQ";

    @BeforeClass
    public void Setup(){
        RestAssured.baseURI = "https://stage-portal.truemedsapi.in";
    }
}

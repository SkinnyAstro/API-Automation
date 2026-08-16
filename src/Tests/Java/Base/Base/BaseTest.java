package Java.Base.Base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected static String accesstoken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiI1NDU0OCIsImVudGl0eVR5cGUiOiJJRCIsInJvbGVMaXN0IjpbIlJP" +
            "TEVfQ1VTVE9NRVIiXSwiaXNzIjoiY29tLnRydWVtZWRzLmF1dGhfc2VydmljZSIsImV4cCI6MTc4Njk4NTIzMiwiaWF0IjoxNzg2ODk4ODMyfQ.vSlCKuh_qzBeHLHRWsyI4w6Hd" +
            "9_2gg1fbmncM-GX3bcbB5xln66F_4RnCJijIIXLmymC-z86NGfBMc3pFslirH5P9Y6NIPJEdPYAnzSzPYPTqroHm2rE_92szilpi0lZqtRMRZEjYXozJPfNcV7qeNyWbCcDYoA88AZaK1" +
            "a8u8kqXTZVrDyr-DwiXn6-nKwWonS_FsMcOGHRY6aGX6xq4Vtl1I-HAWdyPdj-3JyKIGj-_vl7kBZBoZqUoWimxPQkc8ta1HiCWBuMeDHA5tag89Tl2qLjBNQnUsHA5NfD76WVan5TzS55pRnNGQ-" +
            "6-Cj5C673gSfYGx1vIVhkz39StQ";

    @BeforeClass
    public void Setup(){
        RestAssured.baseURI = "https://stage-dev.truemedsapi.in";
    }
}

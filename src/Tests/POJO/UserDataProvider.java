package POJO;

import org.testng.annotations.DataProvider;

public class UserDataProvider {

    @DataProvider(name = "searchData")
    public Object[][] Search(){
            return new Object[][]{
                    {"Telma", "responseData.productList.product.skuName"},
                    {"Glenmark", "responseData.productList.product.manufacturerName"}};
    };

    @DataProvider(name = "Medicine")
    public Object[][] Medicine(){
        return new Object[][]{
                { "Zandu Balm 8ml", "TM-BAGE1-000046" },
                { "Crocin 650mg", "TM-CROC-000012" },
                { "Dolo 650mg", "TM-DOLO-000034" }
        };
    }


}

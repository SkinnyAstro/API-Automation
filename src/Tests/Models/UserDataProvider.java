package Models;

import org.testng.annotations.DataProvider;

public class UserDataProvider {

    @DataProvider(name = "searchData")
    public Object[][] Search(){
            return new Object[][]{
                    {"Telma", "responseData.productList.product.skuName"},
                    {"Glenmark", "responseData.productList.product.manufacturerName"}};
    };


}

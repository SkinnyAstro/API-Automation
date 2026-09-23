package base.DataProviders;

import org.testng.annotations.DataProvider;

public class MedicineDataProvider {
    @DataProvider(name = "MedicineData")
    public static Object [][] getMedicineData(){
        return new Object[][]{
                {"Telma 40 Tablet 15","TM-TACR1-038772"},
                {"Ciplactin Tablet 15","TM-TACR1-008278"}
        };
    }
}

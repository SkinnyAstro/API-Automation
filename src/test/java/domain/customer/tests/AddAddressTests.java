package domain.customer.tests;

import core.BaseTest;
import domain.customer.CustomerService;
import domain.customer.pojo.Addaddress;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AddAddressTests extends BaseTest {
    private int addressId;
    private CustomerService customerService;

    @BeforeClass(alwaysRun = true)
    public void init(){
        customerService = new CustomerService(accesstoken);
    }

    @Test(description = "Adding a valid address")
    public void validAddress(){
        Addaddress address = new Addaddress();
        address.setAddressType("City Address");
        address.setAddressline1("AUTOMATION-TEST");
        address.setPincode(421301);
        address.setAddressline2("Test address");

        Response res = customerService.addAddress(address);
        res.then().statusCode(200);
        addressId = res.jsonPath().getInt("responseData.addressId");

        assertThat(res.jsonPath().getString("statusValue")).isEqualTo("OK");
        assertThat(res.jsonPath().getString("message")).isEqualTo("Address Saved Successfully");
        assertThat(res.jsonPath().getString("responseData.successMsg")).isEqualTo("Address added!");
        assertThat(addressId).isGreaterThan(0);

    }

    @Test(description = "Added address is listed ", dependsOnMethods = "validAddress")
    public void addressIsListed(){
        Response res = customerService.fetchAlladdress();
        res.then().statusCode(200);
        List<Integer> listofaddress = res.jsonPath().getList("responseData.addressId");
        assertThat(listofaddress).contains(addressId);

    }

}

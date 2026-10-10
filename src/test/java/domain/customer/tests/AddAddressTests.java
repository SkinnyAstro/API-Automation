package domain.customer.tests;

import core.BaseTest;
import domain.customer.CustomerService;
import domain.customer.pojo.Addaddress;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AddAddressTests extends BaseTest {
    private int addressId;
    private boolean addressDeleted;
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

    @Test(description = "Deleting the added address", dependsOnMethods = "addressIsListed")
    public void deleteAddress(){
        assertThat(addressId).isGreaterThan(0);
        Response res = customerService.deleteAddress(addressId);
        res.then().statusCode(200);
        assertThat(res.jsonPath().getString("responseData.successMsg")).containsIgnoringCase("address deleted");

        res = customerService.fetchAlladdress();
        List<Integer> listofaddress = res.jsonPath().getList("responseData.addressId");
        assertThat(listofaddress).doesNotContain(addressId);
        addressDeleted = true;
    }

    @Test(description = "Trying to save an address with an Invalid pincode")
    public void invalidPincode(){
        Addaddress address = new Addaddress();
        address.setAddressType("City Address");
        address.setAddressline1("AUTOMATION-TEST");
        address.setPincode(0);
        address.setAddressline2("Test address");

        Response res = customerService.addAddress(address);
        res.then().statusCode(400);
        assertThat(res.jsonPath().getString("statusValue")).isEqualTo("BAD_REQUEST");
        assertThat(res.jsonPath().getString("message")).containsIgnoringCase("pincode");
    }

    @Test(enabled = false, description = "BUG >Backend is saving the address without the addressline 1")
    public void emptyAddressLine1(){
        Addaddress address = new Addaddress();
        address.setAddressType("City Address");
        address.setAddressline1(" ");
        address.setPincode(421301);
        address.setAddressline2("Test address");

        Response res = customerService.addAddress(address);
        res.then().statusCode(400);
        assertThat(res.jsonPath().getString("statusValue")).isEqualTo("BAD_REQUEST");
    }

    @AfterClass(alwaysRun = true)
    public void cleanup(){
        if (addressId > 0 && !addressDeleted) {
            customerService.deleteAddress(addressId);
        }
    }
}

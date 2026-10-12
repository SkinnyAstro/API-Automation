package domain.customer.tests;

import core.BaseTest;
import domain.customer.CustomerService;
import domain.customer.pojo.Patientdetails;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AddPatientTests extends BaseTest {
    private CustomerService customerService;
    private int patientId;
    private boolean patientDeleted;

    @BeforeClass(alwaysRun = true)
    public void init(){
        customerService = new CustomerService(accesstoken);
    }


   /*
   relation id
   1 > Grandparent
   2> Parent
   3> Sibling
   4> Spouse
   5> Myself
   6> Child
   7> Grandchile
   8> Others

    */


    @Test(description = "Adding a valid patient")
    public void addvalidPatient(){
        Patientdetails patient = new Patientdetails();
        patient.setFirstName("Tony");
        patient.setLastName("Stark");
        patient.setAge(29);
        patient.setGender(9);
        patient.setRelationId(8);

        Response res = customerService.addPatient(patient);
        res.then().statusCode(200);
        patientId = res.jsonPath().getInt("responseData.patientId");
        assertThat(res.jsonPath().getString("message")).isEqualTo("Patient Added Successfully");
        assertThat(patientId).isGreaterThan(0);
    }

    @Test(description = "Added patient is visible",dependsOnMethods ="addvalidPatient" )
    public void patientisListed(){
      Response res = customerService.getAllPatients();
      res.then().statusCode(200);
      List<Integer>listofPatients = res.jsonPath().getList("PatientList.patientId");
      assertThat(listofPatients).contains(patientId);
    }

    @Test(description = "Deleting the added patient",dependsOnMethods = "patientisListed")
    public void deletePatient(){
        Response res = customerService.deletePatient(patientId);
        res.then().statusCode(200);
        assertThat(res.jsonPath().getString("message")).isEqualTo("Patient Deleted");

        res= customerService.getAllPatients();
        List<Integer>listofPatients = res.jsonPath().getList("PatientList.patientId");
        assertThat(listofPatients).doesNotContain(patientId);
        patientDeleted = true;
    }

    @AfterClass(alwaysRun = true)
    public void cleanup(){
        if (patientId > 0 && !patientDeleted) {
            customerService.deletePatient(patientId);
        }
    }

}

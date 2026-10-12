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

  

    @Test(enabled = false , description = "BUG > From BE we are able to add more than 150 age and 0 and less than 0")
    public void invalidage(){
        Patientdetails patient = new Patientdetails();
        patient.setRelationId(6);
        patient.setAge(0);
        patient.setFirstName("Guru");
        patient.setLastName("Sartape");
        patient.setGender(8);

        Response res = customerService.addPatient(patient);
        res.prettyPrint();
    }

    @Test(enabled = false, description = "BUG > BE accepts a patient without a first name (the app blocks it)")
    public void addPatientWithoutFirstName(){
        Patientdetails patient = buildTestPatient();
        patient.setFirstName(null);

        Response res = customerService.addPatient(patient);
        deleteIfCreated(res);
        res.then().statusCode(400);
    }

    @Test(description = "Last name is optional: adding a patient without it succeeds")
    public void addPatientWithoutLastName(){
        Patientdetails patient = buildTestPatient();
        patient.setLastName(null);

        Response res = customerService.addPatient(patient);
        Integer createdId = res.jsonPath().get("responseData.patientId");
        deleteIfCreated(res);
        res.then().statusCode(200);
        assertThat(createdId).isNotNull();
    }

    @Test(enabled = false, description = "BUG > BE accepts a patient without an age (the app blocks it)")
    public void addPatientWithoutAge(){
        Patientdetails patient = buildTestPatient();
        patient.setAge(null);

        Response res = customerService.addPatient(patient);
        deleteIfCreated(res);
        res.then().statusCode(400);
    }

    @Test(enabled = false, description = "BUG > BE accepts a patient without a gender (the app blocks it)")
    public void addPatientWithoutGender(){
        Patientdetails patient = buildTestPatient();
        patient.setGender(null);

        Response res = customerService.addPatient(patient);
        deleteIfCreated(res);
        res.then().statusCode(400);
    }

    private Patientdetails buildTestPatient(){
        Patientdetails patient = new Patientdetails();
        patient.setFirstName("AutoTest");
        patient.setLastName("Patient");
        patient.setAge(29);
        patient.setGender(8);
        patient.setRelationId(8);
        return patient;
    }

    // If the backend wrongly accepted the request, remove the patient it created
    private void deleteIfCreated(Response res){
        Integer createdId = res.jsonPath().get("responseData.patientId");
        if (createdId != null) {
            customerService.deletePatient(createdId);
        }
    }

    @AfterClass(alwaysRun = true)
    public void cleanup(){
        if (patientId > 0 && !patientDeleted) {
            customerService.deletePatient(patientId);
        }
    }

}

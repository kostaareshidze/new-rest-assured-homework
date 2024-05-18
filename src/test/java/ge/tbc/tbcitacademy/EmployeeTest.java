package ge.tbc.tbcitacademy;

import com.example.springboot.soap.interfaces.*;
import com.github.javafaker.Faker;
import ge.tbc.tbcitacademy.data.Constants;
import ge.tbc.tbcitacademy.data.Marshall;
import ge.tbc.tbcitacademy.data.Unmarshall;
import ge.tbc.tbcitacademy.steps.EmployeeSteps;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.xml.datatype.DatatypeConfigurationException;
import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Epic("Testing Employees")
public class EmployeeTest {
    EmployeeSteps employeeSteps;
    Faker faker;

    @BeforeClass
    public void setUp() {
        RestAssured.filters(new AllureRestAssured());
        faker = new Faker();
        employeeSteps = new EmployeeSteps();
    }

    @Severity(SeverityLevel.NORMAL)
    @Test(description = "adds employee")
    public void addEmployee() throws DatatypeConfigurationException {
        ObjectFactory factory = new ObjectFactory();
        AddEmployeeRequest addEmployeeRequest = factory.createAddEmployeeRequest();
        EmployeeInfo employeeInfo = factory.createEmployeeInfo();
        employeeInfo.withName(faker.name().fullName())
                .withAddress(faker.address().city())
                .withEmployeeId(Constants.id)
                .withBirthDate(employeeSteps.getCalendar())
                .withDepartment("first")
                .withEmail(faker.internet().emailAddress())
                .withPhone(faker.phoneNumber().phoneNumber())
                .withSalary(new BigDecimal(500));
        addEmployeeRequest.setEmployeeInfo(employeeInfo);
        String body = Marshall.marshallSoapRequest(addEmployeeRequest);
        Response response = employeeSteps.addEmployee(body);
        AddEmployeeResponse unmarshalledEmployee = Unmarshall.unmarshallResponse(response.asString(), AddEmployeeResponse.class);
        assertThat(unmarshalledEmployee.getServiceStatus().getMessage(), is(Constants.added));


    }

    @Severity(SeverityLevel.NORMAL)
    @Test(description = "updates the employee")
    public void updateEmployee() throws DatatypeConfigurationException {
        ObjectFactory factory = new ObjectFactory();
        UpdateEmployeeRequest updateEmployeeRequest = factory.createUpdateEmployeeRequest();
        EmployeeInfo employeeInfo = factory.createEmployeeInfo();
        employeeInfo.withName(faker.name().fullName())
                .withAddress(faker.address().fullAddress())
                .withEmployeeId(Constants.id)
                .withBirthDate(employeeSteps.getCalendar())
                .withDepartment("second")
                .withEmail(faker.internet().emailAddress())
                .withPhone(faker.phoneNumber().phoneNumber())
                .withSalary(new BigDecimal(600));
        updateEmployeeRequest.setEmployeeInfo(employeeInfo);
        String body = Marshall.marshallSoapRequest(updateEmployeeRequest);
        Response response = employeeSteps.updateEmployee(body);
        UpdateEmployeeResponse updatedEmployee = Unmarshall.unmarshallResponse(response.asString(), UpdateEmployeeResponse.class);
        assertThat(updatedEmployee.getServiceStatus().getMessage(), is(Constants.updated));
        assertThat(updateEmployeeRequest.getEmployeeInfo().getDepartment(), is("second"));
    }

    @Severity(SeverityLevel.NORMAL)
    @Test(description = "gets employee by id")
    public void getEmployee() {
        Response response = employeeSteps.getByID(employeeSteps.getMarshalled());
        GetEmployeeByIdResponse getEmployee = Unmarshall.unmarshallResponse(response.asString(), GetEmployeeByIdResponse.class);
        assertThat(getEmployee.getEmployeeInfo().getEmployeeId(), is(Constants.id));
    }

    @Severity(SeverityLevel.NORMAL)
    @Test(description = "deletes employee")
    public void deleteEmployee() {
        ObjectFactory objectFactory = new ObjectFactory();
        DeleteEmployeeRequest deleteEmployeeRequest = objectFactory.createDeleteEmployeeRequest();
        deleteEmployeeRequest.setEmployeeId(Constants.id);
        String body = Marshall.marshallSoapRequest(deleteEmployeeRequest);
        Response response = employeeSteps.deleteEmployee(body);
        DeleteEmployeeResponse deleteEmployee = Unmarshall.unmarshallResponse(response.asString(), DeleteEmployeeResponse.class);
        assertThat(deleteEmployee.getServiceStatus().getMessage(), is(Constants.deleted));
        employeeSteps.getByID(employeeSteps.getMarshalled()).then().statusCode(500);
    }
}

package ge.tbc.tbcitacademy;

import com.example.springboot.soap.interfaces.*;
import com.github.javafaker.Faker;
import ge.tbc.tbcitacademy.data.Constants;
import ge.tbc.tbcitacademy.data.Marshall;
import ge.tbc.tbcitacademy.steps.EmployeeSteps;
import io.qameta.allure.Epic;
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

    @Test(description = "adds employee")
    public void addEmployee() throws DatatypeConfigurationException {
        ObjectFactory factory = new ObjectFactory();
        AddEmployeeRequest addEmployeeRequest = factory.createAddEmployeeRequest();
        EmployeeInfo employeeInfo = factory.createEmployeeInfo();
        employeeInfo.setName(faker.name().fullName());
        employeeInfo.setAddress(faker.address().fullAddress());
        employeeInfo.setEmployeeId(Constants.id);
        employeeInfo.setBirthDate(employeeSteps.getCalendar());
        employeeInfo.setDepartment("first");
        employeeInfo.setEmail(faker.internet().emailAddress());
        employeeInfo.setPhone(faker.phoneNumber().phoneNumber());
        employeeInfo.setSalary(new BigDecimal(500));
        addEmployeeRequest.setEmployeeInfo(employeeInfo);
        String body = Marshall.marshallSoapRequest(addEmployeeRequest);
        Response response = employeeSteps.addEmployee(body);
        String addedText = response.xmlPath().getString(Constants.message);
        assertThat(addedText, is(Constants.added));

    }

    @Test(description = "updates the employee")
    public void updateEmployee() throws DatatypeConfigurationException {
        ObjectFactory factory = new ObjectFactory();
        UpdateEmployeeRequest updateEmployeeRequest = factory.createUpdateEmployeeRequest();
        EmployeeInfo employeeInfo = factory.createEmployeeInfo();
        employeeInfo.setName(faker.name().fullName());
        employeeInfo.setAddress(faker.address().fullAddress());
        employeeInfo.setEmployeeId(Constants.id);
        employeeInfo.setBirthDate(employeeSteps.getCalendar());
        employeeInfo.setDepartment("second");
        employeeInfo.setEmail(faker.internet().emailAddress());
        employeeInfo.setPhone(faker.phoneNumber().phoneNumber());
        employeeInfo.setSalary(new BigDecimal(600));
        updateEmployeeRequest.setEmployeeInfo(employeeInfo);
        String body = Marshall.marshallSoapRequest(updateEmployeeRequest);
        Response response = employeeSteps.updateEmployee(body);
        String updateText = response.xmlPath().getString(Constants.message);
        assertThat(updateText, is(Constants.updated));
        assertThat(updateEmployeeRequest.getEmployeeInfo().getDepartment(), is("second"));
    }

    @Test(description = "gets employee by id")
    public void getEmployee() {
        employeeSteps.getByID(employeeSteps.getMarshalled());
    }

    @Test(description = "deletes employee")
    public void deleteEmployee() {
        ObjectFactory objectFactory = new ObjectFactory();
        DeleteEmployeeRequest deleteEmployeeRequest = objectFactory.createDeleteEmployeeRequest();
        deleteEmployeeRequest.setEmployeeId(Constants.id);
        String body = Marshall.marshallSoapRequest(deleteEmployeeRequest);
        Response response = employeeSteps.deleteEmployee(body);
        String deleteText = response.xmlPath().getString(Constants.message);
        assertThat(deleteText, is(Constants.deleted));
        employeeSteps.getByID(employeeSteps.getMarshalled()).then().statusCode(500);

    }


}

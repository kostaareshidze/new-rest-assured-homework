package ge.tbc.tbcitacademy.steps;

import com.example.springboot.soap.interfaces.GetEmployeeByIdRequest;
import com.example.springboot.soap.interfaces.ObjectFactory;
import ge.tbc.tbcitacademy.data.Constants;
import ge.tbc.tbcitacademy.data.Marshall;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import java.util.GregorianCalendar;

import static io.restassured.RestAssured.given;

public class EmployeeSteps {
    public Response getByID(String body) {
        return given()
                .filter(new AllureRestAssured())
                .header("Content-Type", "text/xml; charset=utf-8")
                .header("SOAPAction", "interfaces.soap.springboot.example.com/exampleSoapHttp/getEmployeeByIdRequest")
                .body(body)
                .post("http://localhost:8087/ws");
    }

    public String getMarshalled() {
        ObjectFactory factory = new ObjectFactory();

        // Create GetEmployeeByIdRequest object
        GetEmployeeByIdRequest getEmployeeByIdRequest = factory.createGetEmployeeByIdRequest();

        // Set employeeId
        getEmployeeByIdRequest.setEmployeeId(Constants.id);
        return Marshall.marshallSoapRequest(getEmployeeByIdRequest);
    }

    public Response addEmployee(String body) {
        return given()
                .filter(new AllureRestAssured())
                .header("Content-Type", "text/xml; charset=utf-8")
                .header("SOAPAction", "interfaces.soap.springboot.example.com/exampleSoapHttp/addEmployeeRequest")
                .body(body)
                .post("http://localhost:8087/ws");
    }

    public Response updateEmployee(String body) {
        return given()
                .filter(new AllureRestAssured())
                .header("Content-Type", "text/xml; charset=utf-8")
                .header("SOAPAction", "interfaces.soap.springboot.example.com/exampleSoapHttp/updateEmployeeRequest")
                .body(body)
                .post("http://localhost:8087/ws");
    }

    public Response deleteEmployee(String body) {
        return given()
                .filter(new AllureRestAssured())
                .header("Content-Type", "text/xml; charset=utf-8")
                .header("SOAPAction", "interfaces.soap.springboot.example.com/exampleSoapHttp/deleteEmployeeRequest")
                .body(body)
                .post("http://localhost:8087/ws");
    }

    public XMLGregorianCalendar getCalendar() throws DatatypeConfigurationException {
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        GregorianCalendar birthDateGregorian = new GregorianCalendar(1990, 4, 16); // Note: Month is zero-based (4 = May)
        return datatypeFactory.newXMLGregorianCalendar(birthDateGregorian);
    }

}

package ge.tbc.tbcitacademy.steps;

import com.example.springboot.soap.interfaces.GetEmployeeByIdRequest;
import com.example.springboot.soap.interfaces.ObjectFactory;
import ge.tbc.tbcitacademy.data.Constants;
import ge.tbc.tbcitacademy.data.Marshall;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import java.util.GregorianCalendar;

import static io.restassured.RestAssured.given;

public class EmployeeSteps {
    @Step("Returns response with body '{body}'")
    public Response getByID(String body) {
        return given()
                .filter(new AllureRestAssured())
                .header("Content-Type", "text/xml; charset=utf-8")
                .header("SOAPAction", "interfaces.soap.springboot.example.com/exampleSoapHttp/getEmployeeByIdRequest")
                .body(body)
                .post("http://localhost:8087/ws");
    }

    @Step("returns marshalled request")
    public String getMarshalled() {
        ObjectFactory factory = new ObjectFactory();
        GetEmployeeByIdRequest getEmployeeByIdRequest = factory.createGetEmployeeByIdRequest();
        getEmployeeByIdRequest.setEmployeeId(Constants.id);
        return Marshall.marshallSoapRequest(getEmployeeByIdRequest);
    }

    @Step("Returns response with body '{body}'")
    public Response addEmployee(String body) {
        return given()
                .filter(new AllureRestAssured())
                .header("Content-Type", "text/xml; charset=utf-8")
                .header("SOAPAction", "interfaces.soap.springboot.example.com/exampleSoapHttp/addEmployeeRequest")
                .body(body)
                .post("http://localhost:8087/ws");
    }

    @Step("Returns response with body '{body}'")
    public Response updateEmployee(String body) {
        return given()
                .filter(new AllureRestAssured())
                .header("Content-Type", "text/xml; charset=utf-8")
                .header("SOAPAction", "interfaces.soap.springboot.example.com/exampleSoapHttp/updateEmployeeRequest")
                .body(body)
                .post("http://localhost:8087/ws");
    }

    @Step("Returns response with body '{body}'")
    public Response deleteEmployee(String body) {
        return given()
                .filter(new AllureRestAssured())
                .header("Content-Type", "text/xml; charset=utf-8")
                .header("SOAPAction", "interfaces.soap.springboot.example.com/exampleSoapHttp/deleteEmployeeRequest")
                .body(body)
                .post("http://localhost:8087/ws");
    }
    @Step("returns calendar")
    public XMLGregorianCalendar getCalendar() throws DatatypeConfigurationException {
        DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        GregorianCalendar birthDateGregorian = new GregorianCalendar(1990, 4, 16); // Note: Month is zero-based (4 = May)
        return datatypeFactory.newXMLGregorianCalendar(birthDateGregorian);
    }


}

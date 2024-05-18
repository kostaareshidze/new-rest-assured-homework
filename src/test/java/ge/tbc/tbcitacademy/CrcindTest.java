package ge.tbc.tbcitacademy;

import ge.tbc.tbcitacademy.data.Constants;
import ge.tbc.tbcitacademy.data.Marshall;
import ge.tbc.tbcitacademy.data.Unmarshall;
import ge.tbc.tbcitacademy.steps.CrcindSteps;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.tempuri.FindPerson;
import org.tempuri.FindPersonResponse;
import org.tempuri.ObjectFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Epic("Person")
public class CrcindTest {
    CrcindSteps crcindSteps;

    @BeforeClass
    public void setUp() {
        crcindSteps = new CrcindSteps();
        RestAssured.filters(new AllureRestAssured());
    }

    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Doing some validations on person")
    public void personValidations() {
        ObjectFactory objectFactory = new ObjectFactory();
        FindPerson findPerson = objectFactory.createFindPerson();
        findPerson.setId(Constants.TEN);
        String body = Marshall.marshallSoapRequest(findPerson);
        Response response = crcindSteps.getCrcindResponse(body);
        FindPersonResponse find = Unmarshall.unmarshallResponse(response.body().asString(), FindPersonResponse.class);
        String SSN = find.getFindPersonResult().getSSN();
        String name = find.getFindPersonResult().getName();
        crcindSteps
                .assertResultIsNotNull(find)
                .assertSSN(SSN, response.xmlPath().getString(Constants.SSN))
                .assertName(name, response.xmlPath().getString(Constants.Name))
                .assertLengthOfString(SSN.length(), crcindSteps.countLengthOfString(SSN))
                .assertLengthOfString(name.length(), crcindSteps.countLengthOfString(name))
                .checksSSNPattern(SSN)
                .assertFavoriteColors(find.getFindPersonResult().getFavoriteColors().getFavoriteColorsItem())
                .assertOfficeZipNotSameAsHome(find.getFindPersonResult().getOffice().getZip(),
                        find.getFindPersonResult().getHome().getZip())
                .assertDateOfBirth(crcindSteps.convertToString(find.getFindPersonResult().getDOB()),
                        response.xmlPath().getString(Constants.DoB));
        List<String> list = find.getFindPersonResult().getFavoriteColors().getFavoriteColorsItem();
        boolean favoriteColor = list.stream()
                .anyMatch(color -> color.equals("Red") && list.indexOf(color) == 2);
        assertThat(favoriteColor, is(true)); //it will fail because index of red is 1
    }
}

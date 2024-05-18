package ge.tbc.tbcitacademy.steps;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import org.tempuri.FindPersonResponse;

import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CrcindSteps {
    @Step("counts length of string '{string}'")
    public int countLengthOfString(String string) {
        int count = 0;
        for (int i = 0; i < string.length(); i++) {
            count++;
        }
        return count;
    }

    @Step("checks '{ssn}' pattern")
    public CrcindSteps checksSSNPattern(String ssn) {
        Pattern pattern = Pattern.compile("^\\d{3}-\\d{2}-\\d{4}$");
        Matcher matcher = pattern.matcher(ssn);
        matcher.matches();
        return this;
    }

    @Step("converts '{xmlGregorianCalendar}' to string")
    public String convertToString(XMLGregorianCalendar xmlGregorianCalendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(xmlGregorianCalendar.toGregorianCalendar().getTime());
    }

    @Step("returns response with body '{body}'")
    public Response getCrcindResponse(String body){
        return given()
                .filter(new AllureRestAssured())
                .header("Content-Type", "text/xml; charset=utf-8")
                .header("SOAPAction", "http://tempuri.org/SOAP.Demo.FindPerson")
                .body(body)
                .post("https://www.crcind.com:443/csp/samples/SOAP.Demo.cls");
    }

    @Step("Checks if '{actual}' and '{expected}' are same")
    public CrcindSteps assertSSN(String actual, String expected) {
        assertThat(actual, is(expected));
        return this;
    }

    @Step("Checks if '{actual}' and '{expected}' are same")
    public CrcindSteps assertName(String actual, String expected) {
        assertThat(actual, is(expected));
        return this;
    }

    @Step("Checks if '{actualLength}' and '{expectedLength}' are same")
    public CrcindSteps assertLengthOfString(int actualLength, int expectedLength) {
        assertThat(actualLength, is(expectedLength));
        return this;
    }

    @Step("Checks if list '{favoriteColors}' contains some elements")
    public CrcindSteps assertFavoriteColors(List<String> favoriteColors) {
        assertThat(favoriteColors, containsInAnyOrder("Orange", "Red"));
        return this;
    }
    @Step("Checks if FindPersonResponse '{find}' is not empty")
    public CrcindSteps assertResultIsNotNull(FindPersonResponse find){
        assertThat(find.getFindPersonResult(), notNullValue());
        return this;
    }

    @Step("Checks if '{officeZip}' and '{homeZip}' are not same")
    public CrcindSteps assertOfficeZipNotSameAsHome(String officeZip, String homeZip) {
        assertThat(officeZip, not(homeZip));
        return this;
    }

    @Step("Checks if '{actualDate}' and '{expectedDate}' are same")
    public void assertDateOfBirth(String actualDate, String expectedDate) {
        assertThat(actualDate, is(expectedDate));
    }


}

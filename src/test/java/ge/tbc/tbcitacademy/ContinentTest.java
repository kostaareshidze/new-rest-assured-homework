package ge.tbc.tbcitacademy;


import ge.tbc.tbcitacademy.data.Constants;
import ge.tbc.tbcitacademy.steps.ContinentSteps;
import io.qameta.allure.Epic;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static com.google.common.base.Predicates.containsPattern;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Epic("Validations")
public class ContinentTest {
    ContinentSteps continentSteps;
    @BeforeClass
    public void setUp() {
        continentSteps = new ContinentSteps();
        RestAssured.filters(new AllureRestAssured());
    }
    @Test(description = "validating components of continents")
    public void continentTest() {
        Response response = continentSteps.getContinents();
        var lst = response.xmlPath().getList(Constants.sNamePath);
        assertThat(lst.size(), is(continentSteps.numberOfsNames(lst)));
        assertThat(lst, containsInAnyOrder(Constants.africa, Constants.antarctica, Constants.asia,
                Constants.europe, Constants.oceania, Constants.americas));
        assertThat(response.xmlPath().getString(Constants.sNameWithAn),
                equalTo(Constants.antarctica));
        assertThat(response.xmlPath().getString(Constants.lastSName),
                equalTo(Constants.americas));
        assertThat(response.xmlPath().getList(Constants.sNamePath), everyItem(not(emptyOrNullString())));
        assertThat(response.xmlPath().getList(Constants.sCodePath), everyItem(not(emptyOrNullString())));
        assertThat(response.xmlPath().getList(Constants.sCodePath), everyItem(matchesPattern("[A-Z]{2}")));
        assertThat(response.xmlPath().getList(Constants.sNamePath), contains(Constants.africa, Constants.antarctica,
                Constants.asia, Constants.europe, Constants.oceania, Constants.americas));
        assertThat(response.xmlPath().getList(Constants.sNamePath), containsInAnyOrder(Constants.africa,
                Constants.antarctica, Constants.asia, Constants.europe, Constants.oceania, Constants.americas));
        assertThat(response.xmlPath().getList(Constants.sNamePath), everyItem(not(containsPattern("\\d"))));
//        assertThat(response.xmlPath().getString("ArrayOftContinent.tContinent.find { it.sCode.startsWith('O') }.sName"),
//                equalTo("Ocenania"));
//        assertThat(response.xmlPath().getList("ArrayOftContinent.tContinent.findAll { it.sName.startsWith('A') && it.sName.endsWith('ca') }.sName"),
//                hasItem("Africa"));
        assertThat(response.xmlPath().getList(Constants.sNamePath), everyItem(not(containsPattern("\\d"))));
    }


}

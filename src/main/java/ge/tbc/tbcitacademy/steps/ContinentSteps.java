package ge.tbc.tbcitacademy.steps;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ContinentSteps {
    public Response getContinents(){
        return given().filter(new AllureRestAssured())
                .get("http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso/ListOfContinentsByName");
    }
    public int numberOfsNames(List<Object> lst){
        int counter = 0;
        for (Object o : lst) {
            counter++;
        }
        return counter;
    }
}

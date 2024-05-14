package ge.tbc.tbcitacademy;

import com.github.javafaker.Faker;
import ge.tbc.tbcitacademy.data.Constants;
import ge.tbc.tbcitacademy.steps.PetStoreSteps;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pet.store.v3.invoker.ApiClient;
import pet.store.v3.invoker.JacksonObjectMapper;
import pet.store.v3.model.Category;
import pet.store.v3.model.Order;
import pet.store.v3.model.Pet;
import pet.store.v3.model.Tag;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static io.restassured.RestAssured.config;
import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;


public class PetStoreTest {
    private ApiClient api;
    Faker faker;
    PetStoreSteps petStoreSteps;

    @BeforeClass
    public void setUp() {
        RestAssured.filters(new AllureRestAssured());
        faker = new Faker();
        petStoreSteps = new PetStoreSteps();
        api = ApiClient.api(ApiClient.Config.apiConfig()
                .reqSpecSupplier(() -> new RequestSpecBuilder()
                        .log(LogDetail.ALL)
                        .setConfig(config()
                                .objectMapperConfig(objectMapperConfig()
                                        .defaultObjectMapper(JacksonObjectMapper.jackson())))
                        .addFilter(new ErrorLoggingFilter())
                        .setBaseUri(Constants.BASE_URL)));
    }

    @Test
    public void postOrder() {
        Order order = petStoreSteps.generatingOrder();
        api
                .store()
                .placeOrder()
                .body(order)
                .reqSpec(requestSpecBuilder -> requestSpecBuilder.addFilter(new AllureRestAssured()))
                .execute(response -> response);


    }

    @Test
    public void postPet() {

        Pet pet = petStoreSteps.generatingPet();
        api
                .pet()
                .addPet()
                .body(pet)
                .reqSpec(requestSpecBuilder -> requestSpecBuilder.addFilter(new AllureRestAssured()))
                .execute(res -> res
                        .then().statusCode(200));
    }
}

package ge.tbc.tbcitacademy;


import com.github.javafaker.Faker;
import ge.tbc.tbcitacademy.data.Constants;
import ge.tbc.tbcitacademy.data.DataProviders;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pet.store.v3.invoker.ApiClient;
import pet.store.v3.invoker.JacksonObjectMapper;


import static io.restassured.RestAssured.config;

import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;

public class SpringTest {
    private ApiClient api;
    @BeforeClass
    public void setUp() {
        RestAssured.filters(new AllureRestAssured());
        api = ApiClient.api(ApiClient.Config.apiConfig()
                .reqSpecSupplier(() -> new RequestSpecBuilder()
                        .log(LogDetail.ALL)
                        .setConfig(config()
                                .objectMapperConfig(objectMapperConfig()
                                        .defaultObjectMapper(JacksonObjectMapper.jackson())))
                        .addFilter(new ErrorLoggingFilter())
                        .setBaseUri(Constants.BASE_URL2)));

    }

    @Test(dataProviderClass = DataProviders.class, dataProvider = "getEmailAndPassword")
    public void authenticationTest(String email, String password) {
        Faker faker = new Faker();
        Response res = api
                .authentication()
                .register()
                .body(new RegisterRequest()
                        .email(email)
                        .password(password)
                        .role(RegisterRequest.RoleEnum.USER)
                        .firstname(faker.name().firstName())
                        .lastname(faker.name().lastName()))
                .reqSpec(requestSpecBuilder -> requestSpecBuilder.addFilter(new AllureRestAssured()))
                .execute(response -> response);
        AuthenticationResponse authenticationResponse = res.as(AuthenticationResponse.class);
        api.authorization().reqSpec(
                        request -> {
                            request.addHeader("Authorization", "Bearer " + authenticationResponse.getAccessToken())
                                    .addHeader("accept", "*/*")
                                    .addFilter(new AllureRestAssured());

                        }
                ).sayHelloWithRoleAdminAndReadAuthority()
                .execute(response -> response);
        String token = authenticationResponse.getAccessToken();
        api
                .authentication()
                .reqSpec(requestSpecBuilder -> {
                    requestSpecBuilder
                            .addHeader("Authorization", "Bearer " + token)
                            .addFilter(new AllureRestAssured());
                })
                .authenticate()
                .body(new AuthenticationRequest()
                        .email(faker.internet().emailAddress())
                        .password(Constants.password + faker.internet().password()))
                .execute(response -> response
                        .as(AuthenticationResponse.class)
                        .getRoles().stream());



    }
}

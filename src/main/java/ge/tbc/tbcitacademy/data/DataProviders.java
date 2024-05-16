package ge.tbc.tbcitacademy.data;


import com.github.javafaker.Faker;
import org.testng.annotations.DataProvider;

public class DataProviders {
    Faker faker = new Faker();
    @DataProvider
    public Object[][] getEmailAndPassword() {
        return new Object[][] {
                {faker.internet().emailAddress(), faker.internet().password()},
                {faker.internet().emailAddress(), faker.internet().password()},
                {faker.internet().emailAddress(), faker.internet().password()},
                {faker.internet().emailAddress(), faker.internet().password()},
                {faker.internet().emailAddress(), Constants.password + faker.internet().password()}

        };
    }
}

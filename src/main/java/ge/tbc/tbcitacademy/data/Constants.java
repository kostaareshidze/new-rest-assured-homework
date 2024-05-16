package ge.tbc.tbcitacademy.data;

import com.github.javafaker.Faker;

public class Constants {
    Faker faker = new Faker();
    public static final String BASE_URL = "https://petstore3.swagger.io/api/v3";
    public static final String BASE_URL2 = "http://localhost:8086";
    public static final String password = "P@ssw0rd";
    public static final String sNamePath = "ArrayOftContinent.tContinent.sName";
    public static final String sCodePath = "ArrayOftContinent.tContinent.sCode";
    public static final String africa = "Africa";
    public static final String asia = "Asia";
    public static final String antarctica = "Antarctica";
    public static final String oceania = "Ocenania";
    public static final String americas = "The Americas";
    public static final String europe = "Europe";
    public static final String sNameWithAn = "ArrayOftContinent.tContinent.find { it.sCode == 'AN' }.sName";
    public static final String lastSName = "ArrayOftContinent.tContinent[-1].sName";
    public static final Long id = 5L;
    public static final String message = "Envelope.Body.deleteEmployeeResponse.serviceStatus.message";
    public static final String deleted = "Content Deleted Successfully";
    public static final String added = "Content Added Successfully";
    public static final String updated = "Content Updated Successfully";
}

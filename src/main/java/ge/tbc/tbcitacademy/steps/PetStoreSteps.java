package ge.tbc.tbcitacademy.steps;

import com.github.javafaker.Faker;
import pet.store.v3.model.Category;
import pet.store.v3.model.Order;
import pet.store.v3.model.Pet;
import pet.store.v3.model.Tag;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class PetStoreSteps {
    Faker faker = new Faker();

    public Pet generatingPet(){
        Pet pet = new Pet();
        return pet
                .id(5L)
                .name(faker.animal().name())
                .status(Pet.StatusEnum.AVAILABLE)
                .addPhotoUrlsItem("string")
                .addTagsItem(new Tag()
                        .id(0L)
                        .name("string"))
                .category(new Category()
                        .id(0L)
                        .name("string"));
    }
    public Order generatingOrder(){
        Order order = new Order();
        return order
                .complete(true)
                .petId(5L)
                .id(2L)
                .quantity(5)
                .shipDate(OffsetDateTime.of(2024, 5, 13, 21,
                        3, 0, 3, ZoneOffset.UTC))
                .status(Order.StatusEnum.APPROVED);
    }
}

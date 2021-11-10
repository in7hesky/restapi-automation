import entities.User;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class UserTest extends BaseTest {
    public static final String USER_ENDPOINT = "/user";

    @Test
    public void shouldAddNewUser() {
        User expectedUser = addNewUser();

        request.when().get(USER_ENDPOINT + "/" + expectedUser.username)
                .then().statusCode(200)
                .and().body("password", equalTo(expectedUser.password));

    }

    private User getNewRandomUser() {
        long randomNumber = faker.number().randomNumber();
        String randomName = faker.name().firstName();

        return new User(randomName + randomNumber,
                randomNumber + randomName);
    }

    private User addNewUser() {
        User userToAdd = getNewRandomUser();
        //delete previous user with the same username if exists
        request.when().delete(USER_ENDPOINT+ "/" + userToAdd.username)
                .then().statusCode(anyOf(is(200), is(404)));

        request.when().body(userToAdd).post(USER_ENDPOINT)
                .then().statusCode(200);

        System.out.println(userToAdd.username);

        return userToAdd;
    }
}

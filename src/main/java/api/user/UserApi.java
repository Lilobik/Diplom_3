package api.user;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import utils.Specification;

import static io.restassured.RestAssured.given;

public class UserApi extends Specification {
    private static final String REGISTER_PATH = "api/auth/register/";
    private static final String LOGIN_PATH = "api/auth/login/";
    private static final String USER_PATH = "api/auth/user/";

    @Step("Registration new user")
    public Response createUser(User user) {
        return (Response) given()
                .spec(Specification.requestSpecification())
                .body(user)
                .when()
                .post(REGISTER_PATH);
    }

    @Step("Authenticating user")
    public Response loginUser(UserData credentials) {
        return (Response) given()
                .spec(Specification.requestSpecification())
                .body(credentials)
                .when()
                .post(LOGIN_PATH);
    }

    @Step("Deleting user")
    public Response deleteUser(Response response) {
        String accessToken = response.body().jsonPath().getString("accessToken");
        return given()
                .spec(Specification.requestSpecification())
                .header("authorization", accessToken)
                .when()
                .delete(USER_PATH);
    }
}

package Sessions;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Firstsession {

    Response res ;
    @BeforeClass
    public void Training(){
        RestAssured.baseURI="https://6821b3bb259dad2655b0438c.mockapi.io";
        res = given().pathParam("UsersEndpoint","Users").when().get("{/UsersEndpoint}");
    }
    @Test
    public void Example(){
        res.then().assertThat().body("name[0]",equalTo("Verna Kerluke"));
        res.then().assertThat().statusCode(200);


    }
}

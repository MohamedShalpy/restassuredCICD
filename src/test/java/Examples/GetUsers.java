package Examples;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;

import static com.google.common.base.Predicates.equalTo;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetUsers {
    Response Users;
    @BeforeTest
    public void Url(){
        RestAssured.baseURI="https://jsonplaceholder.typicode.com";
        Users = given().pathParam("usersEndpoint","users").get("/{usersEndpoint}");
    }
    @Test(priority = 1)
    public void Users(){
        Users.then().assertThat().body("[0].email",startsWith("Sincere"));
        Assert.assertEquals(Users.body().jsonPath().get("[0].id").toString(),Integer.toString(1));
        Assert.assertEquals(Users.body().jsonPath().get("[9].id").toString(),Integer.toString(10));
        Users.prettyPrint();

    }
    @Test(priority = 2)
    public void GetFirstUser(){
        Map<String, Object> firstUser = Users.jsonPath().getMap("[0]");
        Users.then().assertThat().body("[0].username",startsWith("Bret"));
        System.out.println("=== First User ===");
        Users.then().body("username",instanceOf(String.class));
        for (Map.Entry<String, Object> entry : firstUser.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
///
        }
    }
    @Test(priority = 3)
    public void GetLastUser(){
        Map<String, Object> LastUser = Users.jsonPath().getMap("[9]");
        Assert.assertEquals(Users.body().jsonPath().get("[9].id").toString(),Integer.toString(10));
        System.out.println("=== Last User ===");
        for (Map.Entry<String, Object> entry : LastUser.entrySet()){
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

    }
    @Test(priority = 4)
    public void GetMiddleUser(){
        Map<String, Object> MidUser = Users.jsonPath().getMap("[4]");
        Assert.assertEquals(Users.body().jsonPath().get("[4].id").toString(),Integer.toString(5));
        System.out.println("=== Middle User ===");
        for (Map.Entry<String, Object> entry : MidUser.entrySet()){
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}

package Examples;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

public class StartWith {
    Response Start;
    @BeforeTest
    public void StartWi(){
        RestAssured.baseURI ="https://jsonplaceholder.typicode.com";
        Start = given().pathParam("postsEndpoint","posts").get("/{postsEndpoint}");

    }
    @Test
    public void code(){
        Map<String, Object> firstPost = Start.jsonPath().getMap("[0]");
        Start.then().assertThat().body("[0].title",startsWith("s"));
        System.out.println("=== First Post ===");
        for (Map.Entry<String, Object> entry : firstPost.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

    }
}

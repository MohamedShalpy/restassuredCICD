package Examples;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class HasItem {
    Response Has;

    @BeforeTest
    public void Has() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        Has = given().pathParam("postsEndpoint", "posts").when().get("/{postsEndpoint}");
    }
    @Test
    public void Code(){
        Map<String, Object> firstPost = Has.jsonPath().getMap("[0]");
        Has.then().assertThat().body("[0].body",equalTo("quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"));
        System.out.println("=== First Post ===");
        for (Map.Entry<String, Object> entry : firstPost.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

    }
}

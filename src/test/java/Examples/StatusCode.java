package Examples;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class StatusCode {

    Response allPostsResponse;
    Response singlePostResponse;
    int FirstID;
    int lastId;
    int singlePostId = 1;

    @BeforeTest
    public void StCode() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Get list of posts
        allPostsResponse = given()
                .pathParam("postsEndpoint", "posts")
                .when()
                .get("/{postsEndpoint}");

        // Assign global variables
        FirstID = allPostsResponse.jsonPath().getInt("[0].id");
        lastId = allPostsResponse.jsonPath().getInt("[99].id");

        // Get single post
        singlePostResponse = given()
                .pathParam("id", singlePostId)
                .when()
                .get("/posts/{id}");
    }

    @Test
    public void testPostCollectionAssertions() {
        // Assert collection size
        int size = allPostsResponse.jsonPath().getList("").size();
        Assert.assertEquals(size, 100, "Expected 100 posts");

        // Assert IDs in the list
        List<Integer> ids = allPostsResponse.jsonPath().getList("id");
        Assert.assertTrue(ids.contains(1), "ID 1 should exist");
        Assert.assertTrue(ids.contains(100), "ID 100 should exist");

        // Assert all titles are not null
        allPostsResponse.then().body("title", everyItem(notNullValue()));

        // Assert all userIds are between 1 and 10
        allPostsResponse.then().body("userId", everyItem(allOf(greaterThan(0), lessThanOrEqualTo(10))));

        // Assign Local variable and Assert first and last IDs
        int FirstID = allPostsResponse.jsonPath().getInt("[0].id");
        int lastId = allPostsResponse.jsonPath().getInt("[99].id");
        Assert.assertEquals(FirstID, 1, "First post ID should be 1");
        Assert.assertEquals(lastId, 100, "Last post ID should be 100");

        // Status code assertions
        allPostsResponse.then().statusCode(200);
        allPostsResponse.then().statusLine(containsString("OK"));
       // allPostsResponse.then().statusCode(is(oneOf(200,201)));

        // Optional: Print response
        allPostsResponse.prettyPrint();
    }

    @Test
    public void testSinglePostById() {
        int id = singlePostResponse.jsonPath().getInt("id");
        int userId = singlePostResponse.jsonPath().getInt("userId");
        String title = singlePostResponse.jsonPath().getString("title");

        Assert.assertEquals(id, singlePostId, "Returned ID should match the requested one");
        Assert.assertEquals(userId, 1, "User ID should be 1");
        Assert.assertNotNull(title, "Title should not be null");

        singlePostResponse.then().statusCode(200);
        singlePostResponse.then().statusLine(containsString("OK"));
    }
}

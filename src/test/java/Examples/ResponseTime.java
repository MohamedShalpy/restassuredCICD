package Examples;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ResponseTime {
    Response Time;
    @BeforeTest
    public void Res(){
        RestAssured.baseURI="https://jsonplaceholder.typicode.com";
        Time = given().pathParam("postsEndpoint","posts").get("/{postsEndpoint}");
    }
    @Test
    public void Code(){
        Long time = Time.time();
        System.out.println("ResponseTime:"+ time + "ms");
        Assert.assertTrue(time < 2000);
    }
}

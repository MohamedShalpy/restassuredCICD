package Examples;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class Schema {
    Response Sch;
    @BeforeTest
    public void Endpoint(){
        RestAssured.baseURI="https://jsonplaceholder.typicode.com";
        Sch = given().pathParam("postsEndpoint","posts").get("/{postsEndpoint}");
    }
    @Test
    public void Code(){
        Sch.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/Schema2.json"));
        Sch.prettyPrint();
    }
}

package Examples;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;

public class CreateUser {
    @Test
    public void Create(){
        RestAssured.baseURI="https://jsonplaceholder.typicode.com";
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id", 11);
        requestBody.put("name", "Shalaby");
        requestBody.put("Street", "Tanta");
        Response response = given().header("Content-Type","application/json, charset=UTF-8").body(requestBody).when().post("/users");
        response.then().statusCode(201);
        response.prettyPrint();

    }
}

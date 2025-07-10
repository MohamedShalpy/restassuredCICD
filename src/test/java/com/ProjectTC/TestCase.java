package com.ProjectTC;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class TestCase {

    Response res;

    @BeforeClass
    public void SetUp(){
        RestAssured.baseURI="https://6821b3bb259dad2655b0438c.mockapi.io";  //enter the url
        res = given().pathParam("UsersEndpoint","Users").when().get("/{UsersEndpoint}");// enter the endpoint
    }

    @Test
    public void checkJsonSchema(){
//        res.prettyPrint();
        Assert.assertEquals(res.statusCode(),200);
        Assert.assertEquals(res.body().jsonPath().get("id[0]").toString(),Integer.toString(1)); //assert the data for the id from first obj
        Assert.assertEquals(res.body().jsonPath().get("id[3]").toString(),Integer.toString(4)); //assert the data for the id from the last obj
//        JsonSchemaValidator jsonSchemaValidator = matchesJsonSchemaInClasspath("schema/schema.json");

//        res.then()
//                .statusCode(200)
//                .body(matchesJsonSchemaInClasspath("schema/schema.json")); //assert the all response
//        Assert.assertEquals(jsonSchemaValidator.);
        res.then().assertThat().body("name[0]",equalTo("Verna Kerluke")); // check the first name equal the actual from response
        res.then().assertThat().body("name[1]",equalTo("Dr. Bradley Hessel")); // check the City equal the actual from response
        res.then().assertThat().body("createdAt",everyItem(startsWith("2025"))); // check the create at start with this number *note start with take int
     //   res.then().assertThat().body("CountryCode[0].size()",equalTo(2)); //check the size for country code
        res.then().assertThat().body("[0]",hasKey("avatar")); //check the obj has key called avatar
        res.then().assertThat().body("avatar",not(empty())); // check the key is not empty value
//        res.then().body("Country",notNullValue()); //check the country is not null
//        res.then().body(containsString("Saudi Arabia"));
        res.then().time(lessThan(2000L)); //check the response time
      //  res.then().cookie("cookieName",containsString("expectedCookieValue"));
        // check the header if key and value equal the actual or not
//        res.then().assertThat().header("Content-Type" , "application/json");
//        res.then().assertThat().headers("Connection",containsString("keep-alive"),"Server","Cowboy");
//        int ContentLength = Integer.parseInt(res.getHeader("Content-Length"));
//        Assert.assertTrue(ContentLength > 100);






        res.prettyPrint();









    }
}

package Assignment2;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class TC01_Assignment {


    Response res;

    @BeforeClass
    public void SetUp(){
        RestAssured.baseURI="https://reqres.in/api";
        res = given().pathParam("usersEndpoint","users").queryParam("page",2).when().get("/{usersEndpoint}");
        res.prettyPrint();
    }
    @Test(priority = 1)
    public void obj1(){
        Assert.assertEquals(res.body().jsonPath().get("data[0].id").toString(),Integer.toString(7));
        Assert.assertEquals(res.body().jsonPath().get("data[5].id").toString(),Integer.toString(12));
        res.then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/Assignschema.json"));
        res.then().assertThat().body("data[0].email",equalTo("michael.lawson@reqres.in")); // assert if the email in first obj equal the expected email
        res.then().assertThat().body("data[0].first_name",equalTo("Michael")); // assert if the First name  in first obj equal the expected name
        res.then().assertThat().body("data[0].last_name.size()",equalTo(6)); //assert the size for the last name
        res.then().assertThat().body("data[0].",hasKey("avatar")); // check if the first obj have a key called avatar
        res.then().assertThat().body("avatar",not(empty())); //check this key is not empty
        res.then().time(lessThan(1000L)); // assert for the response time can use it if u want lessthan or greater than
        //print the first user
        Map<String, Object> firstUser = res.body().jsonPath().getMap("data[0]");
        System.out.println("First User Object:\n" + firstUser);
        //print all cookies
        System.out.println("Cookies: " + res.getCookies());
        //  res.then().cookie("cookieName",containsString("expectedCookieValue"));
        res.then().assertThat().header("Connection" , "keep-alive");


    }
    @Test(priority = 2)
    public void obj2(){
        Assert.assertEquals(res.body().jsonPath().get("data[0].id").toString(),Integer.toString(7));
        Assert.assertEquals(res.body().jsonPath().get("data[5].id").toString(),Integer.toString(12));
        res.then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/Assignschema.json"));
        res.then().assertThat().body("data[1].email",equalTo("lindsay.ferguson@reqres.in")); // assert if the email in first obj equal the expected email
        res.then().assertThat().body("data[1].first_name",equalTo("Lindsay")); // assert if the First name  in first obj equal the expected name
        res.then().assertThat().body("data[1].last_name.size()",equalTo(8)); //assert the size for the last name
        res.then().assertThat().body("data[1].",hasKey("avatar")); // check if the SECOND Obj have a key called avatar
        res.then().assertThat().body("avatar",not(empty())); //check this key is not empty
        res.then().time(lessThan(1000L)); // assert for the response time can use it if u want lessthan or greater than
        //print the second user
        Map<String, Object> SecondUser = res.body().jsonPath().getMap("data[1]");
        System.out.println("second User Object:\n" + SecondUser);
        //print all cookies
        System.out.println("Cookies: " + res.getCookies());
        //  res.then().cookie("cookieName",containsString("expectedCookieValue"));
        res.then().assertThat().header("Connection" , "keep-alive");


    }
    @Test(priority = 3)
    public void obj3(){
        Assert.assertEquals(res.body().jsonPath().get("data[0].id").toString(),Integer.toString(7));
        Assert.assertEquals(res.body().jsonPath().get("data[5].id").toString(),Integer.toString(12));
        res.then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/Assignschema.json"));
        res.then().assertThat().body("data[2].email",equalTo("tobias.funke@reqres.in"));
        res.then().assertThat().body("data[2].first_name",equalTo("Tobias"));
        res.then().assertThat().body("data[2].last_name.size()",equalTo(5));
        res.then().assertThat().body("data[2].",hasKey("avatar"));
        res.then().assertThat().body("avatar",not(empty()));
        res.then().time(lessThan(1000L)); // assert for the response time can use it if u want lessthan or greater than
        //print the Third user
        Map<String, Object> ThirdUser = res.body().jsonPath().getMap("data[2]");
        System.out.println("Third User Object:\n" + ThirdUser);
        //print all cookies
        System.out.println("Cookies: " + res.getCookies());
        //  res.then().cookie("cookieName",containsString("expectedCookieValue"));
        res.then().assertThat().header("Connection" , "keep-alive");


    }
    @Test(priority = 4)
    public void obj4(){
        Assert.assertEquals(res.body().jsonPath().get("data[0].id").toString(),Integer.toString(7));
        Assert.assertEquals(res.body().jsonPath().get("data[5].id").toString(),Integer.toString(12));
        res.then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/Assignschema.json"));
        res.then().assertThat().body("data[3].email",equalTo("byron.fields@reqres.in"));
        res.then().assertThat().body("data[3].first_name",equalTo("Byron"));
        res.then().assertThat().body("data[3].last_name.size()",equalTo(6));
        res.then().assertThat().body("data[3].",hasKey("avatar"));
        res.then().assertThat().body("avatar",not(empty())); //check this key is not empty
        res.then().time(lessThan(1000L)); // assert for the response time can use it if u want lessthan or greater than
        //print the second user
        Map<String, Object> FourthUser = res.body().jsonPath().getMap("data[3]");
        System.out.println("Fourth User Object:\n" + FourthUser);
        //print all cookies
        System.out.println("Cookies: " + res.getCookies());
        //  res.then().cookie("cookieName",containsString("expectedCookieValue"));
        res.then().assertThat().header("Connection" , "keep-alive");


    }
    @Test(priority = 5)
    public void obj5(){
        Assert.assertEquals(res.body().jsonPath().get("data[0].id").toString(),Integer.toString(7));
        Assert.assertEquals(res.body().jsonPath().get("data[5].id").toString(),Integer.toString(12));
        res.then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/Assignschema.json"));
        res.then().assertThat().body("data[4].email",equalTo("george.edwards@reqres.in"));
        res.then().assertThat().body("data[4].first_name",equalTo("George"));
        res.then().assertThat().body("data[4].last_name.size()",equalTo(7));
        res.then().assertThat().body("data[4].",hasKey("avatar"));
        res.then().assertThat().body("avatar",not(empty())); //check this key is not empty
        res.then().time(lessThan(1000L)); // assert for the response time can use it if u want lessthan or greater than
        //print the second user
        Map<String, Object> FifthUser = res.body().jsonPath().getMap("data[4]");
        System.out.println("Fifth User Object:\n" + FifthUser);
        //print all cookies
        System.out.println("Cookies: " + res.getCookies());
        //  res.then().cookie("cookieName",containsString("expectedCookieValue"));
        res.then().assertThat().header("Connection" , "keep-alive");


    }
    @Test(priority = 6)
    public void obj6(){
        Assert.assertEquals(res.body().jsonPath().get("data[0].id").toString(),Integer.toString(7));
        Assert.assertEquals(res.body().jsonPath().get("data[5].id").toString(),Integer.toString(12));

        res.then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/Assignschema.json"));
        res.then().assertThat().body("data[5].email",equalTo("rachel.howell@reqres.in"));
        res.then().assertThat().body("data[5].first_name",equalTo("Rachel"));
        res.then().assertThat().body("data[5].last_name.size()",equalTo(6));
        res.then().assertThat().body("data[5].",hasKey("avatar"));
        res.then().assertThat().body("avatar",not(empty()));
        res.then().time(lessThan(1000L)); // assert for the response time can use it if u want lessthan or greater than
        //print the Sixth user
        Map<String, Object> SixthUser = res.body().jsonPath().getMap("data[5]");
        System.out.println("Sixth User Object:\n" + SixthUser);
        //print all cookies
        System.out.println("Cookies: " + res.getCookies());
        //  res.then().cookie("cookieName",containsString("expectedCookieValue"));
        res.then().assertThat().header("Connection" , "keep-alive");


    }


}

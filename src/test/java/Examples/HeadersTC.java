package Examples;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class HeadersTC {
    Response Head;
    @BeforeTest
    public void headers(){
        RestAssured.baseURI="https://jsonplaceholder.typicode.com";
        Head = given().pathParam("postsEndpoint","posts").header("Cookie", "myCookieName=myCookieValue")
                .get("/{postsEndpoint}");
    }
    @Test
    public void Code() {
        Head.then().assertThat().header("Connection", "keep-alive");
        //To assert all headers
        Headers h = Head.getHeaders();
        for (Header header : h) {
            System.out.println(header.getName() + ": " + header.getValue());
        }
        //Get Cookie manually
        String cookieVal = Head.getCookie("Cookie");
        System.out.println("Cookie Value: " + cookieVal);
    }
}

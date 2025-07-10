package Examples;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class UploadFile {
    @Test
    public void testFileUpload() {
        RestAssured.baseURI = "https://petstore.swagger.io"; // Use your actual API
        File file = new File("C:\\\\Users\\\\Mohamed Shalaby\\\\Desktop\\\\IMG_8546 (1).jpg");
        if(!file.exists()|| file.length()==0){
            throw new RuntimeException("File not found or empty : " + file.getAbsolutePath());
        }

        Response UploadFile =given().formParam("additionalMetadata","test").multiPart("file",file ,"image/jpeg")
                .when().post("/v2/pet/1/uploadImage");
        UploadFile.then().statusCode(200);
        String message = UploadFile.jsonPath().getString("message");
        Assert.assertTrue(message.contains("File uploaded to"),
                "Unexpected upload message: " + message);
        UploadFile.prettyPrint();
        System.out.println(UploadFile.asPrettyString());
        System.out.println("Message: " + message);
    }
}

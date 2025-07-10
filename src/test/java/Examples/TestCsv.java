package Examples;

import com.opencsv.exceptions.CsvValidationException;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.FileNameConstants;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import com.opencsv.CSVReader;
import static io.restassured.RestAssured.given;

public class TestCsv {
    Response Booking;


    @BeforeTest
    public void Url(){
        RestAssured.baseURI="https://restful-booker.herokuapp.com";
        Booking = given().pathParam("bookingEndpoint","booking").get("/{bookingEndpoint}");
    }
    @Test(dataProvider = "Book1Sheet1")
    public void CSV(Map<String,String> testData){
        System.out.println(testData.get("firstname"));
        System.out.println(testData.get("lastname"));
        System.out.println(testData.get("totalprice"));
        System.out.println("**************************");
    }
    @DataProvider(name = "Book1Sheet1")
    public Object[][] getTestData(){
        Object [][] objArray = null ;
        Map<String,String> map = null;
        List<Map<String,String>> testDataList = null;
        try {
            CSVReader csvReader = new  CSVReader(new FileReader(FileNameConstants.CSV_DATA_PATH));
            testDataList = new ArrayList<Map<String,String>>();
            String[] line = null;
            int count = 0;
            while ((line = csvReader.readNext())!= null) {
                if (count == 0){
                    count++;
                    continue;
                }
                 map = new TreeMap<String,String>(String.CASE_INSENSITIVE_ORDER);
                 map.put("firstname",line[1]);
                map.put("lastname",line[2]);
                map.put("totalprice",line[3]);
                testDataList.add(map);

            }
            objArray = new Object[testDataList.size()][1];
            for (int i=0 ; i < testDataList.size(); i++) {
                objArray[i][0] = testDataList.get(i);
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return objArray;
    }
}

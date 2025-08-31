package Reconciliation;

import com.shaft.driver.SHAFT;
import org.testng.annotations.*;
import utilities.DatabasePage;
import utilities.DatabaseUtils;

    public class DatabaseTest {
        private SHAFT.GUI.WebDriver driver;
        private DatabasePage databasePage;

        @BeforeClass
        public void setup() {
            // Start the SHAFT engine
            driver = DatabaseUtils.initializeDriver();

            // Connect to DB via Utilities
            databasePage = DatabaseUtils.initializeDatabase();
        }

        @Test
        public void testDatabaseSelectQueryAndExport() {
            String query = "SELECT DISTINCT SD.SP_PNR, AIRLINE_PNR, BOOKING_DATE " +
                    "FROM wonderdb.TT_TS_FB_SEGMENT_DETAIL SD " +
                    "JOIN wonderdb.TT_TS_FLIGHT_BOOK FB ON FB.FB_BOOKING_REF_NO = SD.FB_BOOKING_REF_NO " +
                    "WHERE FB.CREATION_TIME > '2025-07-04' " +
                    "AND FB.CREATION_TIME < '2025-07-05' " +
                    "AND FB.SUPPLIER_NAME LIKE \"%Galileo%\" " +
                    "AND SD.SP_PNR IS NOT NULL;";

            String excelPath = "C:/Users/Mahmoud/Desktop/queryResults.xlsx";
            databasePage.executeSelectQueryAndSaveToExcel(query, excelPath);
        }

        @AfterClass
        public void tearDown() {
            databasePage.closeConnection();
            driver.quit();
        }
    }



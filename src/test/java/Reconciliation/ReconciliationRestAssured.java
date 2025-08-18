package Reconciliation.RestAssured;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.path.xml.XmlPath;
import org.testng.annotations.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static io.restassured.RestAssured.given;

public class ReconciliationRestAssured {
    String Username_UAE = "Universal API/uAPI5691377188-91fdb834";
    String Password_UAE = "8Hx*Z$3a=o";
    String Username_EGY = "Universal API/uAPI4079873840-832eeac3";
    String Password_EGY = "s600pr2J";
    String Username_KSA = "Universal API/uAPI2206758512-88ef4797";
    String Password_KSA = "8Kk&}3Hf7c";

    @Test
    public void fetchAndSaveRecordsByCredential() throws IOException {
        RestAssured.baseURI = "https://emea.universal-api.travelport.com/B2BGateway/connect/uAPI/UniversalRecordService";

        String actionDate = "2025-08-18"; // Variable for ActionDate
        int maxResults = 99;

        // ✅ Credentials + Labels (Username_UAE, Username_EGY, Username_KSA)
        String[][] credentials = {
                {"Username_UAE", Username_UAE, Password_UAE},
                {"Username_EGY", Username_EGY, Password_EGY},
                {"Username_KSA", Username_KSA, Password_KSA}
        };

        // ✅ Store ProviderLocatorCodes for each credential separately
        List<List<String>> allResults = new ArrayList<>();
        List<String> headers = new ArrayList<>();

        // 🔁 Loop through each credential
        for (String[] cred : credentials) {
            String label = cred[0];     // "Username_UAE" etc.
            String username = cred[1];  // actual API username
            String password = cred[2];  // actual API password

            Set<String> providerCodes = new LinkedHashSet<>(); // Keep unique & ordered
            System.out.println("🔐 Fetching records using: " + label);

            for (int i = 0; i <= 100000; i += 98) {
                int startFrom = i;

                String requestBody =
                        "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                                "xmlns:univ=\"http://www.travelport.com/schema/universal_v52_0\" " +
                                "xmlns:com=\"http://www.travelport.com/schema/common_v52_0\">" +
                                "<soapenv:Header/>" +
                                "<soapenv:Body>" +
                                "<univ:UniversalRecordSearchReq ActionDate=\"" + actionDate + "\" TicketStatus=\"Ticketed\">" +
                                "<univ:UniversalRecordSearchModifiers MaxResults=\"" + maxResults + "\" StartFromResult=\"" + startFrom + "\"/>" +
                                "</univ:UniversalRecordSearchReq>" +
                                "</soapenv:Body>" +
                                "</soapenv:Envelope>";

                Response response = given()
                        .header("Content-Type", "text/xml")
                        .header("Accept", "text/xml")
                        .auth().preemptive().basic(username, password)
                        .body(requestBody)
                        .post();

                XmlPath xmlPath = new XmlPath(response.asString());

                // ✅ Extract ProviderLocatorCode
                List<String> providerCodesList = xmlPath.getList(
                        "Envelope.Body.UniversalRecordSearchRsp.UniversalRecordSearchResult.ProductInfo.@ProviderLocatorCode"
                );
                if (providerCodesList != null && !providerCodesList.isEmpty()) {
                    providerCodes.addAll(providerCodesList);
                }

                // ✅ Stop loop if no more results
                String moreResults = xmlPath.getString("Envelope.Body.UniversalRecordSearchRsp.@MoreResults");
                System.out.println("🔍 StartFrom=" + startFrom + " | MoreResults=" + moreResults);
                if (!"true".equalsIgnoreCase(moreResults)) {
                    System.out.println("🚫 No more results for user: " + label);
                    break;
                }

            }


            allResults.add(new ArrayList<>(providerCodes));
            headers.add(label); // ✅ Use Username_UAE / Username_EGY / Username_KSA
        }

        // ✅ Save to CSV with multiple columns
        String csvFilePath = System.getProperty("user.dir") + "/provider_codes_by_credential.csv";
        try (FileWriter writer = new FileWriter(csvFilePath)) {
            // Write header row
            writer.write(String.join("    |         ", headers) + "\n");

            // Find max size
            int maxRows = allResults.stream().mapToInt(List::size).max().orElse(0);

            // Write row by row
            for (int row = 0; row < maxRows; row++) {
                List<String> rowValues = new ArrayList<>();
                for (List<String> list : allResults) {
                    rowValues.add(row < list.size() ? list.get(row) : ""); // empty if no value
                }
                writer.write(String.join("          |         ", rowValues) + "\n");
            }
        }

        System.out.println("✅ Records saved with separate columns per credential");
        System.out.println("📄 CSV saved at: " + csvFilePath);
    }
}

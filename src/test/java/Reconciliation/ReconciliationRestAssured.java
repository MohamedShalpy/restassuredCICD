package Reconciliation;

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

    // ✅ Global set to track provider codes across ALL credentials
    private final Set<String> globalSeenProviderCodes = new HashSet<>();

    /**
     * ✅ Helper function to check uniqueness of ProviderLocatorCode
     */
    private boolean isUniqueProviderCode(String providerCode) {
        if (providerCode == null || providerCode.isEmpty()) {
            return false;
        }
        return globalSeenProviderCodes.add(providerCode);
    }

    @Test
    public void fetchAndSaveRecordsByCredential() throws IOException {
        RestAssured.baseURI = "https://emea.universal-api.travelport.com/B2BGateway/connect/uAPI/UniversalRecordService";

        String actionDate = "2025-08-21"; // Variable for ActionDate
        int maxResults = 99;

        String[][] credentials = {
                {"UAE", Username_UAE, Password_UAE},
                {"EGY", Username_EGY, Password_EGY},
                {"KSA", Username_KSA, Password_KSA}
        };

        String csvFilePath = System.getProperty("user.dir") + "/provider_codes_by_credential.csv";
        try (FileWriter writer = new FileWriter(csvFilePath)) {
            // ✅ Write CSV header
            writer.write("Credential,ProviderLocatorCode,CreatedDate,TicketStatus\n");

            // 🔁 Loop credentials
            for (String[] cred : credentials) {
                String label = cred[0];
                String username = cred[1];
                String password = cred[2];

                System.out.println("🔐 Fetching records using: " + label);

                for (int i = 0; i <= 100000; i += 98) {
                    int startFrom = i;

                    String requestBody =
                            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                                    "xmlns:univ=\"http://www.travelport.com/schema/universal_v52_0\" " +
                                    "xmlns:com=\"http://www.travelport.com/schema/common_v52_0\">" +
                                    "<soapenv:Header/>" +
                                    "<soapenv:Body>" +
                                    "<univ:UniversalRecordSearchReq ActionDate=\"" + actionDate + "\">" +
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

                    // ✅ Extract each UniversalRecordSearchResult
                    List<Object> results = xmlPath.getList("Envelope.Body.UniversalRecordSearchRsp.UniversalRecordSearchResult");

                    if (results == null || results.isEmpty()) {
                        break;
                    }

                    for (int idx = 0; idx < results.size(); idx++) {
                        String createdDate = xmlPath.getString("Envelope.Body.UniversalRecordSearchRsp.UniversalRecordSearchResult[" + idx + "].@CreatedDate");
                        String ticketStatus = xmlPath.getString("Envelope.Body.UniversalRecordSearchRsp.UniversalRecordSearchResult[" + idx + "].@TicketStatus");

                        // ✅ FIX: Extract all provider codes as list
                        List<String> providerCodes = xmlPath.getList(
                                "Envelope.Body.UniversalRecordSearchRsp.UniversalRecordSearchResult[" + idx + "].ProductInfo.@ProviderLocatorCode"
                        );

                        if (providerCodes == null || providerCodes.isEmpty()) {
                            providerCodes = Collections.singletonList("");
                        }

                        // ✅ Loop through provider codes
                        for (String providerCode : providerCodes) {
                            if (providerCode == null) providerCode = "";

                            if (isUniqueProviderCode(providerCode)) {
                                String row = label + "," + providerCode + "," + createdDate + "," + ticketStatus;
                                writer.write(row + "\n");
                            }
                        }
                    }

//
                    // ✅ Stop loop if no more results
                    String moreResults = xmlPath.getString("Envelope.Body.UniversalRecordSearchRsp.@MoreResults");
                    System.out.println("🔍 StartFrom=" + startFrom + " | MoreResults=" + moreResults);
                    if (!"true".equalsIgnoreCase(moreResults)) {
                        System.out.println("🚫 No more results for user: " + label);
                        break;
                    }
                }
            }
        }

        System.out.println("✅ Unique records saved with ProviderLocatorCode, CreatedDate, TicketStatus");
        System.out.println("📄 CSV saved at: " + csvFilePath);
    }
}

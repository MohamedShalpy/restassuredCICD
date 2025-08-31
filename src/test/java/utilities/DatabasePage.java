//package utilities;
//
//import java.io.FileOutputStream;
//import java.sql.*;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//public class DatabasePage {
//    private String url;
//    private String username;
//    private String password;
//    private Connection connection;
//
//    public DatabasePage(String url, String username, String password) {
//        this.url = url;
//        this.username = username;
//        this.password = password;
//    }
//
//    public void connectToDatabase() {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            System.out.println("Attempting to connect to the database...");
//            connection = DriverManager.getConnection(url, username, password);
//            System.out.println("Database connected successfully!");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void executeSelectQueryAndSaveToExcel(String query, String excelFilePath) {
//        try {
//            if (connection == null || connection.isClosed()) {
//                System.out.println("Database connection is not established.");
//                return;
//            }
//
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(query);
//
//            // Create Excel workbook & sheet
//            Workbook workbook = new XSSFWorkbook();
//            Sheet sheet = workbook.createSheet("QueryResults");
//
//            // Create header row
//            Row headerRow = sheet.createRow(0);
//            headerRow.createCell(0).setCellValue("SP_PNR");
//            headerRow.createCell(1).setCellValue("AIRLINE_PNR");
//            headerRow.createCell(2).setCellValue("BOOKING_DATE");
//
//            int rowIndex = 1;
//            while (resultSet.next()) {
//                Row row = sheet.createRow(rowIndex++);
//                row.createCell(0).setCellValue(resultSet.getString("SP_PNR"));
//                row.createCell(1).setCellValue(resultSet.getString("AIRLINE_PNR"));
//                row.createCell(2).setCellValue(resultSet.getString("BOOKING_DATE"));
//            }
//
//            // Write to Excel file
//            FileOutputStream fileOut = new FileOutputStream(excelFilePath);
//            workbook.write(fileOut);
//            fileOut.close();
//            workbook.close();
//
//            System.out.println("Data saved to Excel: " + excelFilePath);
//
//            resultSet.close();
//            statement.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void closeConnection() {
//        try {
//            if (connection != null && !connection.isClosed()) {
//                connection.close();
//                System.out.println("Database connection closed.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
package utilities;

import java.io.FileOutputStream;
import java.sql.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DatabasePage {
    private Connection connection;

    // Constructor بياخد الـ Connection مباشرة
    public DatabasePage(Connection connection) {
        this.connection = connection;
    }

    public void executeSelectQueryAndSaveToExcel(String query, String excelFilePath) {
        try {
            if (connection == null || connection.isClosed()) {
                System.out.println("Database connection is not established.");
                return;
            }

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Create Excel workbook & sheet
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("QueryResults");

            // Create header row dynamically من الـ Metadata
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            Row headerRow = sheet.createRow(0);
            for (int i = 1; i <= columnCount; i++) {
                headerRow.createCell(i - 1).setCellValue(metaData.getColumnName(i));
            }

            // Fill data
            int rowIndex = 1;
            while (resultSet.next()) {
                Row row = sheet.createRow(rowIndex++);
                for (int i = 1; i <= columnCount; i++) {
                    row.createCell(i - 1).setCellValue(resultSet.getString(i));
                }
            }

            // Write to Excel file
            FileOutputStream fileOut = new FileOutputStream(excelFilePath);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();

            System.out.println("✅ Data saved to Excel: " + excelFilePath);

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✅ Database connection closed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

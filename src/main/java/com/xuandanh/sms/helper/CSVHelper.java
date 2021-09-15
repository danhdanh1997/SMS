package com.xuandanh.sms.helper;

import com.xuandanh.sms.domain.Staff;
import org.apache.commons.csv.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVHelper {
    public static String TYPE = "text/csv";
    static String[] HEADERS = { "staff_id", "active", "email", "first_name","image_url","last_name","password","username","store_id" };

    public static boolean hasCSVFormat(MultipartFile file) {
        System.out.println(file.getContentType());
        return TYPE.equals(file.getContentType())
                || file.getContentType().equals("application/vnd.ms-excel");
    }

    public static List<Staff>csvToStaff(InputStream inputStream){
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<Staff> tutorials = new ArrayList<Staff>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Staff tutorial = new Staff(
                        csvRecord.get("staff_id"),
                        Boolean.parseBoolean(csvRecord.get("active")),
                        csvRecord.get("email"),
                        csvRecord.get("first_name"),
                        csvRecord.get("image_url"),
                        csvRecord.get("last_name"),
                        csvRecord.get("password"),
                        csvRecord.get("username"),
                        Integer.parseInt(csvRecord.get("store_id"))
                );

                tutorials.add(tutorial);
            }

            return tutorials;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }
    }

    public static ByteArrayInputStream staffsToCSV(List<Staff> staffList) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format)) {
            for (Staff staff : staffList) {
                List<String> data = Arrays.asList(
                        staff.getStaffId(),
                        staff.getEmail(),
                        staff.getFirstName(),
                        staff.getImageUrl(),
                        staff.getLastName(),
                        staff.getPassword(),
                        staff.getUsername(),
                        String.valueOf(staff.isActive())
                );

                csvPrinter.printRecord(data);
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }
}

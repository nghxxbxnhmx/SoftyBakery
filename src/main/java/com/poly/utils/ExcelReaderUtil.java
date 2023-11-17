package com.poly.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

public class ExcelReaderUtil {
    public static void read(String filePath) throws EncryptedDocumentException, InvalidFormatException {
        try (FileInputStream fis = new FileInputStream(new File(filePath));
                Workbook wb = WorkbookFactory.create(fis)) {
            Sheet sheet = wb.getSheetAt(0);
            FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();

            for (Row row : sheet) {
                for (Cell cell : row) {
                    CellValue cellValue = formulaEvaluator.evaluate(cell);
                    System.out.print((int) cellValue.getNumberValue());
                }
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    public Map<Integer, List<String>> readExcel(String fileLocation) throws IOException {
    //     Map<Integer, List<String>> data = new HashMap<>();

    //     try (FileInputStream file = new FileInputStream(fileLocation); ReadableWorkbook wb = new ReadableWorkbook(file)) {
    //         Sheet sheet = wb.getFirstSheet();
    //         try (Stream<Row> rows = sheet.openStream()) {
    //             rows.forEach(r -> {
    //                 data.put(r.getRowNum(), new ArrayList<>());

    //                 for (Cell cell : r) {
    //                     data.get(r.getRowNum()).add(cell.getRawValue());
    //                 }
    //             });
    //         }
    //     }

    //     return data;
    // }
}

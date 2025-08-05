package DBToExcel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class ExcelSheets {


    public static boolean makeSheet(ResultSet resultSet, String sheetName, String fileName) throws SQLException, IOException, InvalidFormatException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int cols = metaData.getColumnCount();
        ArrayList<String[]> data_list = new ArrayList<>();

        while (resultSet.next()) {
            String[] row = new String[cols];
            for (int i = 1; i < cols; i++) {
                row[i - 1] = resultSet.getString(i);

            }
            data_list.add(row);
        }

        String[][] data = new String[cols][data_list.size()];
        return makeSheet(data_list.toArray(data), sheetName, fileName);
    }

    public static boolean makeSheet(String[][] data, String sheetName, String filePath) throws IOException, InvalidFormatException {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            XSSFWorkbook wb = new XSSFWorkbook(file);
            XSSFSheet sheet;
            if (wb.getSheet(sheetName) == null) {
                wb.createSheet(sheetName);
            }
            sheet = wb.getSheet(sheetName);
            for (int i = 0; i < data.length; i++) {

                XSSFRow row;
                try {
                    row = sheet.getRow(i);
                } catch (Exception e) {
                    row = sheet.createRow(i);
                }
                for (int j = 0; j < data[i].length; j++) {
                    XSSFCell cell;
                    try {
                        cell = row.getCell(j);
                    } catch (Exception e) {
                        cell = row.createCell(j);
                    }
                    cell.setCellValue(data[i][j]);
                }
            }
            FileOutputStream fo = new FileOutputStream(file);
            wb.write(fo);
            wb.close();
            fo.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean makeSheet(String[] data, String sheetName, String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            XSSFWorkbook wb = new XSSFWorkbook(file);
            XSSFSheet sheet;
            if (wb.getSheet(sheetName) == null) {
                wb.createSheet(sheetName);
            }
            sheet = wb.getSheet(sheetName);
            for (int i = 0; i < data.length; i++) {

                XSSFRow row;
                try {
                    row = sheet.getRow(i);
                } catch (Exception e) {
                    row = sheet.createRow(i);
                }

                XSSFCell cell;
                try {
                    cell = row.getCell(0);
                } catch (Exception e) {
                    cell = row.createCell(0);
                }
                cell.setCellValue(data[i]);
            }
            FileOutputStream fo = new FileOutputStream(file);
            wb.write(fo);
            wb.close();
            fo.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public static String [][] getSheetData(String filePath,String sheetName)throws Exception{
        File file= new File(filePath);
        if (!file.exists()){
            throw new Exception("file not found");
        }

        XSSFWorkbook wb= new XSSFWorkbook(file);
        XSSFSheet sheet = wb.getSheet(sheetName);

        int rows=sheet.getLastRowNum();
        XSSFRow row =sheet.getRow(0);
        int cols =row.getLastCellNum();

        String [][] data= new String[rows][cols];
        for (int i=0;i<rows;i++){ /// skipping the header getting data from 2nd row, if need to get data from 1st row then make getRow(i)
            for(int j=0;j<cols;j++){
                data[i][j]=sheet.getRow(i+1).getCell(j).toString();
            }
        }
        wb.close();

        return data;
    }
}

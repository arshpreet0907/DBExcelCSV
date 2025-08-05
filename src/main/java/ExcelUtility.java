import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtility {

    /**
     * here we will make utility methods to call for all the Excel operations
     * we have methods as :
            * 1) getRowCount
            * 2) getCellCount
            * 3) getCellData
            * 4) setCellData
            * 5) fillGreenColor
            * 6) fillRedColor
     * */
    public static FileInputStream fi;
    public static FileOutputStream fo;
    public static XSSFWorkbook wb;
    public static XSSFSheet sheet;
    public static XSSFRow row;
    public static XSSFCell cell;
    public static CellStyle style;

    public static int getRowCount(String xlfile, String xlsheet) throws IOException {
        fi=new FileInputStream(xlfile);
        wb=new XSSFWorkbook(fi);
        sheet=wb.getSheet(xlsheet);
        int rowCount=sheet.getLastRowNum();
        wb.close();
        fi.close();
        return rowCount;
    }
    public static int getCellCount(String xlfile, String xlsheet, int rownum) throws IOException{
        fi=new FileInputStream(xlfile);
        wb=new XSSFWorkbook(fi);
        sheet=wb.getSheet(xlsheet);
        row=sheet.getRow(rownum);
        int cellCount=row.getLastCellNum();
        wb.close();
        fi.close();
        return cellCount;
    }
    public static String getCellData(String xlfile, String xlsheet, int rownum,int colnum)throws IOException{
        fi=new FileInputStream(xlfile);
        wb=new XSSFWorkbook(fi);
        sheet=wb.getSheet(xlsheet);
        row=sheet.getRow(rownum);
        cell=row.getCell(colnum);

        String data;
        try {
//            data=cell.toString();
            /// this also works, nothing wrong here

            /// we have another class named DataFormatter to get the cell data properly
            DataFormatter formatter= new DataFormatter();
            data=formatter.formatCellValue(cell);
            /// formatted cell value as a String regardless of the cell type


        }catch (Exception e){
            /// using catch to handle the empty cell, data not found exception can happen
            data="";
        }
        wb.close();
        fi.close();
        return data;
    }
    public static void setCellData(String xlfile, String xlsheet, int rownum,int colnum,String data)throws IOException{

        /// reading and writing into the same file at the same time

        fi=new FileInputStream(xlfile);
        wb=new XSSFWorkbook(fi);
        sheet=wb.getSheet(xlsheet);
        row=sheet.getRow(rownum);
        cell=row.createCell(colnum); /// creating a new cell

        cell.setCellValue(data);
        fo=new FileOutputStream(xlfile);
        wb.write(fo);

        wb.close();
        fi.close();
        fo.close();
    }
    public static void fillGreenColor(String xlfile, String xlsheet, int rownum,int colnum)throws IOException{
        fi=new FileInputStream(xlfile);
        wb=new XSSFWorkbook(fi);
        sheet=wb.getSheet(xlsheet);
        row=sheet.getRow(rownum);
        cell=row.getCell(colnum);

        style=wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cell.setCellStyle(style);

        fo= new FileOutputStream(xlfile);
        wb.write(fo);

        wb.close();
        fi.close();
        fo.close();

    }
    public static void fillRedColor(String xlfile, String xlsheet, int rownum,int colnum)throws IOException{
        fi=new FileInputStream(xlfile);
        wb=new XSSFWorkbook(fi);
        sheet=wb.getSheet(xlsheet);
        row=sheet.getRow(rownum);
        cell=row.getCell(colnum);

        style=wb.createCellStyle();
        style.setFillForegroundColor(IndexedColors.RED.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cell.setCellStyle(style);

        fo= new FileOutputStream(xlfile);
        wb.write(fo);

        wb.close();
        fi.close();
        fo.close();

    }
}

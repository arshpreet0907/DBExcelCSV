import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;

public class Main {


    public static void main(String[] args) throws SQLException, IOException{

        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String password = "root";
        Connection con = DriverManager.getConnection(url, user, password);
        Statement stmt = con.createStatement();
        System.out.println("database changed : "+stmt.executeUpdate("use arsh;"));

        ResultSet rs = stmt.executeQuery("SELECT * FROM user_data");

        File f= new File(System.getProperty("user.dir")+"\\excel_sheets\\sql_table.xlsx");

        if (!f.exists()){
            f.getParentFile().mkdirs();
            f.createNewFile();
        }

        XSSFWorkbook wb= new XSSFWorkbook();
        XSSFSheet sheet=wb.createSheet("one data");

        int row_num=0;
        while (rs.next()) {
            int id = rs.getInt("user_id");
            String name = rs.getString("user_name");
            String pass = rs.getString("password");

            XSSFRow row= sheet.createRow(row_num++);
            row.createCell(0).setCellValue(id);
            row.createCell(1).setCellValue(name);
            row.createCell(2).setCellValue(pass);

        }
        FileOutputStream fo= new FileOutputStream(f);
        wb.write(fo);

    }
}
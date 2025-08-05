package DBToExcel;

import java.sql.Connection;

public class RunQueries {
    public static void main(String[] args) {
        Connection con=SQLConnection.connection();
        String[][] employeeData = {
                {"101", "John", "Doe", "john.doe@company.com", "Engineering", "75000.00","true"},
                {"102", "Jane", "Smith", "jane.smith@company.com", "Marketing", "65000.00","true"},
                {"103", "Mike", "Johnson", "mike.johnson@company.com", "Sales", "60000.00","false"}
        };
        String database="arsh";
        String tableName="employee";
        String []colNames={"id","first_name","last_name","email","department","amount","boolean"};
        String []colTypes={"int","varchar","varchar","text","varchar","decimal","boolean"};
        SQLConnection.insertData(con,database,tableName,colNames,colTypes,employeeData);
    }
}

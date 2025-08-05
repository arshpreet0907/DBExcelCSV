package DBToExcel;

import java.sql.*;
import java.util.Locale;

public class SQLConnection {

    private static final String SHOW=" SHOW ";
    private static final String USE=" USE ";
    private static final String SEMICOLON=" ; ";
    private static final String SELECT=" SELECT ";
    private static final String ALL=" * ";
    private static final String FROM=" FROM ";
    private static final String DATABASES=" DATABASES ";
    private static final String INSERT=" INSERT ";
    private static final String INTO=" INTO ";
    private static final String VALUES=" VALUES ";
    private static final String COMMA=" , ";
    private static final String OPEN_BRACKET=" ( ";
    private static final String CLOSE_BRACKET=" ) ";
    private static final String SINGLE_QUOTE=" ' ";
    private static final String DECIMAL="decimal";

    private static final String db_url="jdbc:mysql://localhost:3306/";
    private static final String db_user="root";
    private static final String db_pass="root";

    public static final String RESULT_SET="result_set";
    public static final String BOOLEAN="boolean";
    public static final String INT="int";

    public static Connection connection(){
        try {
            return DriverManager.getConnection(db_url,db_user,db_pass);
        }catch (SQLException sql_e){
            System.out.println(sql_e.getMessage());
            return null;
        }
    }
    public static boolean changeDatabase(Connection con,String database){
        try(Statement statement=con.createStatement()) {
            statement.executeUpdate(USE+database+SEMICOLON);
            return true;
        }
        catch (SQLException sql_e){
            System.out.println(sql_e.getMessage());
            return false;
        }
    }
    public static ResultSet showAllDatabases(Connection con){
        try(Statement statement=con.createStatement()) {
            return statement.executeQuery(SHOW+DATABASES+SEMICOLON);
        }
        catch (SQLException sql_e){
            System.out.println(sql_e.getMessage());
            return null;
        }
    }
    public static ResultSet getAllTableData(Connection con, String table_name){
        try(Statement statement=con.createStatement()) {
            return statement.executeQuery(SELECT+ALL+FROM+table_name+SEMICOLON);
        }
        catch (SQLException sql_e){
            System.out.println(sql_e.getMessage());
            return null;
        }
    }
    public static Object runCustomQuery(Connection con, String query, String return_type){
        try(Statement statement=con.createStatement()) {

            if (return_type.equalsIgnoreCase(RESULT_SET))
                return statement.executeQuery(query);

            else if (return_type.equalsIgnoreCase(BOOLEAN))
                return statement.execute(query);

            else if (return_type.equalsIgnoreCase(INT))
                return statement.executeQuery(query);

            else {
                System.out.println("invalid return type, please enter among the following : result_set, boolean, int");
                return null;
            }
        }
        catch (SQLException sql_e){
            System.out.println(sql_e.getMessage());
            return null;
        }
    }
    public static Integer insertData(Connection con,String database,String tableName,String []colNames,String colTypes[],String [][] data){

        /// if colNames is null then treat it as all columns insert
        /// if colTypes is null then treat everything as string

        changeDatabase(con,database);
        StringBuilder query=new StringBuilder();
        query.append(INSERT+INTO+tableName);
        if (colNames!=null){
            query.append(OPEN_BRACKET);
            for (int i=0;i<colNames.length;i++){
                query.append(colNames[i]);
                if (i==colNames.length-1)
                    continue;
                query.append(COMMA);
            }
            query.append(CLOSE_BRACKET);
        }
        query.append(VALUES);
        for (int i=0;i<data.length;i++){
            query.append(OPEN_BRACKET);
            for(int j=0;j<data[i].length;j++){
                query.append(quotesNoQuotes(colTypes[j])+data[i][j]+quotesNoQuotes(colTypes[j]));
                if (j==data[i].length-1)
                    continue;
                query.append(COMMA);
            }
            query.append(CLOSE_BRACKET);
            if (i==data.length-1){
                query.append(SEMICOLON);
            }
            else
                query.append(COMMA);
        }
        System.out.println(query);
        return 1;
//                (Integer) runCustomQuery(con,query,INT);

    }
    public static Integer insertData(Connection con,String database,String tableName,String[] colNames,String [][] data) {

        return insertData(con,database,tableName,colNames,null,data);

    }
        public static Integer insertData(Connection con,String database,String tableName,String [][] data){

        return insertData(con,database,tableName,null,null,data);

    }
    private static String quotesNoQuotes(String colType){
        colType=colType.toLowerCase();
        if (colType.equals(INT)||colType.equals(DECIMAL)||colType.equals(BOOLEAN))
            return "";
        else
            return SINGLE_QUOTE;
    }



}

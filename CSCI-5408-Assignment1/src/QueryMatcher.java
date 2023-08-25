import java.util.*;
public class QueryMatcher {
    /**
     * This method is used to handle the query to process curd operation commands;
     */
    public void operations(){

        System.out.println("Write your SQL Query here ");

        Scanner queryInputScanner = new Scanner(System.in);
        String  queryInput = queryInputScanner.nextLine();
        System.out.println(queryInput);
        String upperCased = queryInput.toUpperCase();
        if (upperCased.indexOf("CREATE") !=-1 && upperCased.indexOf("DATABASE") !=-1)
        {
            CreateDataBase createDataBase_obj = new CreateDataBase();
            createDataBase_obj.createDb(queryInput);
        }
        if (upperCased.indexOf("CREATE") == 0 && upperCased.indexOf("DATABASE") ==-1){
         CreateTableClass createTable_obj = new CreateTableClass();
         createTable_obj.createTable(queryInput);
        } else if (upperCased.indexOf("SELECT") == 0) {
          Select select_obj = new Select();
          select_obj.selectValues(queryInput);

        }else if (upperCased.indexOf("UPDATE") == 0) {
            System.out.println("BeginTransaction");
            Update update_obj = new Update();
            update_obj.updateTable(queryInput);
        }else if (upperCased.indexOf("DELETE") == 0) {
            System.out.println("BeginTransaction");
            Delete delete_obj = new Delete();
            delete_obj.deleteTable(queryInput);
        }else if (upperCased.indexOf("INSERT") == 0) {
            System.out.println("BeginTransaction");
            InsertInto insertIntoTable_obj = new InsertInto();
            insertIntoTable_obj.insertIntoTable(queryInput);

        }



    }
}

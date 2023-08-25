import java.util.HashMap;
import java.util.*;
public class InsertInto {
    /**
     * This method is used to find the column in the SQL query.
     * @param query input SQL query.
     * @return processed query with separated column for the insert value in the column operations.
     */
    static HashMap<String,String> columnFinder(String query){
        int columnStartIndex =  query.indexOf('(');
        HashMap<String,String> insertColumnValue =  new HashMap<>();
        String trimedQuery = query.substring(columnStartIndex+1,query.indexOf(')'));
        String[] columnNamesDelimiterSplit = trimedQuery.split(",");
        String onlyValueQuery = query.replace(query.substring(0,query.indexOf(')')+1),"");
        System.out.println(onlyValueQuery);
        String trimedQueryValue = onlyValueQuery.substring(onlyValueQuery.indexOf('(')+1,onlyValueQuery.indexOf(')'));
        String[] valueDelimiterSplit = trimedQueryValue.split(",");
        if (columnNamesDelimiterSplit.length ==valueDelimiterSplit.length){
            for (int i=0;i<columnNamesDelimiterSplit.length;i++)
            {

                insertColumnValue.put(columnNamesDelimiterSplit[i].trim(),valueDelimiterSplit[i].trim());

            }

        }
        return insertColumnValue;
    }
    /**
     * This method is used to find the column in the SQL query based on insert condition.
     * @param columns columns in the given SQL query.
     * @return processed query with column and its value from the SQL statement.
     */
    static HashMap<Integer,String[]> columnAndTypeSeparator( String[] columns) {
        int columnListLength = columns.length;
        HashMap<Integer, String[]> columnsMap = new HashMap<>();

        for (int i = 0; i < columnListLength; i++) {
            String[] columnDetails ={"",""};
            String column = columns[i];
            String a = column.trim().split(" ")[0];
            String b = column.trim().split(" ")[1];
            System.out.println("column-->"+column.trim().split(" ")[0]);
            System.out.println("data-->"+column.trim().split(" ")[1]);
            columnDetails[0]=a;
            columnDetails[1]=b;
            columnsMap.put(i, columnDetails);
        }
        return columnsMap;
    }
    /**
     * This method is used to find the column  and its value in the SQL query based on insert condition.
     * @param columns columns in the given SQL query.
     * @param tableName Name of the table to which insert operation need to be done.
     * @return processed query with column and its value to insert the value in the table column.
     */
    static  HashMap<String,ArrayList<String>>  columnAndValueForInsertOperation(String columns,String tableName){
        int indexOfTableName =  columns.indexOf(tableName);
        int trimindex = indexOfTableName + tableName.length()+2;
        System.out.println(columns);
        String []columnAndValue = columns.substring(trimindex,columns.indexOf(']')).split(";");
        HashMap<String,ArrayList<String>> columnAndValueMap = new HashMap<>();
        for (String i: columnAndValue) {
            System.out.println(i);
            ArrayList<String> valueSplit = new ArrayList<>(Arrays.asList(i.split(":")[1]));

            columnAndValueMap.put(i.split(":")[0],valueSplit);


        }
        System.out.println(columnAndValueMap);
    return columnAndValueMap;
    }


    public static void insertIntoTable (String query) {
        //String query = "INSERT INTO Persons (PersonID, LastName, FirstName,Address,City) VALUES (7, 8, 9,10,11);";
        String[] insertQueryCondition = {"INSERT", "INTO"};
        //String query ="CREATE TABLE Persons ( PersonID int, LastName varchar(255), FirstName varchar(255), Address varchar(255), City varchar(255))";
        System.out.println(query);
        boolean flag = true;
        String[] inputQuery = query.split(" ");
        //System.out.println(inputQuery);
        //System.out.println(inputQuery[0]);
        for (String k : inputQuery) {
            //System.out.println(k);
        }
        for (String j : insertQueryCondition) {
            flag = false;
            //System.out.println("query-->"+j);
            for (int i = 0; i < inputQuery.length; i++) {
                //System.out.println("animal-->"+inputQuery[i]);
                if (j.equals(inputQuery[i])) {
                    flag = true;
                    break;
                }

            }
        }
        if (flag == true) {
            String tableName = inputQuery[insertQueryCondition.length];
            System.out.println("tableName-->" + tableName);
            BalanceParanthesis checkbalance = new BalanceParanthesis();
            boolean isColumsyntax = checkbalance.balancedParenthensies(query);
            String columnListString = "";
            if (isColumsyntax == true) {

                HashMap<String,String> columns = columnFinder(query);
//                HashMap<Integer,String[]> columnAndTypeSeparator = columnAndTypeSeparator(columns);
                System.out.println(columns);
                ReadClass read_obj = new ReadClass();

                HashMap<String,ArrayList<String>> file = columnAndValueForInsertOperation(read_obj.read(tableName),tableName);
                System.out.println(read_obj.read(tableName));
                for(String i:columns.keySet()){
                    System.out.println(i);
                    ArrayList<String> appendnewvalues = file.get(i);
                    appendnewvalues.add(columns.get(i));

                    System.out.println(appendnewvalues);
                    System.out.println(file);
                }
                String fileFinal ="";
               for (String i:columns.keySet()){
                   String valueConcat ="";
                   String columnConcat = i+":";
                   ArrayList<String> value = file.get(i);
                   int count =0;
                   for (String item:value){
                       count++;
                       valueConcat += item;
                       if(count < value.size()){
                           valueConcat += ",";
                       }
                       else{
                           valueConcat += ";";
                       }

                   }
                   fileFinal += columnConcat+valueConcat;
                   System.out.println("concat-->"+fileFinal);

               }

                String createContentInFile = tableName+":"+"["+fileFinal+"]";
                System.out.println("concat-->"+createContentInFile);
                ReadAndWriteInFile fileWriter_obj = new ReadAndWriteInFile();

                fileWriter_obj.createFile(tableName,"",createContentInFile,false,"insert");
                System.out.println("kova-->"+createContentInFile);


            }

        }
    }}

import java.util.HashMap;

public class CreateTableClass {
    /**
     * This method is used to print the table column values.
     * @param value array of values of Column.
     */
    static void printingIterator(String[] value){

        for (String i: value){
            System.out.println("inside printing function-->"+i);
        }
    }
    /**
     * This method is used to find the column in the SQL query.
     * @param query input SQL query.
     * @return processed query with separated column for create table operations.
     */
    static String[] columnFinder(String query){
        int columnStartIndex =  query.indexOf('(');
        String trimedQuery = query.substring(columnStartIndex+1,query.length()-1);
        String[] columnNamesDelimiterSplit = trimedQuery.split(",");
        System.out.println(columnNamesDelimiterSplit.length);
        return columnNamesDelimiterSplit;
    }
    /**
     * This method is used to find the column in the SQL query.
     * @param columns columns in the given SQL query.
     * @return processed query with column and its value.
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
            printingIterator(columnDetails);
            columnsMap.put(i, columnDetails);
        }
        return columnsMap;
    }
    /**
     * This method is used to find the column in the SQL query.
     * Create a new table in the database based on the given input query.
     * @param query SQL query.
     */
    public static void createTable(String query){
        String[] createQueryCondition = {"CREATE","TABLE"};
        //String query ="CREATE TABLE Persons ( PersonID int, LastName varchar(255), FirstName varchar(255), Address varchar(255), City varchar(255))";
        System.out.println(query);
        if (query.toUpperCase().indexOf("CREATE") !=-1 )
        {
            boolean flag =true;
            String[] inputQuery = query.split(" ");
            //System.out.println(inputQuery);
            //System.out.println(inputQuery[0]);
            for (String k: inputQuery){
                //System.out.println(k);
            }
            for (String j:createQueryCondition) {
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
                if (flag == true){
                    String tableName = inputQuery[createQueryCondition.length];
                    System.out.println("tableName-->"+tableName);
                    BalanceParanthesis checkbalance =  new BalanceParanthesis();
                    boolean isColumsyntax = checkbalance.balancedParenthensies(query);
                    String columnListString ="";
                    if (isColumsyntax == true){
                        String[] columns = columnFinder(query);
                        HashMap<Integer,String[]> columnAndTypeSeparator = columnAndTypeSeparator(columns);
                        System.out.println(columnAndTypeSeparator);
                        for (int i =0;i<columnAndTypeSeparator.size();i++) {
                            System.out.println(columnAndTypeSeparator.get(i)[0]);
                           columnListString += columnAndTypeSeparator.get(i)[0]+":"+i+";\n";

                        }

                       String createContentInFile = tableName+":"+"["+columnListString+"]";
                        ReadAndWriteInFile fileWriter_obj = new ReadAndWriteInFile();

                        fileWriter_obj.createFile(tableName,"",createContentInFile,false,"table");
                        System.out.println("kova-->"+createContentInFile);
                        }

                    }

        }

    }
}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Select {
    /**
     * This method is used to fetch the tables and print its value.
     * @param trimmedQueryColumSplit fetched column values.
     * @param file content in the tables.
     */
    static void printColumnValues(String [] trimmedQueryColumSplit,HashMap<String, ArrayList<String>> file){

        for(String columnNeed:trimmedQueryColumSplit){
            System.out.println("columnNeed--->"+columnNeed);

            ArrayList a = file.get(columnNeed.trim());
            System.out.println("a--->"+a);
            for (int columnValueIndex =0; columnValueIndex<a.size();columnValueIndex++){

                System.out.println(a.get(columnValueIndex));
            }


        }

    }
    /**
     * This method is used to find the column  and its value in the SQL query based on insert condition.
     * @param columns columns in the given SQL query.
     * @param tableName Name of the table to which insert operation need to be done.
     * @return processed query with column and its value to insert the value in the table column.
     */
    static HashMap<String, ArrayList<String>> columnAndValueForSelectOperation(String columns, String tableName) {
        int indexOfTableName = columns.indexOf(tableName);
        int trimindex = indexOfTableName + tableName.length() + 2;
        String[] columnAndValue = columns.substring(trimindex, columns.indexOf(']')).split(";");
        HashMap<String, ArrayList<String>> columnAndValueMap = new HashMap<>();
        for (String i : columnAndValue) {
           // System.out.println(i);
            String readValueSplit = i.split(":")[1];
            ArrayList<String> valueSplit = new ArrayList<>(Arrays.asList(readValueSplit.split(",")));
            columnAndValueMap.put(i.split(":")[0], valueSplit);
            //System.out.println(valueSplit.get(0));


        }
        //System.out.println(columnAndValueMap);
        return columnAndValueMap;
    }

    /**
     * This method is used to select the values from the table and print them.
     * @param query columns in the given SQL query.
     */
    public static void selectValues(String query) {

       // String query = "SELECT FirstName,LastName FROM Persons;";
        int selectIndex = query.indexOf("SELECT");
        int selectLength = 7;
        int fromIndex = query.indexOf("FROM");
        int whereIndex = query.indexOf("WHERE");
        String trimmedtableNameQuery="";
        String[] trimmedQueryConditionColumnSplit;
        HashMap<String, String> selectColumnCondition = new HashMap<String, String>();
        if (selectIndex == 0) {

            String trimmedColumnQuery = query.substring(selectIndex+selectLength, fromIndex);
            if(whereIndex==-1)
            {
                trimmedtableNameQuery = query.substring(fromIndex, query.indexOf(";"));
            }
            else{
                System.out.println("kk");

            }

            String[] trimmedQueryColumSplit = trimmedColumnQuery.split(",");
            String tableName = trimmedtableNameQuery.split(" ")[1];
            ReadClass read_obj = new ReadClass();
            HashMap<String, ArrayList<String>> file = columnAndValueForSelectOperation(read_obj.read(tableName), tableName);
            int indexofAsterick = trimmedColumnQuery.indexOf("*");
            if (indexofAsterick==-1){
            printColumnValues(trimmedQueryColumSplit,file);

            }
            else{

                printColumnValues(file.keySet().toArray(new String[0]), file);
            }
        }
    }
}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Delete {
    /**
     * This method is process the column and index number of the row need to be changed;
     * @param file Name of the table to which insert operation need to be done.
     * @param conditionValueIndexInExistingTable Name of the table to which insert operation need to be done.
     */
    static void updateColumnValues( HashMap<String, ArrayList<String>> file, int conditionValueIndexInExistingTable) {

        for (String columnKey : file.keySet()) {
            ArrayList<String> w = file.get(columnKey);
            System.out.println("file-->" + w);
            w.set(conditionValueIndexInExistingTable, "000");
            System.out.println("update value"+w);

        }

    }
    /**
     * This method is used to write the data in the file after deleting the needed rows.
     * Processed sql query will be added to the respective column in the table.
     * @param file read file from the database.
     * @param tableName Name of the table to which insert operation need to be done.
     */
    static void writingDataInTheFile(HashMap<String, ArrayList<String>> file, String tableName) {


        String fileFinal = "";
        for (String i : file.keySet()) {
            String valueConcat = "";
            String columnConcat = i + ":";
            ArrayList<String> value = file.get(i);
            int count = 0;
            for (String item : value) {
                count++;
                if (!item.equals("000")) {
                    valueConcat += item;
                    if (count < value.size()) {
                        valueConcat += ",";
                    } else {
                        valueConcat += ";";
                    }

                }
            }
            fileFinal += columnConcat + valueConcat;
            System.out.println("concat-->" + fileFinal);

        }

        String createContentInFile = tableName + ":" + "[" + fileFinal + "]";
        System.out.println("concat-->" + createContentInFile);
        ReadAndWriteInFile fileWriter_obj = new ReadAndWriteInFile();

        fileWriter_obj.createFile(tableName, "", createContentInFile, false,"delete");
        System.out.println("kova-->" + createContentInFile);
    }
    /**
     * This method is used to find the column  and its value in the SQL query based on insert condition.
     * @param columns columns in the given SQL query.
     * @param tableName Name of the table to which insert operation need to be done.
     * @return processed query with column and its value to insert the value in the table column.
     */
    static HashMap<String, ArrayList<String>> columnAndValueForDeleteOperation(String columns, String tableName) {
        int indexOfTableName = columns.indexOf(tableName);
        //int trimindex = indexOfTableName + tableName.length() + 2;
        int trimindex = columns.indexOf('[')+1;
        String[] columnAndValue = columns.substring(trimindex, columns.indexOf(']')).split(";");
        HashMap<String, ArrayList<String>> columnAndValueMap = new HashMap<>();
        for (String i : columnAndValue) {
            System.out.println(i);
            String readValueSplit = i.split(":")[1];
            ArrayList<String> valueSplit = new ArrayList<>(Arrays.asList(readValueSplit.split(",")));
            columnAndValueMap.put(i.split(":")[0], valueSplit);
            System.out.println(valueSplit.get(0));


        }
        //System.out.println(columnAndValueMap);
        return columnAndValueMap;
    }
    /**
     * This method is used to delete the row in the table based on the condition.
     * @param query given SQL query.
     * @return process and deleted row in the table.
     */
    public static void deleteTable(String query) {

        //String query = "UPDATE PERSONS SET Address='Alfred Schmidt', FirstName='Frankfurt',LastName='Frankfurt' WHERE City=city1;";
        //String query = "DELETE FROM PERSONS WHERE City=city1;";
        HashMap<String, String> updateNeedColumnAndValues = new HashMap<String, String>();
        HashMap<String, String> updateColumnCondition = new HashMap<String, String>();
        if (query.indexOf("DELETE") == 0) {

            String[] inputQuery = query.split(" ");
            String tableName = inputQuery[2];

            boolean setIndex = inputQuery[1].equals("FROM");
            int columnStartIndex = 4;
            int indexOFWhere = query.indexOf("WHERE");
            int indexofSemiColon = query.indexOf(";");
            if (setIndex) {
                System.out.println(tableName);
                String trimmedQuery = query.substring(indexOFWhere, query.indexOf(";"));
                if (query.indexOf("WHERE") != -1) {
                    String trimmedQueryConditionColumn = query.substring(indexOFWhere + 6, indexofSemiColon);
                    String[] trimmedQueryConditionColumnSplit = trimmedQueryConditionColumn.split(",");
                    for (String i : trimmedQueryConditionColumnSplit) {

                        updateColumnCondition.put(i.split("=")[0], i.split("=")[1]);
                    }
                    System.out.println(updateColumnCondition);

                ReadClass read_obj = new ReadClass();
               HashMap<String, ArrayList<String>> file = columnAndValueForDeleteOperation(read_obj.read(tableName), tableName);
               //System.out.println(file);
                String conditionValue  = "";
                int conditionValueIndexInExistingTable = -1;
                for (String conditionKey : updateColumnCondition.keySet()) {
                    conditionValue  = updateColumnCondition.get(conditionKey);
                    //System.out.println(conditionValue);
                    for (int indexOfFindValue = 0; indexOfFindValue < file.get(conditionKey).size(); indexOfFindValue++) {
                        ArrayList<String> a =file.get(conditionKey);
                        if (a.get(indexOfFindValue).equals(conditionValue)){
                            conditionValueIndexInExistingTable =indexOfFindValue;
                            System.out.println("index-->"+conditionValueIndexInExistingTable);
                        }
                    }
                }

                //Update the column value in the existing table
                updateColumnValues(file,conditionValueIndexInExistingTable);
                System.out.println("file-->"+file);
                writingDataInTheFile(file,tableName);

                }


            }
        }
    }
}

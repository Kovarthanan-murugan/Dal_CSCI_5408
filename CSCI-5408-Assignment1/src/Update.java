import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Update {
    /**
     * This method is process the column and index number of the row need to be changed;
     * @param updateNeedColumnAndValues read file from the database.
     * @param file Name of the table to which insert operation need to be done.
     * @param conditionValueIndexInExistingTable Name of the table to which insert operation need to be done.
     */
    static void updateColumnValues(HashMap<String, String> updateNeedColumnAndValues,HashMap<String, ArrayList<String>> file,int conditionValueIndexInExistingTable){

        for (String columnKey : updateNeedColumnAndValues.keySet()) {
            ArrayList<String> w = file.get(columnKey);
            w.set(conditionValueIndexInExistingTable,updateNeedColumnAndValues.get(columnKey));

        }
    }
    /**
     * This method is used to write the data in the file.
     * Processed sql query will be added to the respective column in the table.
     * @param file read file from the database.
     * @param tableName Name of the table to which insert operation need to be done.
     */
    static void writingDataInTheFile(HashMap<String, ArrayList<String>> file,String tableName ){


        String fileFinal ="";
        for (String i:file.keySet()){
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

        fileWriter_obj.createFile(tableName,"",createContentInFile,false,"update");
        System.out.println("kova-->"+createContentInFile);
    }

    /**
     * This method is used to find the column  and its value in the SQL query based on update condition.
     * @param columns columns in the given SQL query.
     * @param tableName Name of the table to which update operation need to be done.
     * @return processed query with column and its value to insert the value in the table column.
     */
    static HashMap<String, ArrayList<String>> columnAndValueForUpdateOperation(String columns, String tableName) {
        int indexOfTableName = columns.indexOf(tableName);
        int trimindex = indexOfTableName + tableName.length() + 2;
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
     * This method is used to update the column in the table based on the condition.
     * @param query given SQL query.
     * @return process and update the value in the table.
     */
    public static void updateTable(String query) {

       // String query = "UPDATE PERSONS SET Address='Alfred Schmidt', FirstName='Frankfurt',LastName='Frankfurt' WHERE City=4;";
        HashMap<String, String> updateNeedColumnAndValues = new HashMap<String, String>();
        HashMap<String, String> updateColumnCondition = new HashMap<String, String>();
        if (query.indexOf("UPDATE") == 0) {

            String[] inputQuery = query.split(" ");
            String tableName = inputQuery[1];

            int setIndex = query.indexOf("SET");
            int columnStartIndex = 4;
            int indexOFWhere = query.indexOf("WHERE");
            int indexofSemiColon = query.indexOf(";");
            if (setIndex != -1) {
                System.out.println(tableName);
                String trimmedQuery = query.substring(setIndex + columnStartIndex, indexOFWhere);
                if (query.indexOf("WHERE") != -1) {
                    String trimmedQueryConditionColumn = query.substring(indexOFWhere + 6,indexofSemiColon);
                    String[] trimmedQueryConditionColumnSplit = trimmedQueryConditionColumn.split(",");
                    String[] trimmedQueryColumSplit = trimmedQuery.split(",");
                    for (String i : trimmedQueryConditionColumnSplit) {

                        updateColumnCondition.put(i.split("=")[0], i.split("=")[1]);
                    }
                    for (String i : trimmedQueryColumSplit) {
                        Update print = new Update();
                        updateNeedColumnAndValues.put(i.split("=")[0].trim(), i.split("=")[1].trim());
                    }

                }
                ReadClass read_obj = new ReadClass();
                HashMap<String, ArrayList<String>> file = columnAndValueForUpdateOperation(read_obj.read(tableName), tableName);
                String conditionValue  = "";
                int conditionValueIndexInExistingTable = -1;
                for (String conditionKey : updateColumnCondition.keySet()) {
                    conditionValue  = updateColumnCondition.get(conditionKey);
                    for (int indexOfFindValue = 0; indexOfFindValue < file.get(conditionKey).size(); indexOfFindValue++) {
                        ArrayList<String> a =file.get(conditionKey);
                        if (a.get(indexOfFindValue).equals(conditionValue)){
                            conditionValueIndexInExistingTable =indexOfFindValue;
                        }
                    }
                }

                //Update the column value in the existing table
                updateColumnValues(updateNeedColumnAndValues,file,conditionValueIndexInExistingTable);
                System.out.println("file-->"+file);
                writingDataInTheFile(file,tableName);

            }


        }
    }
}

public class CreateDataBase {
    /**
     * This method is used to create a database for the user.
     * @param query given SQL query.
     */
    public static void createDb(String query){
        String[] createQueryCondition = {"CREATE","TABLE"};
        String dataBaseNameQuery = query.substring(query.indexOf("DATABASE"),query.indexOf(";"));
        String [] dataBaseNameSplit = dataBaseNameQuery.split(" ");
        String  dataBaseName = dataBaseNameSplit[1];
        String createContentInFile = "Database";
        ReadAndWriteInFile fileWriter_obj = new ReadAndWriteInFile();

        fileWriter_obj.createFile(dataBaseName,"",createContentInFile,false,"database");


    }
}

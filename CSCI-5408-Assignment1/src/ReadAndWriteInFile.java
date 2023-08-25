import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ReadAndWriteInFile {

    /**
     * This method is used to fetch and write in to the files.
     * @param filename name of the file.
     * @param source type of SQL statement.
     * @param content content need to be written in the file.
     * @param append columns in the given SQL query.
     * @param database created database.
     */
    static void writefunction(String filename, String source, String content, boolean append, String database) {

        try {
            File myObj = new File("E:\\java-traning\\Assignment\\assignment12\\" + filename + ".txt");
            if (myObj.createNewFile()) {

            } else {
                if (database.equals("database")) {

                    System.out.println("Database exist already cant create new one");
                }

            }
            FileWriter myWriter = new FileWriter(filename + ".txt", append);
            if (source.equals("login")) {
                content = "\n" + content;
            }
            myWriter.write(content);
            myWriter.close();
            System.out.println("Table saved successfully");

        } catch (IOException e) {
            System.out.println("Syntax error please check your query");
        }
    }
    /**
     * This method is used to call by other classes to create a file.
     * @param filename name of the file.
     * @param source type of SQL statement.
     * @param content content need to be written in the file.
     * @param append columns in the given SQL query.
     * @param database created database.
     */

    public void createFile(String filename, String source, String content, boolean append, String database) {
        String changes = "";
        if (database.equals("update") || database.equals("delete") || database.equals("insert")) {
            System.out.println("Want to commit the changes or Rollback");
            Scanner changesNeed = new Scanner(System.in);
            changes = changesNeed.nextLine();
            if (changes.toUpperCase().equals("COMMIT")) {
                writefunction(filename, source, content, append, database);
            } else {

                System.out.println("Rolled Back Successfully");
            }

        } else {

            writefunction(filename, source, content, append, database);
        }
        if (!database.equals("database") && !database.equals("create")) {
            System.out.println("Want to end the transaction?");
            Scanner transaction = new Scanner(System.in);
            String transactionStatus = transaction.nextLine();

            if (transactionStatus.toUpperCase().equals("END TRANSACTION")) {
                System.exit(0);
            }
            ;

        }
    }
}

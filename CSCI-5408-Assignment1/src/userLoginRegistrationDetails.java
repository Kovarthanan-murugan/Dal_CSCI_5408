import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
public class userLoginRegistrationDetails {
    /**
     * This method is used to create user login and fill them in the usertable
     * Fetch user details during login process
     * @return created login details to the user table.
     */
    public boolean signInSignup() {
        boolean loginFlag = false;
        System.out.println("-------------------");
        System.out.println("Type create to \"Create  New Account\"");
        System.out.println("Type open to \"Open existing schema\"");

        System.out.println("--------------------");
        Scanner schemaChoiceScanner = new Scanner(System.in);
        String schemaChoice = schemaChoiceScanner.nextLine();
        System.out.println(schemaChoice);
        if (schemaChoice.toUpperCase().equals("CREATE")) {
            System.out.println("Enter the user name");
            String userName = schemaChoiceScanner.nextLine();
            System.out.println("Enter the password");
            String password = schemaChoiceScanner.nextLine();
            System.out.println("Please type your Security question");
            String securityQuestion = schemaChoiceScanner.nextLine();
            System.out.println("Set answer for this security question" + "/n" + securityQuestion);
            String securityQuestionAnswer = schemaChoiceScanner.nextLine();
            System.out.println(securityQuestionAnswer);
            String str = "[\n" + userName + "." + "UserName: " + userName + ";\n"
                    + userName + "." + "Password: " + password + ";\n"
                    + userName + "." + "SecurityQuestion: " + securityQuestion + ";\n"
                    + userName + "." + "securityQuestionAnswer: " + securityQuestionAnswer + ";\n]\n";
            ReadAndWriteInFile fileOperation = new ReadAndWriteInFile();
            fileOperation.createFile("userDetails","login",str,true,"sign");

        } else if (schemaChoice.toUpperCase().equals("OPEN")) {
            System.out.println("!!!LoginPage!!");
            System.out.println("Enter the user name");
            String userName_input = schemaChoiceScanner.nextLine();
            System.out.println("Enter the password");
            String password_input = schemaChoiceScanner.nextLine();

            try {
                HashMap<String, String> userKeyValue = new HashMap<String, String>();
                int splitIndex;
                int endIndex;
                String key = "";
                String value = "";
                String userName = "";
                File myObj = new File("userDetails.txt");
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    //System.out.println(data);
                    if (!data.equals("[") && !data.equals("]") && data.length() != 0) {
                        splitIndex = data.indexOf(":");
                        endIndex = data.indexOf(";");
                        key = data.substring(0, splitIndex);
                        value = data.substring(splitIndex + 2, endIndex);
                        userKeyValue.put(key, value);

                    }


                }
                String userCredentialName = userKeyValue.get(userName_input + ".UserName");
                String userCredentialpass = userKeyValue.get(userName_input + ".Password");
                if (userCredentialName != null) {
                    loginFlag = true;
                    System.out.println("correct user name");
                    if (userCredentialpass.equals(password_input)) {
                        System.out.println("correct password");
                        System.out.println("Please answer your security question to login");
                        String security_input = schemaChoiceScanner.nextLine();
                        String userCredentialSecurity = userKeyValue.get(userCredentialName + ".securityQuestionAnswer");
                        if (userCredentialSecurity.equals(security_input)) {
                            System.out.println("login successfully");
                        } else {
                            loginFlag = false;
                            System.out.println("Wrong Security answer");
                        }
                    } else {
                        loginFlag =false;
                        System.out.println("Wrong credentias");
                    }

                } else {
                    loginFlag = false;
                    System.out.println("Wrong credentias");
                }
                myReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

        }
     return loginFlag;
    }
}

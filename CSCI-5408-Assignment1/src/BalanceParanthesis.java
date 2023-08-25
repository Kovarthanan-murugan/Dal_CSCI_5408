import java.util.ArrayList;
import java.util.Stack;

public class BalanceParanthesis {

    public static boolean balancedParenthensies(String s) {
        Stack<Character> stack  = new Stack<Character>();
        ArrayList<Integer> charw = new ArrayList<Integer>();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            //System.out.println(c);
            if( c == '(') {
                if(i==0) {
                    charw.add(i);
                }
                stack.push(c);

            } else if(c == ')') {

                if(stack.isEmpty() || stack.pop() != '(') {
                    System.out.println("-->");
                        charw.add(i);

                    return false;
                }

            }

        }
        return stack.isEmpty();
    }
}
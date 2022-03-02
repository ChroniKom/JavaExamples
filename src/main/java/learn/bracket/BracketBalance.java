package learn.bracket;

import java.util.*;

public class BracketBalance {

    public static void main(String[] args) {
        long globalStart = System.currentTimeMillis();
        long start = System.currentTimeMillis();
        System.out.println("1: " + isBalanced("{[()]}"));
        System.out.println("finished in: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        System.out.println("2: " + isBalanced("{[]()}"));
        System.out.println("finished in: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        System.out.println("3: " + isBalanced("{[(])}"));
        System.out.println("finished in: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        System.out.println("4: " + isBalanced("}[()]{"));
        System.out.println("finished in: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        System.out.println("5: " + isBalanced(null));
        System.out.println("finished in: " + (System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        System.out.println("5: " + isBalanced(""));
        System.out.println("finished in: " + (System.currentTimeMillis() - start));

        System.out.println("all finished in: " + (System.currentTimeMillis() - globalStart));
    }

    public static boolean isBalanced(String brackets) {
        if (brackets == null) {
            return false;
        }

        Stack<Character> bracketStack = new Stack<>();
        for (int i = 0; i < brackets.length(); i++) {
            switch (brackets.charAt(i)) {
                case '{':
                case '[':
                case '(':
                    bracketStack.push(brackets.charAt(i));
                    continue;

                default:
                    if (bracketStack.empty()) {
                        return false;
                    }

                    if (!isBalancedBrackets(bracketStack.pop(), brackets.charAt(i))) {
                        return false;
                    }
            }
        }

        return true;
    }

    public static boolean isBalanced2(String brackets) {
        if (brackets == null) {
            return false;
        }

        Stack<Character> bracketStack = new Stack<>();
        for (int i = 0; i < brackets.length(); i++) {
            int type = getBracketType(brackets.charAt(i));
            if (type == 1) {
                bracketStack.push(brackets.charAt(i));
            } else if (type == -1) {
                if (bracketStack.empty()) {
                    return false;
                }

                if (!isBalancedBrackets(bracketStack.pop(), brackets.charAt(i))) {
                    return false;
                }
            } else {
                return false;
            }
        }

        return true;
    }

    private static boolean isBalancedBrackets(char left, char right) {
        return left == '{' && right == '}'
                || left == '[' && right == ']'
                || left == '(' && right == ')';
    }

    private static int getBracketType(char character) {
        switch (character) {
            case '{':
            case '[':
            case '(':
                return 1;

            case '}':
            case ']':
            case ')':
                return -1;

            default:
                return 0;
        }
    }
}

package firemage.vertretungsplan;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by flose on 25.07.2017.
 */

public class Utils {

    public static ArrayList<Integer> getAllNumbers(String s) {
        ArrayList<Integer> numbers = new ArrayList<>();
        char[] chars = s.toCharArray();
        for(Character ch : chars) {
            if(Character.isDigit(ch))
                numbers.add(Integer.parseInt(ch.toString()));
        }
        return numbers;
    }

    public static String removeAllNumbers(String s) {
        StringBuilder result = new StringBuilder();
        for(Character ch : s.toCharArray()) {
            if(!Character.isDigit(ch))
               result.append(ch.toString());
        }
        return result.toString();
    }

    public static String removeAllDots(String s) {
        StringBuilder result = new StringBuilder();
        for(Character ch : s.toCharArray()) {
            if(ch != 46)
                result.append(ch.toString());
        }
        return result.toString();
    }

    //Range should be formatted as "3-11"
    public static boolean isNumberInRange(int number, String range) throws IllegalArgumentException {
        int num1;
        int num2;

        try {
            StringTokenizer tok = new StringTokenizer(range, "-");
            if(tok.countTokens() != 2)
                throw new IllegalArgumentException("Cannot parse range " + range);

            num1 = Integer.parseInt(tok.nextToken());
            num2 = Integer.parseInt(tok.nextToken());
        } catch(Exception ex) {
            throw new IllegalArgumentException("Cannot parse range " + range, ex);
        }
        return number >= num1 && number <= num2;
    }

    //Range should be formatted as "a-d"
    public static boolean isLetterInRange(char letter, String range) throws IllegalArgumentException {

        char ch1;
        char ch2;

        try {

            StringTokenizer tok = new StringTokenizer(range, "-");

            ch1 = tok.nextToken().charAt(0);
            ch2 = tok.nextToken().charAt(0);
        } catch(Exception ex) {
            throw new IllegalArgumentException("Cannot parse range " + range, ex);
        }
        return letter>=ch1 && letter <=ch2;
    }

    public static boolean containsLetters(String s) {
        for(Character ch : s.toCharArray()) {
            if(Character.isAlphabetic(ch))
                return true;
        }
        return false;
    }

    public static boolean isRange(String s) {
        int count = 0;
        for(Character ch : s.toCharArray()) {
            if(ch == 45)
                count++;
        }
        return count == 1;
    }
}


package utils;

import java.util.Scanner;

public class Console {
    private final Scanner sc = new Scanner(System.in);

    public int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                int v = Integer.parseInt(s);
                if (v < min || v > max) {
                    System.out.println("Enter a number in [" + min + "," + max + "].");
                    continue;
                }
                return v;
            } catch (Exception e) {
                System.out.println("Invalid number, try again.");
            }
        }
    }

    public String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            if (!s.isEmpty())
                return s;
            System.out.println("Input cannot be empty.");
        }
    }

    public boolean readBoolean(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim().toLowerCase();
            if (s.equals("true") || s.equals("t") || s.equals("yes") || s.equals("y"))
                return true;
            if (s.equals("false") || s.equals("f") || s.equals("no") || s.equals("n"))
                return false;
            System.out.println("Please enter true/false or yes/no.");
        }
    }
}
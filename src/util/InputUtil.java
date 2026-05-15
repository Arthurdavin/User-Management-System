package util;

import java.util.Scanner;

public class InputUtil {
    private static final Scanner sc = new Scanner(System.in);
    public static String getString(String label){
        System.out.print(label);
        return sc.nextLine();
    }
    public static int getInt(String label){
        while (true){
            try {
                System.out.print(label);
                return Integer.parseInt(sc.nextLine());
            }
            catch (NumberFormatException e){
                System.out.println("Invalid input, Please enter a number");
            }
        }
    }
}

package bullscows;

import java.util.Scanner;

public class Main {
    final static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        CheckingSecretCode();
    }

    public static void CheckingSecretCode() {
        System.out.println("Input the length of the secret code:");
        String lengthSecretCode = sc.nextLine();
        if (!lengthSecretCode.matches("\\d+")) {
            System.out.println("Error: " + lengthSecretCode + " isn't a valid number.");
        } else if (Integer.parseInt(lengthSecretCode) > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
        } else if (Integer.parseInt(lengthSecretCode) < 1) {
            System.out.println("Error: can't generate a secret number with a length of " + lengthSecretCode +
                    " because there aren't enough unique digits.");
        } else {
            System.out.println("Input the number of possible symbols in the code:");
            String lengthSymbolCode = sc.nextLine();
            if (!lengthSymbolCode.matches("\\d+")) {
                System.out.println("Error: " + lengthSymbolCode + " isn't a valid number.");
            } else if (Integer.parseInt(lengthSymbolCode) > 36) {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            } else if (Integer.parseInt(lengthSymbolCode) < 1) {
                System.out.println("Error: can't generate a secret number with a length of " + lengthSymbolCode +
                        " because there aren't enough unique digits.");
            } else if (Integer.parseInt(lengthSecretCode) > Integer.parseInt(lengthSymbolCode)) {
                System.out.println("Error: it's not possible to generate a code with a " +
                        "length of " + lengthSecretCode + " with " + lengthSymbolCode + " unique symbols");
            } else {
                int num = Integer.parseInt(lengthSecretCode);
                int lengthSymbol = Integer.parseInt(lengthSymbolCode);
                createCode(num, lengthSymbol);
            }
        }
    }

    public static void createCode(int num, int lengthSymbol) {
        StringBuilder closeCode = new StringBuilder(num);
        String lengthCode = "";
        StringBuilder code = new StringBuilder(num);
        boolean flag = true;
        while (flag) {
            closeCode.delete(0, closeCode.length());
            flag = false;
            String numericString = "0123456789" + "abcdefghijklmnopqrstuvwxyz"; // 36
            int length = numericString.length() - (37 - lengthSymbol);
            for (int i = 0; i < num; i++) {
                closeCode.append("*");
                int index = (int) (length * Math.random());
                code.append(numericString.charAt(index));
            }
            if (lengthSymbol <= 10) {
                lengthCode = "0-" + numericString.charAt(length);
            } else {
                lengthCode = "0-9," + "a-" + numericString.charAt(length);
            }
            if (num > 1) {
                for (int i = 0; i < code.length() - 1; i++) {
                    if (Character.getNumericValue(code.charAt(i)) == Character.getNumericValue(code.charAt(i + 1))) {
                        code.delete(0, code.length());
                        flag = true;
                        break;
                    }
                    for (int j = 0; j < code.length(); j++) {
                        if (Character.getNumericValue(code.charAt(i)) == Character.getNumericValue(code.charAt(j)) && i != j) {
                            code.delete(0, code.length());
                            flag = true;
                            break;
                        }
                    }
                }
            }
        }
        System.out.println("The secret is prepared: " + closeCode + "(" + lengthCode + ").");
        System.out.println(code);
        Grade(code);
    }

        public static void Grade(StringBuilder code) {
            System.out.println("Okay, let's start a game!");
            boolean win = false;
            int turn = 1;
            while (!win) {
                System.out.println("Turn " + turn + ":");
                int cow = 0;
                int bull = 0;
                StringBuilder num = new StringBuilder(sc.next());
                for (int i = 0; i < code.length(); i++) {
                    if (code.charAt(i) == num.charAt(i)) {
                        bull++;
                    }
                    for (int j = 0; j < num.length(); j++) {
                        if (code.charAt(i) == num.charAt(j) && i != j) {
                            cow++;
                        }
                    }
                }
                if (bull != num.length()) {
                    if (bull > 0 && cow > 0) {
                        System.out.println("Grade: " + bull + " bull(s) and " + cow + " cow(s).");
                    } else if (bull == 0 && cow > 0) {
                        System.out.println("Grade: " + cow + " cow(s).");
                    } else if (bull > 0 && cow == 0) {
                        System.out.println("Grade: " + bull + " bull(s).");
                    } else {
                        System.out.println("Grade: None.");
                    }
                } else {
                    System.out.println("Grade: " + bull + " bulls\n" +
                            "Congratulations! You guessed the secret code.");
                    win = true;
                }
                turn++;
            }
        }
}
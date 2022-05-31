import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите арифметическое выражение:");
        String inputString = scanner.nextLine().toUpperCase();
        String[] inputStringArr = inputString.split(" ");
        boolean roman = checkInput(inputStringArr);
        System.out.println(calc(inputStringArr, roman));
    }

    public static String calc(String[] inputStringArr, boolean roman) throws Exception {
        if (roman) {
            inputStringArr[0] = romanToArabic(inputStringArr[0]);
            inputStringArr[2] = romanToArabic(inputStringArr[2]);
        }
        //определение типа математической операции
        char operation = inputStringArr[1].charAt(0);
        //вычисление
        int x, y, z = 0;
        x = Integer.parseInt(inputStringArr[0]);
        y = Integer.parseInt(inputStringArr[2]);
        switch (operation) {
            case '+' -> z = x + y;
            case '-' -> z = x - y;
            case '*' -> z = x * y;
            case '/' -> z = x / y;
        }
        if (roman) {
            if (z == 0) {
                throw new Exception("в римской системе нет нуля");
            } else if (z < 0) {
                throw new Exception("в римской системе нет отрицательных чисел");
            }
            return arabianToRoman(z);
        } else {
            return String.valueOf(z);
        }
    }

    public static boolean checkInput (String[] inputStringArr) throws Exception{
        String[] operators = {"+", "-", "*", "/"};
        String[] arabicNumbers = {"1","2","3","4","5","6","7","8","9","10"};
        String[] romanianNumbers = {"I","II","III","IV","V","VI","VII","VIII","IX","X"};

        //проверка на мат операцию
        String operator = "";
        for (String op : operators) {
            for (String inputString : inputStringArr) {
                if (inputString.equals(op)) {
                    operator = op;
                    break;
                }
            }
        }
        if (operator.equals("")) {
            throw new Exception("строка не является математической операцией");
        }
        //проверка на формат
        if (inputStringArr.length!=3) {
            throw new Exception("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
        //проверка корректности чисел
        int num1 = 0;
        int num2 = 0;
        for (String number : arabicNumbers) {
            if (inputStringArr[0].equals(number)) {
                num1 = 1;
            }
            if (inputStringArr[2].equals(number)) {
                num2 = 1;
            }
        }
        for (String number : romanianNumbers) {
            if (inputStringArr[0].equals(number)) {
                num1 = 2;
            }
            if (inputStringArr[2].equals(number)) {
                num2 = 2;
            }
        }
        if (num1 == 0 || num2 == 0) {
            throw new Exception("не допустимое значение числа");
        } else if (num1 != num2) {
            throw new Exception("используются одновременно разные системы счисления");
        }
        //это римская система счисления?
        return num1 == 2;
    }

    public static String arabianToRoman (int input) {
        int C, L, X, V, I;
        boolean nine = false, ninety = false;
        String outputString = "";

        //разбираем арабское число
        C = input / 100;
        input = input - C * 100;
        if (input == 90) {
            ninety = true;
        }
        L = input / 50;
        input = input - L * 50;
        X = input / 10;
        input = input - X * 10;
        if (input == 9) {
            nine = true;
        }
        V = input / 5;
        input = input - V * 5;
        I = input;

        //собираем римское число
        if (C != 0) {
            outputString = "C";
        }
        if (ninety) {
            outputString = outputString + "XC";
        } else {
            if (L != 0) {
                outputString = outputString + "L";
            }
            if (X != 0) {
                switch (X) {
                    case 1 -> outputString = outputString + "X";
                    case 2 -> outputString = outputString + "XX";
                    case 3 -> outputString = outputString + "XXX";
                    case 4 -> outputString = outputString + "XL";
                }
            }
        }
        if (nine) {
            outputString = outputString + "IX";
        } else {
            if (V != 0) {
                outputString = outputString + "V";
            }
            if (I != 0) {
                switch (I) {
                    case 1 -> outputString = outputString + "I";
                    case 2 -> outputString = outputString + "II";
                    case 3 -> outputString = outputString + "III";
                    case 4 -> outputString = outputString + "IV";
                }
            }
        }
        return outputString;
    }

    public static String romanToArabic (String input) {
        return switch (input) {
            case "I" -> "1";
            case "II" -> "2";
            case "III" -> "3";
            case "IV" -> "4";
            case "V" -> "5";
            case "VI" -> "6";
            case "VII" -> "7";
            case "VIII" -> "8";
            case "IX" -> "9";
            case "X" -> "10";
            default -> "";
        };
    }
}
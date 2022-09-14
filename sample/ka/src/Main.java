import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

class CalcObj {
    /**
     * Арабские числа
     */
    final String NUMBER_ARAB = "0123456789";
    /**
     * Составные буквы римских цифр
     */
    final String NUMBER_RIM = "IVXLCDM";
    /**
     * Операторы используемые в калькуляторе
     */
    final String OPERATOR_VAL = "+-*/";

    char OperatorValue = 0;

    String LeftValue = "";
    String RigthValue = "";

    boolean isChar(char C) {
        return ((NUMBER_ARAB.indexOf(C) >= 0) ||
               (NUMBER_RIM.indexOf(C) >= 0) ||
               (OPERATOR_VAL.indexOf(C) >= 0));
    }

    /**
     * Является число римскими цифрами
     * @param AValue передаем число
     * @return число является
     */
   boolean isNumberRim(String AValue) {
        int L = AValue.length();
        if (L > 0) {
            char C = AValue.charAt(0);
            return NUMBER_RIM.indexOf(C) >= 0;
        }
        else
            return true;
    }

    /**
     * Проверяем чтобы было оба числа римскими или только арабскими цифрами
     */
    boolean isNumberValues(String ALeftValue, String ARightValue) throws Exception {
        if (ALeftValue.isEmpty() || ARightValue.isEmpty()) {
            throw new Exception("Cтрока не является математической операцией");
        }
        // Принадлежность одной системе
        boolean bLeftValue = isNumberRim(ALeftValue);
        boolean bRightValue = isNumberRim(ARightValue);
        return (bLeftValue == bRightValue);
    }

    /**
     * Преобразование римских цифр в арабские
     */
    int RomanToArabic(String S) {
        int i = S.length() - 1;
        int r = 0;
        while (i >= 0) {
            char C = S.charAt(i);
            switch (C) {
                case ('I'):
                    r = r + 1;
                    break;
                case ('V'):
                    if (i > 0 && S.charAt(i - 1) == 'I') {
                        r = r + 4;
                        i--;
                    } else {
                        r = r + 5;
                    }
                    break;
                case ('X'):
                    if (i > 0 && S.charAt(i - 1) == 'I') {
                        r = r + 9;
                        i--;
                    } else {
                        r = r + 10;
                    }
                    break;
                case ('L'):
                    if (i > 0 && S.charAt(i - 1) == 'X') {
                        r = r + 40;
                        i--;
                    } else {
                        r = r + 50;
                    }
                    break;
                case ('C'):
                    if (i > 0 && S.charAt(i - 1) == 'X') {
                        r = r + 90;
                        i--;
                    } else {
                        r = r + 100;
                    }
                    break;
                case ('D'):
                    if (i > 0 && S.charAt(i - 1) == 'C') {
                        r = r + 400;
                        i--;
                    } else {
                        r = r + 500;
                    }
                    break;
                case ('M'):
                    if (i > 0 && S.charAt(i - 1) == 'C') {
                        r = r + 900;
                        i--;
                    } else {
                        r = r + 1000;
                    }
                    break;
            }
            i--;
        }
        return r;
    }

    String ArabicToRoman(int Value) throws Exception  {
        // Калькулятор должен принимать на вход числа от 1 до 10 включительно, не более.
        // На выходе числа не ограничиваются по величине и могут быть любыми.

       if (Value < 0)
           throw new Exception("В римской системе нет отрицательных чисел");
       if (Value == 0)
           throw new Exception("В римской системе нет нуля");

       int[] A = new int[] {1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
       String[] R = new String[] {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};

       StringBuilder RomNamber = new StringBuilder();
       for(int i = 12; i >= 0; i--){
           while (Value >= A[i]) {
               Value = Value - A[i];
               RomNamber.append(R[i]);
           }
       }
       return RomNamber.toString();
    }


    /**
     * Преобразование в int
     */
    int ToValue(String Value) {
        if (isNumberRim(Value)) {
            return RomanToArabic(Value);
        } else {
            try {
                return Integer.parseInt(Value);
            } catch (Exception e){
                return 0;
            }

        }
    }

    /**
     * Исполнить математические выражение
     */
    String Execute(String S) throws Exception {
        LeftValue = "";
        RigthValue = "";
        OperatorValue = 0;
        int L = S.length();
        for(int i = 0; i < L; i++) {
            char c = S.charAt(i);
            if (c != ' ') {
                if (isChar(c)) {
                    if (OPERATOR_VAL.indexOf(c) >= 0) {
                        if (OperatorValue == 0)
                            OperatorValue = c;
                        else
                           throw new Exception("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *) ");
                    }
                    else
                    {
                        if (OperatorValue == 0)
                            LeftValue = LeftValue + c;
                        else
                            RigthValue = RigthValue + c;
                    }
                }
                else
                    throw new Exception("Не допустимый символ [" + c + "] в " + S);
            }
        }

        if (isNumberValues(LeftValue,RigthValue)) {

            int ResultVal = 0;

            int lVal = ToValue(LeftValue);
            int rVal = ToValue(RigthValue);
            switch (OperatorValue) {
                case ('+'):
                    ResultVal = lVal + rVal;
                    break;
                case ('-'):
                    ResultVal =  lVal - rVal;
                    break;
                case ('*'):
                    ResultVal =  lVal * rVal;
                    break;
                case ('/'):
                    ResultVal =  lVal / rVal;
                    break;
                default:
                    throw new Exception("Строка не является математической операцией");
            }
            /*todo: нужно придумать что дургое*/
            if (isNumberRim(LeftValue)) {
                return ArabicToRoman(ResultVal);
            } else {
                return Integer.toString(ResultVal);
            }
        } else {
            throw new Exception("Используются одновременно разные системы счисления: " + S );
        }
    }
}

public class Main {

    public static String calc(String input) {
        try {
            CalcObj c = new CalcObj();
            return c.Execute(input.toUpperCase());
        }
        catch (Exception e) {
            System.out.println(e.toString());
            return "";
        }
    }

    public static void main(String[] args) {
        // System.out.println(calc("1 + 1"));
        // System.out.println(calc("I + I"));
        // System.out.println(calc("VI * IX"));
        // System.out.println(calc("VIII / II"));
        // System.out.println(calc("x / II"));
        InputStream inputStream = System.in;
        Reader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        try {
            String imput = bufferedReader.readLine();
            System.out.println(calc(imput));
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
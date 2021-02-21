package weasel;


import java.util.Random;


/**
 * @author Rayne
 * @version 1.0
 */


public class weaselProgram {
    public static char[] whenGeneratingRandomAlphanumericString_thenCorrect() {
        int leftLimit = 32; // numeral '0'
        int rightLimit = 127; // letter 'z'
        int targetStringLength = 28;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                //.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        char[] randomStringList = generatedString.toCharArray();
        return randomStringList;
    }


    public static void main(String[] args) {
        char[] list = whenGeneratingRandomAlphanumericString_thenCorrect();
        System.out.println(list);
    }
}


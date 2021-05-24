import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.println("Paste path here: ");
        String path = sc.next();

        System.out.print("Enter word: ");
        String inputWord = sc.next();
        if(inputWord.length() == 0|| inputWord.contains(" ")){
            throw new Exception("Enter minimum 1 letter word and no spaces");
        }

        String phrase = getPhraseFromTxtFile(path);

        String inputWordToLowerCase = inputWord.toLowerCase();

        String phraseWithoutSpecialChars = phrase.replaceAll("[^A-Za-z0-9 ]","");

        String phraseToLowerCase = phraseWithoutSpecialChars.toLowerCase();

        int phraseLength = phraseWithoutSpecialChars.replaceAll(" ", "").length();

        String[] phraseToArray = phraseToLowerCase.split(" ");

        List<String> wordsList = Arrays.stream(phraseToArray).collect(Collectors.toList());

        char[] logicWordToArray = inputWordToLowerCase.toCharArray();

        int totalCharCount = getWordCharactersInPhraseCount(logicWordToArray, phraseToLowerCase);
        ArrayList<CharWithLengthAndFrequency> charWithLengthAndFrequencies = new ArrayList<>();
        List<CharWithLengthAndFrequency> sortedCharWithLengthAndFrequencies;

        for (String word : wordsList) {

            ArrayList<Character> charsToSaveInObject = new ArrayList<>();
            int charCount = 0;
            for (char c : logicWordToArray) {
                if (word.indexOf(c) != -1) {
                    charCount = charCount + (word.length() - word.replaceAll(String.valueOf(c), "").length());
                    charsToSaveInObject.add(c);
                }
            }
            if (charCount != 0) {
                CharWithLengthAndFrequency charWithLengthAndFrequency =
                        new CharWithLengthAndFrequency(charsToSaveInObject, word.length(), calculateFrequency(charCount, totalCharCount), charCount);

                    charWithLengthAndFrequencies.stream().forEach(chLF -> {
                        if(chLF.getLetters().equals(charWithLengthAndFrequency.getLetters()) && chLF.getSize() == charWithLengthAndFrequency.getSize()){
                            charWithLengthAndFrequency.setCharCount(chLF.getCharCount() + charWithLengthAndFrequency.getCharCount());
                            chLF.setCharCount(0);
                        }
                    });
                    charWithLengthAndFrequencies.add(charWithLengthAndFrequency);
            }
        }

        sortedCharWithLengthAndFrequencies = charWithLengthAndFrequencies.stream()
                .filter(charWithLengthAndFrequency -> charWithLengthAndFrequency.getCharCount() != 0)
                .collect(Collectors.toList());

        sortedCharWithLengthAndFrequencies.stream().sorted(Comparator.comparing(CharWithLengthAndFrequency::getCharCount)).forEach(System.out::println);

        System.out.println("Total frequency: " + totalCharCount+ "/" + phraseLength + " : " + calculateFrequency(totalCharCount,phraseLength));
    }

    private static String getPhraseFromTxtFile(String path) throws IOException {
        String everything;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
        }
        return everything;
    }

    private static int getWordCharactersInPhraseCount(char[] logicWordToArray, String phraseToLowerCase) {
        String phraseWithoutWordCharacters =phraseToLowerCase;
        for(char letter : logicWordToArray) {
            phraseWithoutWordCharacters = phraseWithoutWordCharacters.replaceAll(String.valueOf(letter), "");
        }
        System.out.println(phraseWithoutWordCharacters);
        return phraseToLowerCase.length() - phraseWithoutWordCharacters.length();
    }

    private static double calculateFrequency(int charCount, int phraseLength) {
        double divideResult =  (double) charCount / (double) phraseLength;
        BigDecimal bigDecimal = new BigDecimal(divideResult).setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}
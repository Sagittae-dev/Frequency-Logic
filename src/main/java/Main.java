import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args)  {
        choicePhraseSource();
    }

    private static void choicePhraseSource()  {
        Scanner sc = new Scanner(System.in);
        System.out.println("If You want to get phrase from disc enter 1, if from keyboard enter 2");
        try {
            int choice = sc.nextInt();
            checkChoice(choice);
        } catch (Exception e) {
            System.out.println("Wrong type! ");
            choicePhraseSource();
        }
    }

    private static void checkChoice(int choice)  {
        if (choice == 1) {
            getPhraseFromDisc();
        } else if (choice == 2) {
            getPhraseFromKeyboard();

        } else {
            System.out.println("You must choice 1 or 2.");
            choicePhraseSource();
        }
    }

    private static String getInputWordFromKeyboard() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter word: ");
        String inputWord = sc.nextLine().toLowerCase();
        if (inputWord.length() == 0 || inputWord.contains(" ")) {
            System.out.println("Enter minimum 1 letter word and no spaces");
            getInputWordFromKeyboard();
        }
        return inputWord;
    }

    private static String getPathFromKeyboard() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Paste path/url here: ");
        return sc.next();
    }

    private static void getPhraseFromDisc() {
        String path = getPathFromKeyboard();
        String inputWord = getInputWordFromKeyboard();
        String phrase = getPhraseFromTxtFile(path).replaceAll("[^A-Za-z0-9 ]", "").toLowerCase();
        calculateFrequency(phrase, inputWord);
    }

    private static String getPhraseFromTxtFile(String path) {
        String everything = null;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
        } catch (IOException e) {
            System.out.println("Wrong path !");
            getPathFromKeyboard();
        }
        return everything;
    }

    private static void getPhraseFromKeyboard() {
        String inputWord = getInputWordFromKeyboard();
        System.out.println("Enter Your own phrase");
        Scanner sc = new Scanner(System.in);
        String phrase = sc.nextLine().replaceAll("[^A-Za-z0-9 ]", "").toLowerCase();
        calculateFrequency(phrase, inputWord);
    }

    private static void calculateFrequency(String phrase, String inputWord) {
        int phraseLength = phrase.replaceAll(" ", "").length();

        String[] phraseToArray = phrase.split(" ");

        List<String> wordsList = Arrays.stream(phraseToArray).collect(Collectors.toList());

        char[] inputWordToArray = inputWord.toCharArray();

        int totalCharCount = getWordCharactersInPhraseCount(inputWordToArray, phrase);
        ArrayList<CharWithLengthAndFrequency> charWithLengthAndFrequencies = new ArrayList<>();
        List<CharWithLengthAndFrequency> sortedCharWithLengthAndFrequencies;

        for (String word : wordsList) {

            ArrayList<Character> charsToSaveInObject = new ArrayList<>();
            int charCount = 0;
            for (char c : inputWordToArray) {
                if (word.indexOf(c) != -1) {
                    charCount = charCount + (word.length() - word.replaceAll(String.valueOf(c), "").length());
                    charsToSaveInObject.add(c);
                }
            }
            if (charCount != 0) {
                CharWithLengthAndFrequency charWithLengthAndFrequency =
                        new CharWithLengthAndFrequency(charsToSaveInObject, word.length(), calculateFrequencyForOneObject(charCount, totalCharCount), charCount);

                charWithLengthAndFrequencies.forEach(chLF -> {
                    if (chLF.getLetters().equals(charWithLengthAndFrequency.getLetters()) && chLF.getSize() == charWithLengthAndFrequency.getSize()) {
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

        System.out.println("Total frequency: " + totalCharCount + "/" + phraseLength + " : " + calculateFrequencyForOneObject(totalCharCount, phraseLength));
    }

    private static int getWordCharactersInPhraseCount(char[] logicWordToArray, String phraseToLowerCase) {
        String phraseWithoutWordCharacters = phraseToLowerCase;
        for (char letter : logicWordToArray) {
            phraseWithoutWordCharacters = phraseWithoutWordCharacters.replaceAll(String.valueOf(letter), "");
        }
        System.out.println(phraseWithoutWordCharacters);
        return phraseToLowerCase.length() - phraseWithoutWordCharacters.length();
    }

    private static double calculateFrequencyForOneObject(int charCount, int phraseLength) {
        double divideResult = (double) charCount / (double) phraseLength;
        BigDecimal bigDecimal = new BigDecimal(divideResult).setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}
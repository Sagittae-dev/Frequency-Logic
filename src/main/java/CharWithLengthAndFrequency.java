import java.util.List;
import java.util.Objects;

public class CharWithLengthAndFrequency {

    List<Character> letters;
    int size;
    double frequency;
    int charCount;


    public CharWithLengthAndFrequency(List<Character> letters, int size, double frequency, int charCount) {
        this.letters = letters;
        this.size = size;
        this.frequency = frequency;
        this.charCount = charCount;
    }

    public List<Character> getLetters() {
        return letters;
    }

    public void setLetters(List<Character> letters) {
        this.letters = letters;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public int getCharCount() {
        return charCount;
    }

    public void setCharCount(int charCount) {
        this.charCount = charCount;
    }

    @Override
    public String toString() {
        return "{" +
                "letter=" + letters +
                ", size=" + size +
                ", frequency=" + frequency +
                ", charCount=" + charCount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CharWithLengthAndFrequency)) return false;
        CharWithLengthAndFrequency that = (CharWithLengthAndFrequency) o;
        return size == that.size &&
                Double.compare(that.frequency, frequency) == 0 &&
                charCount == that.charCount &&
                Objects.equals(letters, that.letters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(letters, size, frequency, charCount);
    }
}

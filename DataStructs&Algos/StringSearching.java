import java.util.List;
import java.util.ArrayList;

/**
 * @author Carey Crook
 */
public class StringSearching implements StringSearchingInterface {

    @Override
    public List<Integer> boyerMoore(CharSequence pattern, CharSequence text) {
        if (pattern == null || pattern.length() == 0 || text == null) {
            throw new IllegalArgumentException();
        }
        if (text.length() < pattern.length()) {
            return new ArrayList<>();
        }
        List<Integer> res = new ArrayList<>();
        int[] lastTable = buildLastTable(pattern);
        int i = pattern.length() - 1;
        int end = i;
        while (i < text.length()) {
            char index = text.charAt(i);
            if (index == pattern.charAt(end)) {
                int j = i - 1;
                int current = end - 1;
                while (current > -1
                        && text.charAt(j) == pattern.charAt(current)) {
                    j--;
                    current--;
                }
                if (current == -1) {
                    res.add(j + 1);
                }
            }
            int shift = 1;
            int temp = pattern.length() - 1 - lastTable[index];
            if (temp > 1) {
                shift = temp;
            }
            i += shift;
        }
        return res;
    }

    @Override
    public int[] buildLastTable(CharSequence pattern) {
        if (pattern == null) { throw new IllegalArgumentException(); }
        int[] res = new int[Character.MAX_VALUE + 1];
        for (int i = 0; i < res.length; i++) {
            res[i] = -1;
        }
        for (int i = pattern.length() - 1; i > -1; i--) {
            char temp = pattern.charAt(i);
            if (res[temp] == -1) {
                res[temp] = i;
            }
        }
        return res;
    }

    @Override
    public int generateHash(CharSequence current, int length) {
        if (current == null) { throw new IllegalArgumentException(); }
        if (length < 0 || length > current.length()) {
            throw new IllegalArgumentException();
        }
        int res = 0;
        int index = 0;
        for (int i = current.length() - 1; i > -1; i--) {
            int j = i;
            int temp = current.charAt(index);
            index++;
            while (j > 0) {
                temp *= BASE;
                j--;
            }
            res += temp;
        }
        return res;
    }

    @Override
    public int updateHash(int oldHash, int length, char oldChar, char newChar) {
        int temp = oldChar;
        for (int i = length - 1; i > 0; i--) {
            temp *= BASE;
        }
        int newHash = oldHash;
        newHash -= temp;
        newHash *= BASE;
        newHash += newChar;
        return newHash;
    }

    @Override
    public List<Integer> rabinKarp(CharSequence pattern, CharSequence text) {
        if (pattern == null || pattern.length() == 0 || text == null) {
            throw new IllegalArgumentException();
        }
        if (text.length() < pattern.length()) {
            return new ArrayList<>();
        }
        List<Integer> res = new ArrayList<>();
        int patternHash = generateHash(pattern, pattern.length());
        String subString = "";
        for (int i = 0; i < pattern.length(); i++) {
            subString += text.charAt(i);
        }
        int textHash = generateHash(subString, pattern.length());
        for (int i = 0; i < text.length() - pattern.length() + 1; i++) {
            if (patternHash == textHash) {
                res.add(i);
            }
            if (i != text.length() - pattern.length()) {
                textHash = updateHash(textHash, pattern.length(),
                        text.charAt(i), text.charAt(i + pattern.length()));
            }
        }
        return res;
    }
}

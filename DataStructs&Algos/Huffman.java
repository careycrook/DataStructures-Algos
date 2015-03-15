import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Iterator;

public class Huffman {

    /**
     * Builds a frequency map of characters for the given string.
     *
     * This should just be the count of each character.
     * No character not in the input string should be in the frequency map.
     *
     * @param s the string to map
     * @return the character -> Integer frequency map
     */
    public static Map<Character, Integer> buildFrequencyMap(String s) {
        if (s == null) { throw new IllegalArgumentException(); }
        Map<Character, Integer> res = new HashMap<Character, Integer>();
        for (int i = 0; i < s.length(); i++) {
            if (res.containsKey(s.charAt(i))) {
                res.replace(s.charAt(i), res.get(s.charAt(i)) + 1);
            } else {
                res.put(s.charAt(i), 1);
            }
        }
        return res;
    }

    /**
     * Build the Huffman tree using the frequencies given.
     *
     * Add the nodes to the tree based on their natural ordering (the order
     * given by the compareTo method).
     * The frequency map will not necessarily come from the
     * {@code buildFrequencyMap()} method. Every entry in the map should be in
     * the tree.
     *
     * @param freq the frequency map to represent
     * @return the root of the Huffman Tree
     */
    public static HuffmanNode buildHuffmanTree(Map<Character, Integer> freq) {
        if (freq == null) { throw new IllegalArgumentException(); }
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<>();
        Set<Character> mapSet = freq.keySet();
        for (Character next : mapSet) {
            HuffmanNode temp = new HuffmanNode(next, freq.get(next));
            queue.add(temp);
        }
        while (queue.size() > 1) {
            HuffmanNode temp = new HuffmanNode(queue.remove(), queue.remove());
            queue.add(temp);
        }
        return queue.remove();
    }

    /**
     * Traverse the tree and extract the encoding for each character in the
     * tree.
     *
     * The tree provided will be a valid huffman tree but may not come from the
     * {@code buildHuffmanTree()} method.
     *
     * @param tree the root of the Huffman Tree
     * @return the map of each character to the encoding string it represents
     */
    public static Map<Character, EncodedString> buildEncodingMap(
            HuffmanNode tree) {
        if (tree == null) { throw new IllegalArgumentException(); }
        Map<Character, EncodedString> res = new HashMap<>();
        EncodedString code = new EncodedString();
        encodingMapRecursive(tree, code, res);
        if (res.size() == 1) {
            code.zero();
            res.replace(tree.getCharacter(), code);
        }
        return res;
    }

    private static void encodingMapRecursive(HuffmanNode current,
            EncodedString res, Map<Character, EncodedString> huffMap) {
        if (current.getLeft() == null && current.getRight() == null) {
            huffMap.put(current.getCharacter(), res);
        } else {
            EncodedString res2 = new EncodedString();
            res2.concat(res);
            res.zero();
            res2.one();
            encodingMapRecursive(current.getLeft(), res, huffMap);
            encodingMapRecursive(current.getRight(), res2, huffMap);
        }
    }

    /**
     * Encode each character in the string using the map provided.
     *
     * If a character in the string doesn't exist in the map ignore it.
     *
     * The encoding map may not necessarily come from the
     * {@code buildEncodingMap()} method, but will be correct for the tree given
     * to {@code decode()} when decoding this method's output.
     *
     * @param encodingMap the map of each character to the encoding string it
     * represents
     * @param s the string to encode
     * @return the encoded string
     */
    public static EncodedString encode(Map<Character, EncodedString>
            encodingMap, String s) {
        if (encodingMap == null || s == null) {
            throw new IllegalArgumentException();
        }
        Map<Character, Integer> freqMap = buildFrequencyMap(s);
        HuffmanNode head = buildHuffmanTree(freqMap);
        Map<Character, EncodedString> encodeMap = buildEncodingMap(head);
        EncodedString code = new EncodedString();
        for (int i = 0; i < s.length(); i++) {
            code.concat(encodeMap.get(s.charAt(i)));
        }
        return code;
    }

    /**
     * Decode the encoded string using the tree provided.
     *
     * The encoded string may not necessarily come from {@code encode()}, but
     * will be a valid string for the given tree.
     *
     * (tip: use StringBuilder to make this method faster -- concatenating
     * strings is SLOW.)
     *
     * @param tree the tree to use to decode the string
     * @param es the encoded string
     * @return the decoded string
     */
    public static String decode(HuffmanNode tree, EncodedString es) {
        if (tree == null || es == null) {
            throw new IllegalArgumentException();
        }
        String res = "";
        Iterator<Byte> iter = es.iterator();
        boolean flag = true;
        if (tree.getLeft() == null && tree.getRight() == null) {
            while (flag) {
                if (!iter.hasNext()) {
                    flag = false;
                } else {
                    res += tree.getCharacter();
                    iter.next();
                }
            }
        }
        HuffmanNode curr = tree;
        while (flag) {
            if (curr.getLeft() != null && curr.getRight() != null) {
                Byte b = iter.next();
                if (b == 0) {
                    curr = curr.getLeft();
                } else {
                    curr = curr.getRight();
                }
            }
            if (curr.getLeft() == null && curr.getRight() == null) {
                res += curr.getCharacter();
                curr = tree;
            }
            if (!iter.hasNext()) {
                flag = false;
            }
        }
        return res;
    }
}

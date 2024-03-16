//-----------------------------------------------------
//Title: Trie Homework (homework4)
//Author: Melisa SUBAŞI
//ID: 22829169256
//Section: 1
//Assignment: 4
//-----------------------------------------------------

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

// TrieNode represents a node in the Trie
class TrieNode {
    Map<Character, TrieNode> children; // Map to store children nodes
    boolean isEndOfWord; // Flag indicating the end of a word

    TrieNode() {
        children = new HashMap<>();
        isEndOfWord = false;
    }
}

// Trie class represents the Trie data structure
class Trie {
    TrieNode root; // The root of the Trie

    Trie() {
        root = new TrieNode();
    }

    // Insert a word into the Trie
    void insert(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            current.children.putIfAbsent(c, new TrieNode());
            current = current.children.get(c);
        }
        current.isEndOfWord = true;
    }

    // Search for a word in the Trie
    boolean search(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            if (!current.children.containsKey(c)) {
                return false;
            }
            current = current.children.get(c);
        }
        return current.isEndOfWord;
    }

    // Count and print the occurrences of each string as a prefix of other strings in alphabetical order
    void countPrefix() {
        TreeMap<String, Integer> prefixMap = new TreeMap<>();
        countPrefixHelper(root, "", prefixMap);

        for (Map.Entry<String, Integer> entry : prefixMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    // Helper function for counting prefixes
    private void countPrefixHelper(TrieNode node, String prefix, TreeMap<String, Integer> prefixMap) {
        if (node.isEndOfWord) {
            prefixMap.put(prefix, prefixMap.getOrDefault(prefix, 0) + 1);
        }
        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            countPrefixHelper(entry.getValue(), prefix + entry.getKey(), prefixMap);
        }
    }

    // Reverse find and print all strings ending with a given suffix in alphabetical order
    void reverseFind(String suffix) {
        Trie reversedTrie = new Trie();
        reverseFindHelper(root, "", reversedTrie);

        reversedTrie.printStrings(suffix);
    }

    // Helper function for reverse finding
    private void reverseFindHelper(TrieNode node, String word, Trie reversedTrie) {
        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            reverseFindHelper(entry.getValue(), word + entry.getKey(), reversedTrie);
        }
        if (node.isEndOfWord) {
            reversedTrie.insert(new StringBuilder(word).reverse().toString());
        }
    }

    // Print the shortest unique prefix to identify each string in the Trie
    void shortestUniquePrefix(Trie trie) {
        TrieNode current = trie.root;
        shortestUniquePrefixHelper(current, "");
    }

    // Helper function for finding the shortest unique prefix
    private void shortestUniquePrefixHelper(TrieNode node, String prefix) {
        if (node.isEndOfWord) {
            System.out.println(prefix.isEmpty() ? "not exists" : prefix);
        }
        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            shortestUniquePrefixHelper(entry.getValue(), prefix + entry.getKey());
        }
    }

    // Print strings ending with a given suffix in alphabetical order
    private void printStrings(String suffix) {
        printStringsHelper(root, "", suffix);
    }

    // Helper function for printing strings
    private void printStringsHelper(TrieNode node, String word, String suffix) {
        if (node.isEndOfWord && word.endsWith(suffix)) {
            System.out.println(new StringBuilder(word).reverse().toString());
        }
        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            printStringsHelper(entry.getValue(), word + entry.getKey(), suffix);
        }
    }

    // Print the longest common prefix for all strings in the Trie
    void longestCommonPrefix() {
        longestCommonPrefixHelper(root, "");
    }

    // Helper function for finding the longest common prefix
    private void longestCommonPrefixHelper(TrieNode node, String prefix) {
        if (node.children.size() == 1 && !node.isEndOfWord) {
            for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
                longestCommonPrefixHelper(entry.getValue(), prefix + entry.getKey());
            }
        } else {
            System.out.println(prefix.isEmpty() ? "not exists" : prefix);
        }
    }
}

// Main class for user interaction
public class TrieOperations {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Trie trie = new Trie();

        // User enters the number of inputs
        int n = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // User enters words
        for (int i = 0; i < n; i++) {
            String word = scanner.nextLine();
            trie.insert(word);
        }

        while (true) {
            System.out.println("Enter Operation Code:");
            // 1-Search 2-countPrefix 3-reverseFind 4-ShortestUniquePrefix 5-LongestCommonPrefix 6-exit
            int operationCode = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (operationCode) {
                case 1:
                    // User enters word to search
                    String searchWord = scanner.nextLine();
                    System.out.println(trie.search(searchWord));
                    break;
                case 2:
                    trie.countPrefix();
                    break;
                case 3:
                    // User enters suffix to reverse find
                    String suffix = scanner.nextLine();
                    trie.reverseFind(suffix);
                    break;
                case 4:
                    Trie newTrie = new Trie();
                    newTrie.insert("dummy"); // Insert a dummy value to avoid an empty trie
                    trie.shortestUniquePrefix(newTrie);
                    break;
                case 5:
                    trie.longestCommonPrefix();
                    break;
                case 6:
                    // Exits the program
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid operation code. Try again.");
            }
        }
    }
}

package robertincorporated.appderobert;

import android.content.Context;
import android.content.res.Resources;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.Vector;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTest {
    LinkedList<String> spanish_dic = null;
    LinkedList<String> english_dic = null;
    Resources res = Resources.getSystem();

    /**
     * Helper function to verify that the word is included within the
     * application spanish dictionary. (sequential algorithm)
     */
    private boolean isSpanishWord(String word) {
        if (spanish_dic == null) {
            // Read the dictionary.
            InputStream file = UnitTest.class.getResourceAsStream("spanish");
            try (BufferedReader br = new BufferedReader(new InputStreamReader(file))) {
                String word_line;
                while ((word_line = br.readLine()) != null) {
                    spanish_dic.add(word_line);
                }
            }
            catch (Exception ignored) { }
        }

        for (String spanish_word : spanish_dic) {
            if (spanish_word.equals(word))
                return true;
        }
        return false;
    }

    /**
     * Helper function to verify that the word is included within the
     * application english dictionary. (sequential algorithm)
     */
    private boolean isEnglishWord(String word) {
        if (english_dic == null) {
            // Read the dictionary.
            InputStream file = UnitTest.class.getResourceAsStream("american_english");
            try (BufferedReader br = new BufferedReader(new InputStreamReader(file))) {
                String word_line;
                while ((word_line = br.readLine()) != null) {
                    english_dic.add(word_line);
                }
            }
            catch (Exception ignored) { }
        }

        for (String english_word : english_dic) {
            if (english_word.equals(word))
                return true;
        }
        return false;
    }

    @Test
    public void can_generate_spanish_words_collection() {
        RandomWordListGenerator wlGen = new RandomWordListGenerator(
                RandomWordListGenerator.SPANISH,
                4,
                res
        );

        Vector<String> wordList = wlGen.getRandomWordCollection();
        assert !wordList.isEmpty();

        for (String word : wordList) {
            System.out.println(word);
            assert !word.isEmpty();
            assert isSpanishWord(word);
        }
    }

    @Test
    public void can_generate_english_words_collection() {
        RandomWordListGenerator wlGen = new RandomWordListGenerator(
                RandomWordListGenerator.ENGLISH,
                4,
                res
        );

        Vector<String> wordList = wlGen.getRandomWordCollection();
        assert  !wordList.isEmpty();

        for (String word : wordList) {
            System.out.println(word);
            assert !word.isEmpty();
            assert isEnglishWord(word);
        }
    }

    @Test
    public void can_retrieve_current_words_collection() {
        RandomWordListGenerator wlGen = new RandomWordListGenerator(
                RandomWordListGenerator.ENGLISH,
                4,
                res
        );

        Vector<String> wordList = wlGen.getRandomWordCollection();
        Vector<String> retrievedWordList = wlGen.getCurrentWordCollection();

        assert !wordList.isEmpty();
        assert !retrievedWordList.isEmpty();

        // Verify that the retrieved and generated wordlists are the same.
        for (String word : wordList)
            assert retrievedWordList.contains(wordList);

    }
}
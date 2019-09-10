package robertincorporated.appderobert;

import android.content.res.Resources;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;

/**
 * The purpose of this class is to create a collection of words that can
 * be later used for other purposes such as passwords, on a text view
 * and other nasty things.
 *
 * The collection is randomly chosen from a dictionary of either spanish
 * or english via a NON CRYPTOGRAPHICALLY SECURE java system library.
 *
 * USAGE:
 *
 * */
public class RandomWordListGenerator {
    public static final int ENGLISH = 0;
    public static final int SPANISH = 1;
    private static final int EN_DICFILE = R.raw.american_english; //= "src/main/res/raw/american_english";
    private static final int ES_DICFILE = R.raw.spanish; //= "src/main/res/raw/spanish";

    private int selectedLanguage;
    private int collectionLength;

    private Vector<String> wordVector;
    private LinkedList<String> dictionary;
    private Resources resHandle;

    public RandomWordListGenerator(int language, int collectionSize, Resources resourceHandler){
        int dic_file;
        switch (language) {
            case ENGLISH:
                selectedLanguage = ENGLISH;
                dic_file = EN_DICFILE;
                break;
            case SPANISH:
                selectedLanguage = SPANISH;
                dic_file = ES_DICFILE;
                break;
            default:
                throw new IllegalArgumentException("Language not defined.");
        }

        if (collectionSize <= 0)
            throw new IllegalArgumentException("Collection size must be strictly positive.");
        else
            collectionLength = collectionSize;

        wordVector = new Vector<>(collectionLength);
        dictionary = new LinkedList<>();
        resHandle = resourceHandler;

        // Read the dictionary.
        InputStream in = resourceHandler.openRawResource(dic_file);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String word_line;
            while ((word_line = br.readLine()) != null) {
                dictionary.add(word_line);
            }
        }
        catch (Exception exception) {
            Log.d(this.getClass().getSimpleName(), "Error reading dictionary file.");
            Log.d(this.getClass().getSimpleName(), exception.getMessage());
        }
    }

    /**
     * Getter for previously generated word collection.
     * @return Vector<String>
     */
    public Vector<String> getCurrentWordCollection() {
        if (wordVector.isEmpty())
            return getRandomWordCollection();
        return wordVector;
    }

    /**
     * Will generate and return a word collection vector of the initialized language and size.
     * Previous word collections will be cleared.
     * @return Vector<String>
     */
    public Vector<String> getRandomWordCollection() {

        wordVector.clear();

        for (int i=0; i<collectionLength; i++)
            wordVector.add(getRandomWord());

        return wordVector;
    }

    /**
     * Helper function to return a random word from repetition from the
     * initialized language dictionary.
     * @return String
     */
    private String getRandomWord() {
        Random randg = new Random();
        int ind = randg.nextInt(dictionary.size());

        return dictionary.get(ind);
    }


}

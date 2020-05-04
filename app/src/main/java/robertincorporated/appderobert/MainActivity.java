package robertincorporated.appderobert;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RandomWordListGenerator wordListGen;
    private String SelectLanguage;
    private int collectionSize = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.wordTextView);

        Button langButton = findViewById(R.id.langButton);
        SelectLanguage = langButton.getText().toString();
        wordListGen = new RandomWordListGenerator(
                getLanguage(),      // Selected language on the view
                collectionSize,     // At least 4.
                getResources()      // System resources to access the dictionaries.
        );

    }

    /**
     * Button event to generate new password phrase.
     */
    public void generatePass(View view) {
        TextView tv = findViewById(R.id.wordTextView);
        tv.setText(wordListGen.getRandomWordCollectionStr());
    }

    /**
     * Changes the language of the generated word set.
     */
    public void changeLang(View view) {
        Button langButton = findViewById(R.id.langButton);
        String language = langButton.getText().toString();

        if (language.equals("English")){
            langButton.setText(R.string.spanish_lang_button_text);
            SelectLanguage = "Spanish";
        }
        else if (language.equals("Spanish")) {
            langButton.setText(R.string.english_lang_button_text);
            SelectLanguage = "English";
        }
    }

    private int getLanguage() {
        if (SelectLanguage.equals("English"))
            return RandomWordListGenerator.ENGLISH;
        if (SelectLanguage.equals("Spanish"))
            return RandomWordListGenerator.SPANISH;

        return -1;
    }
}

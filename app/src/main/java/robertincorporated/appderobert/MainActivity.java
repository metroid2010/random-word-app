package robertincorporated.appderobert;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RandomWordListGenerator wordListGen;
    private int SelectLanguage = 0;
    private int collectionSize = 4;
    private static String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.wordTextView);

        // setup our word generator
        wordListGen = new RandomWordListGenerator(
                getResources(),      // Selected language on the view
                SelectLanguage     // At least 4
        );

        wordListGen.setCollectionLength(collectionSize);

        // setup language spinner
        Spinner langSpinner = findViewById(R.id.langSpinner);
        ArrayAdapter<String> langSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, RandomWordListGenerator.langs);
        langSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        langSpinner.setAdapter(langSpinnerAdapter);
        langSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getItemAtPosition(position).toString()) {
                    case "ENG":
                        SelectLanguage = 0;
                        wordListGen.changeDictionary(SelectLanguage);
                        break;
                    case "SPA":
                        SelectLanguage = 1;
                        wordListGen.changeDictionary(SelectLanguage);
                        break;
                    default:
                        SelectLanguage = 0;
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }

    /**
     * Button event to generate new password phrase.
     */
    public void generatePass(View view) {
        TextView tv = findViewById(R.id.wordTextView);
        password = wordListGen.getRandomWordCollection();
        tv.setText(password);
    }

    public void switchApostrophe(View view) {
        CheckBox apostrBox = findViewById(R.id.apostrophe);
        RandomWordListGenerator.apostrophe = !RandomWordListGenerator.apostrophe;
        apostrBox.setChecked(RandomWordListGenerator.apostrophe);
    }

    public void copyToClipboard(View view) {
        String label = "password";
        Button ctcbButton = findViewById(R.id.copyToCBButton);
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(label, password);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(MainActivity.this, "Password copied to clipboard", Toast.LENGTH_SHORT).show();
    }

}

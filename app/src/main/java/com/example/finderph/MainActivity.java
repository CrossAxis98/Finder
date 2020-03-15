package com.example.finderph;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button buttonCopyText;
    private Button buttonFindPhrase;
    private Button buttonReset;
    private TextView textViewPhrase;
    private TextView editText;

    private String initialText;
    private String phrase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonCopyText = (Button) findViewById(R.id.buttonCopyText);
        buttonFindPhrase = (Button) findViewById(R.id.buttonFindPhrase);
        buttonReset= (Button) findViewById(R.id.buttonReset);
        textViewPhrase = (TextView) findViewById(R.id.textViewPhrase);
        editText = (TextView) findViewById(R.id.editText);

        buttonCopyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Gets a handle to the Clipboard Manager
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

                String clip = clipboardManager.getPrimaryClip().getItemAt(0).getText().toString();
                if(clip != null) {
                    editText.setText(clip);
                    initialText = editText.getText().toString();
                }
                else
                {
                    textViewPhrase.setText("Schowek jest pusty.");
                }

            }
        });


        buttonFindPhrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(editText.getText().toString().equals("")))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Znajdź wyraz");

                    final  EditText editTextPhrase = new EditText(MainActivity.this);
                    editTextPhrase.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(editTextPhrase);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            phrase = editTextPhrase.getText().toString();
                        if(phrase.length() > 0)
                        {
                            textViewPhrase.setText(findPhrase(phrase));

                        }
                        else
                        {
                            editText.setText(initialText);
                            textViewPhrase.setText("Nic nie podałeś głupku");
                        }

                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        }
                    });

                    builder.show();


                }
                else
                {
                    textViewPhrase.setText("Najlpierw skopiuj tekst ze schowka");
                }
            }
        });


        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            editText.setText("");
            textViewPhrase.setText("");
            }
        });


    }

    /**
     * Metoda znajduje wystąpienia poszukiwanego wyrazu w tekście i zaznacza kolorując tło wokół niego.
     * @param phrase
     * @return String z liczbą wystąpień wyrazu
     */

    private String findPhrase(String phrase)
    {
        /* Konwertuj text na obiekt typu SpannableString */
        SpannableString sText = new SpannableString(initialText);

        /* Znajdź długość frazy*/

        int phraseLength= phrase.length();

        /*Znajdz pierwsze wystąpienie frazy*/
        int index = initialText.indexOf(phrase);

        /* Inicjuj zmienna licznika wystapienia fraz*/
        int phraseCounter = 0;

        while(index>=0)
        {
            phraseCounter++;

            /*Zmiana koloru tła frazy*/

            sText.setSpan(new BackgroundColorSpan(Color.YELLOW), index, index+phraseLength, 0);

            /*Znajdz indeks nastepnego wystapienia frazy*/

            index = initialText.indexOf(phrase, index+phraseLength);
        }

        editText.setText(sText);


    return String.format("I've found %d phrase matches.", phraseCounter);
    }
}

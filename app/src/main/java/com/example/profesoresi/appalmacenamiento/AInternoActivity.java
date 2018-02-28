package com.example.profesoresi.appalmacenamiento;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AInternoActivity extends AppCompatActivity {

    private EditText etTexto;
    static final String NOM_FICHERO = "MiFichero.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ainterno);

        etTexto = findViewById(R.id.etTexto);
    }

    public void guardarAI(View v){
        String string = etTexto.getText().toString() + "\n";
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(NOM_FICHERO, MODE_APPEND);
            fos.write(string.getBytes());

            almacenarSP();
            setResult(RESULT_OK, getIntent());

        } catch (FileNotFoundException e) {
            Toast.makeText(this, "El fichero no ha sido encontrado",
                    Toast.LENGTH_LONG).show();
            setResult(RESULT_CANCELED, getIntent());
        } catch (IOException e) {
            Toast.makeText(this, "Error de conexión con el fichero",
                    Toast.LENGTH_LONG).show();
            setResult(RESULT_CANCELED, getIntent());
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            finish();
        }

    }

    // almacenar el nombre del fichero en el fichero de preferencias
    private void almacenarSP() {
        SharedPreferences sp = getSharedPreferences("SPActivity", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("FICHERO_INT", NOM_FICHERO);
        editor.commit();
    }
}

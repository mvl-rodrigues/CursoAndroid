package br.com.alura.agenda2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ListaAlunosActivity extends AppCompatActivity {

    public static final String TITLE = "Agenda";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        setTitle(TITLE);
    }
}

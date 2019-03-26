package br.com.alura.agenda2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ListaAlunosActivity extends AppCompatActivity {

    public static final String TITLE = "Agenda";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        setTitle(TITLE);

        String[] alunos = {"victor","caio","luis","jessica","victor","caio","luis","jessica","victor","caio","luis","jessica"};

        ListView listaAlunos = findViewById(R.id.activity_lista_alunos_listview);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, alunos);

        listaAlunos.setAdapter(adapter);

        Button novoAluno = findViewById(R.id.lista_alunos_novo_aluno);

        novoAluno.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent vaiParaFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(vaiParaFormulario);
            }
        });

    }
}
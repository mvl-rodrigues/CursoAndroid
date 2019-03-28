package br.com.alura.agenda2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.alura.agenda2.dao.AlunoDAO;
import br.com.alura.agenda2.model.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {

    public static final String TITLE = "Agenda";
    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        setTitle(TITLE);
        listaAlunos = findViewById(R.id.activity_lista_alunos_listview);

        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Aluno aluno = (Aluno) lista.getItemAtPosition(position);
                Intent vaiParaFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                vaiParaFormulario.putExtra("aluno", aluno);
                startActivity(vaiParaFormulario);
            }
        });

        Button novoAluno = findViewById(R.id.lista_alunos_novo_aluno);
        novoAluno.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent vaiParaFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(vaiParaFormulario);
            }
        });

        registerForContextMenu(listaAlunos);
    }

    private void carregaLista() {
        AlunoDAO dao = new AlunoDAO(this);

        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        ArrayAdapter<Aluno> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, alunos);

        listaAlunos.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);

                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);

                dao.deletar(aluno);

                carregaLista();
                return false;
            }
        });

    }
}

package br.com.alura.agenda2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import br.com.alura.agenda2.dao.AlunoDAO;
import br.com.alura.agenda2.model.Aluno;

public class FormularioActivity extends AppCompatActivity {

    public static final String TITLE = "Formulário";
    public static final int CAMERA_CODE = 100;
    private FormularioHelper helper;
    private String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        setTitle(TITLE);
        helper = new FormularioHelper(this);

        Intent intent = getIntent();
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");
        if (aluno != null){
            helper.preencheAluno(aluno);
        }

        Button btnFoto = findViewById(R.id.formulario_btn_foto);
        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() +".jpg";
                File arquivoFoto = new File(caminhoFoto);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
                startActivityForResult(intentCamera, CAMERA_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_CODE) {
                helper.carregaImagem(caminhoFoto);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_formulario_ok:

                Aluno aluno = helper.pegaAluno();

                AlunoDAO dao = new AlunoDAO(this);

                if (aluno.getId() != null){
                    dao.altera(aluno);
                } else {
                    dao.inserir(aluno);
                }
                dao.close();

                Toast.makeText(FormularioActivity.this, "Aluno " +aluno.getNome()+ " salvo", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

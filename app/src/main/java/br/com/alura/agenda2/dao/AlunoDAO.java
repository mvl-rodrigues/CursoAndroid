package br.com.alura.agenda2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.agenda2.model.Aluno;

public class AlunoDAO extends SQLiteOpenHelper {

    public AlunoDAO(Context context) {
        super(context, "Agenda", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Alunos (" +
                "id INTEGER PRIMARY KEY," +
                "nome TEXT NOT NULL," +
                "endereco TEXT," +
                "telefone TEXT," +
                "site TEXT," +
                "nota REAL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Alunos";
        db.execSQL(sql);
        onCreate(db);
    }

    public void inserir(Aluno aluno) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoAluno(aluno);

        db.insert("Alunos",null, dados);

    }

    private ContentValues pegaDadosDoAluno(Aluno aluno) {
        ContentValues dados = new ContentValues();

        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getNota());
        return dados;
    }

    public List<Aluno> buscaAlunos() {

        String sql = "SELECT * FROM Alunos";

        SQLiteDatabase db = getReadableDatabase();

        Cursor ponteiro = db.rawQuery(sql, null);

        List<Aluno> alunos = new ArrayList<>();

        while (ponteiro.moveToNext()){
            Aluno aluno = new Aluno();

            aluno.setId(ponteiro.getLong(ponteiro.getColumnIndex("id")));
            aluno.setNome(ponteiro.getString(ponteiro.getColumnIndex("nome")));
            aluno.setEndereco(ponteiro.getString(ponteiro.getColumnIndex("endereco")));
            aluno.setTelefone(ponteiro.getString(ponteiro.getColumnIndex("telefone")));
            aluno.setSite(ponteiro.getString(ponteiro.getColumnIndex("site")));
            aluno.setNota(ponteiro.getDouble(ponteiro.getColumnIndex("nota")));

            alunos.add(aluno);
        }
        ponteiro.close();

        return alunos;
    }

    public void deletar(Aluno aluno) {

        SQLiteDatabase db = getWritableDatabase();

        String[] params = {aluno.getId().toString()};
        db.delete("Alunos", "id = ?", params);
    }

    public void altera(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = pegaDadosDoAluno(aluno);
        String[] params = {aluno.getId().toString()};
        db.update("Alunos", dados, "id = ?", params);
    }
}

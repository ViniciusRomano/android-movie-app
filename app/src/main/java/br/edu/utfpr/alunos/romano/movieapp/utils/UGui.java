package br.edu.utfpr.alunos.romano.movieapp.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import br.edu.utfpr.alunos.romano.movieapp.R;

/**
 * Created by romano on 11/14/17.
 */

public class UGui {

    public static void avisoErro(Context contexto, int idTexto){

        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);

        builder.setTitle(R.string.txt_alert);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setMessage(idTexto);

        builder.setNeutralButton(R.string.txt_ok,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void confirmaAcao(Context contexto,
                                    int mensagem,
                                    DialogInterface.OnClickListener listener){

        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);

        builder.setTitle(R.string.txt_confirm);
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setMessage(mensagem);

        builder.setPositiveButton(R.string.txt_yes, listener);
        builder.setNegativeButton(R.string.txt_no, listener);

        AlertDialog alert = builder.create();
        alert.show();
    }

    public static String validaCampoTexto(Context  contexto,
                                          EditText editText,
                                          int idMensagemErro){

        String texto = editText.getText().toString();

        if (UString.stringVazia(texto)){
            UGui.avisoErro(contexto, idMensagemErro);
            editText.setText(null);
            editText.requestFocus();
            return null;
        }else{
            return texto.trim();
        }
    }

}

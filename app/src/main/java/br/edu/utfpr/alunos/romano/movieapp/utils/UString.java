package br.edu.utfpr.alunos.romano.movieapp.utils;

/**
 * Created by romano on 11/14/17.
 */

public class UString {

        public static boolean stringVazia(String texto){

            if (texto == null || texto.trim().length() == 0){
                return true;
            }else{
                return false;
            }
        }
}

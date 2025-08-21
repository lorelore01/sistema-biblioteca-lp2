package com.uepb.libSys;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Biblioteca {


    private ArrayList<Livro> listaLivros = new ArrayList<>();

    public void adicionarLivro(String titulo, String autor, int anoPublicacao) {
        Livro novoLivro = new Livro(titulo, autor, anoPublicacao);
        listaLivros.add(novoLivro);
        System.out.println("Livro " + titulo + " adicionado com sucesso.");
    }

    public void adicionarLivro(Livro livro) {
        listaLivros.add(livro);
        System.out.println("Livro " + livro.getTitulo() + " adicionado com sucesso.");

    }

    public static void main(String[] args) {

        Biblioteca minhaBiblioteca = new Biblioteca();
        Livro memoriasPostumas = new Livro("Memórias Postumas de Bras Cubas", "Machado de Assis", 1881);

        minhaBiblioteca.adicionarLivro("Cujo", "Stephen King", 1981);
        minhaBiblioteca.adicionarLivro("Gravity Falls", "Caio Victor", 2016);
        minhaBiblioteca.adicionarLivro(memoriasPostumas);
        for (Livro livro : minhaBiblioteca.listaLivros) {
            System.out.println(livro.toString());
        }


    }
}
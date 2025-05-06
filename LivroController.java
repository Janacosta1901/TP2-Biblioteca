/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.estga.bibliotecalp;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author janainagomes
 */

public class LivroController {
    private List<Livro> livros;
    private int proximoId = 1;

    public LivroController() {
        livros = new ArrayList<>();
    }

    public void adicionarLivro(String isbn, String titulo, String autor) {
        livros.add(new Livro(proximoId++, isbn, titulo, autor));
    }

    public List<Livro> listarLivros() {
        return livros;
    }

    public Livro buscarPorId(int id) {
        return livros.stream().filter(l -> l.getId() == id).findFirst().orElse(null);
    }

    public void removerLivro(int id) {
        livros.removeIf(l -> l.getId() == id);
    }
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.estga.bibliotecalp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author janainagomes
 */
public class EmprestimoController {
    private List<Emprestimo> emprestimos;
    private int proximoId = 1;

    public EmprestimoController() {
        emprestimos = new ArrayList<>();
    }

   public void novoEmprestimo(Livro livro, Membro membro, Date dataDevolucaoPrevista) {
    if (livro.isDisponivel()) {
        livro.setDisponivel(false);
        emprestimos.add(new Emprestimo(proximoId++, livro, membro, new Date(), dataDevolucaoPrevista));
    }
}

    public void devolverEmprestimo(int id, Date data) {
        Emprestimo emp = buscarPorId(id);
        if (emp != null) {
            emp.devolverLivro(data);
        }
    }

    public Emprestimo buscarPorId(int id) {
        return emprestimos.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
    }

    public List<Emprestimo> listarEmprestimos() {
        return emprestimos;
    }
}

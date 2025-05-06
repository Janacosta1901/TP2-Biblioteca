/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.estga.bibliotecalp;
import java.util.Date;
/**
 *
 * @author janainagomes
 */
public class Emprestimo {
    private int id;
    private Livro livro;
    private Membro membro;
    private Date dataEmprestimo;
    private Date dataDevolucaoPrevista;
    private Date dataDevolucaoEfetiva;

    public Emprestimo(int id, Livro livro, Membro membro, Date dataEmprestimo, Date dataDevolucaoPrevista) {
        this.id = id;
        this.livro = livro;
        this.membro = membro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
        this.dataDevolucaoEfetiva = null;
       
    }

    public int getId() { return id; }
    public Livro getLivro() { return livro; }
    public Membro getMembro() { return membro; }
    public Date getDataEmprestimo() { return dataEmprestimo; }
    public Date getDataDevolucaoPrevista() { return dataDevolucaoPrevista; }
    public Date getDataDevolucaoEfetiva() { return dataDevolucaoEfetiva; }

    public void devolverLivro(Date data) {
        this.dataDevolucaoEfetiva = data;
        this.livro.setDisponivel(true);
        
        
    }

    public String getEstado() {
        if (dataDevolucaoEfetiva == null) {
            return new Date().after(dataDevolucaoPrevista) ? "Atrasado" : "Ativo";
        }
        return "Devolvido";
       
        
    }
}

    

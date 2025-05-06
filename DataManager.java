/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.estga.bibliotecalp;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author janainagomes
 */

public class DataManager {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static void salvarDados(
            List<Livro> livros,
            List<Membro> membros,
            List<Emprestimo> emprestimos
    ) {
        try {
            salvarLivros(livros);
            salvarMembros(membros);
            salvarEmprestimos(emprestimos);
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    public static void carregarDados(
            LivroController lc,
            MembroController mc,
            EmprestimoController ec
    ) {
        try {
            carregarLivros(lc);
            carregarMembros(mc);
            carregarEmprestimos(lc, mc, ec);
        } catch (Exception e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
        }
    }

    private static void salvarLivros(List<Livro> livros) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter("livros.txt"))) {
            for (Livro l : livros) {
                pw.printf("%d;%s;%s;%s;%b%n", l.getId(), l.getIsbn(), l.getTitulo(), l.getAutor(), l.isDisponivel());
            }
        }
    }

    private static void salvarMembros(List<Membro> membros) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter("membros.txt"))) {
            for (Membro m : membros) {
                pw.printf("%d;%s;%s;%s;%s%n", m.getId(), m.getNumeroSocio(), m.getPrimeiroNome(), m.getApelido(), m.getEmail());
            }
        }
    }

    private static void salvarEmprestimos(List<Emprestimo> emprestimos) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter("emprestimos.txt"))) {
            for (Emprestimo e : emprestimos) {
                String devolucao = e.getDataDevolucaoEfetiva() != null ? sdf.format(e.getDataDevolucaoEfetiva()) : "";
                pw.printf("%d;%d;%d;%s;%s;%s%n",
                        e.getId(),
                        e.getLivro().getId(),
                        e.getMembro().getId(),
                        sdf.format(e.getDataEmprestimo()),
                        sdf.format(e.getDataDevolucaoPrevista()),
                        devolucao);
            }
        }
    }

    private static void carregarLivros(LivroController lc) throws Exception {
        File f = new File("livros.txt");
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                Livro livro = new Livro(
                        Integer.parseInt(partes[0]),
                        partes[1],
                        partes[2],
                        partes[3]
                );
                livro.setDisponivel(Boolean.parseBoolean(partes[4]));
                lc.listarLivros().add(livro);
            }
        }
    }

    private static void carregarMembros(MembroController mc) throws Exception {
        File f = new File("membros.txt");
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                Membro membro = new Membro(
                        Integer.parseInt(partes[0]),
                        partes[1],
                        partes[2],
                        partes[3],
                        partes[4]
                );
                mc.listarMembros().add(membro);
            }
        }
    }

    private static void carregarEmprestimos(LivroController lc, MembroController mc, EmprestimoController ec) throws Exception {
        File f = new File("emprestimos.txt");
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                Livro livro = lc.buscarPorId(Integer.parseInt(partes[1]));
                Membro membro = mc.buscarPorId(Integer.parseInt(partes[2]));

                if (livro != null && membro != null) {
                    Date dataEmp = sdf.parse(partes[3]);
                    Date dataPrev = sdf.parse(partes[4]);
                    Date dataDev = partes[5].isEmpty() ? null : sdf.parse(partes[5]);

                    Emprestimo e = new Emprestimo(Integer.parseInt(partes[0]), livro, membro, dataEmp, dataPrev);
                    if (dataDev != null) e.devolverLivro(dataDev);
                    ec.listarEmprestimos().add(e);
                }
            }
        }
    }
}


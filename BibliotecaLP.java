/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.estga.bibliotecalp;

import javax.swing.*;
/**
 *
 * @author janainagomes
 */

public class BibliotecaLP extends JFrame {

    public BibliotecaLP(LivroController lc, MembroController mc, EmprestimoController ec) {
        setTitle("Sistema de Gestão de Biblioteca");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Criação do menu
        JMenuBar menuBar = new JMenuBar();

        JMenu menuLivros = new JMenu("Livros");
        JMenuItem gestaoLivros = new JMenuItem("Gestão de Livros");
        gestaoLivros.addActionListener(e -> new LivroForm(lc).setVisible(true));
        menuLivros.add(gestaoLivros);

        JMenu menuMembros = new JMenu("Membros");
        JMenuItem gestaoMembros = new JMenuItem("Gestão de Membros");
        gestaoMembros.addActionListener(e -> new MembroForm(mc).setVisible(true));
        menuMembros.add(gestaoMembros);

        JMenu menuEmprestimos = new JMenu("Empréstimos");
        JMenuItem gestaoEmprestimos = new JMenuItem("Gestão de Empréstimos");
        gestaoEmprestimos.addActionListener(e -> new EmprestimoForm(lc, mc, ec).setVisible(true));
        menuEmprestimos.add(gestaoEmprestimos);

        JMenu menuSair = new JMenu("Sair");
        JMenuItem sair = new JMenuItem("Fechar");
        sair.addActionListener(e -> System.exit(0));
        menuSair.add(sair);

        // Adiciona menus à barra
        menuBar.add(menuLivros);
        menuBar.add(menuMembros);
        menuBar.add(menuEmprestimos);
        menuBar.add(menuSair);

        setJMenuBar(menuBar);
    }

    public static void main(String[] args) {
        LivroController lc = new LivroController();
MembroController mc = new MembroController();
EmprestimoController ec = new EmprestimoController();

// Carregar dados
DataManager.carregarDados(lc, mc, ec);

// Salvar ao sair
Runtime.getRuntime().addShutdownHook(new Thread(() ->
    DataManager.salvarDados(lc.listarLivros(), mc.listarMembros(), ec.listarEmprestimos())
));

        // Executa a interface principal
        SwingUtilities.invokeLater(() -> new BibliotecaLP(lc, mc, ec).setVisible(true));
    }
}



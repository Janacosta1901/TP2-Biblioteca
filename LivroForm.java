/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.estga.bibliotecalp;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
/**
 *
 * @author janainagomes
 */
public class LivroForm extends JFrame {
    private LivroController controller;
    private JTable tabela;
    private DefaultTableModel model;

    public LivroForm(LivroController controller) {
        this.controller = controller;

        setTitle("Gestão de Livros");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        // Tabela
        model = new DefaultTableModel(new Object[]{"ID", "ISBN", "Título", "Autor", "Disponível"}, 0);
        tabela = new JTable(model);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = new JPanel();

        JButton btnAdicionar = new JButton("Adicionar Livro");
        btnAdicionar.addActionListener(this::adicionarLivro);

        JButton btnRemover = new JButton("Remover Selecionado");
        btnRemover.addActionListener(e -> removerLivroSelecionado());

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnRemover);

        add(painelBotoes, BorderLayout.SOUTH);

        atualizarTabela();
    }

    private void atualizarTabela() {
        model.setRowCount(0); // limpa tabela
        List<Livro> livros = controller.listarLivros();
        for (Livro l : livros) {
            model.addRow(new Object[]{
                l.getId(), l.getIsbn(), l.getTitulo(), l.getAutor(), l.isDisponivel() ? "Sim" : "Não"
            });
        }
    }

    private void adicionarLivro(ActionEvent e) {
        JTextField isbn = new JTextField();
        JTextField titulo = new JTextField();
        JTextField autor = new JTextField();

        Object[] campos = {
            "ISBN:", isbn,
            "Título:", titulo,
            "Autor:", autor
        };

        int resultado = JOptionPane.showConfirmDialog(this, campos, "Adicionar Livro", JOptionPane.OK_CANCEL_OPTION);

        if (resultado == JOptionPane.OK_OPTION) {
            if (!isbn.getText().isEmpty() && !titulo.getText().isEmpty() && !autor.getText().isEmpty()) {
                controller.adicionarLivro(isbn.getText(), titulo.getText(), autor.getText());
                atualizarTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos!");
            }
        }
    }

    private void removerLivroSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha != -1) {
            int id = (int) model.getValueAt(linha, 0);
            controller.removerLivro(id);
            atualizarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um livro para remover.");
        }
    }
}

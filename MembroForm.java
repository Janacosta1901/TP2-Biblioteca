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
public class MembroForm extends JFrame {
    private MembroController controller;
    private JTable tabela;
    private DefaultTableModel model;

    public MembroForm(MembroController controller) {
        this.controller = controller;

        setTitle("Gestão de Membros");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new Object[]{"ID", "Número Sócio", "Nome", "Apelido", "Email"}, 0);
        tabela = new JTable(model);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();

        JButton btnAdicionar = new JButton("Adicionar Membro");
        btnAdicionar.addActionListener(this::adicionarMembro);

        JButton btnRemover = new JButton("Remover Selecionado");
        btnRemover.addActionListener(e -> removerMembroSelecionado());

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnRemover);

        add(painelBotoes, BorderLayout.SOUTH);

        atualizarTabela();
    }

    private void atualizarTabela() {
        model.setRowCount(0);
        List<Membro> membros = controller.listarMembros();
        for (Membro m : membros) {
            model.addRow(new Object[]{
                m.getId(), m.getNumeroSocio(), m.getPrimeiroNome(), m.getApelido(), m.getEmail()
            });
        }
    }

    private void adicionarMembro(ActionEvent e) {
        JTextField numeroSocio = new JTextField();
        JTextField primeiroNome = new JTextField();
        JTextField apelido = new JTextField();
        JTextField email = new JTextField();

        Object[] campos = {
            "Número de Sócio:", numeroSocio,
            "Primeiro Nome:", primeiroNome,
            "Apelido:", apelido,
            "Email:", email
        };

        int resultado = JOptionPane.showConfirmDialog(this, campos, "Adicionar Membro", JOptionPane.OK_CANCEL_OPTION);

        if (resultado == JOptionPane.OK_OPTION) {
            if (!numeroSocio.getText().isEmpty() && !primeiroNome.getText().isEmpty() &&
                !apelido.getText().isEmpty() && !email.getText().isEmpty()) {

                controller.adicionarMembro(numeroSocio.getText(), primeiroNome.getText(), apelido.getText(), email.getText());
                atualizarTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!");
            }
        }
    }

    private void removerMembroSelecionado() {
        int linha = tabela.getSelectedRow();
        if (linha != -1) {
            int id = (int) model.getValueAt(linha, 0);
            controller.removerMembro(id);
            atualizarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um membro para remover.");
        }
    }
}


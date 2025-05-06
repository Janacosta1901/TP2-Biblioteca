/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.estga.bibliotecalp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EmprestimoForm extends JFrame {
    private LivroController livroController;
    private MembroController membroController;
    private EmprestimoController emprestimoController;

    private JComboBox<Livro> comboLivros;
    private JComboBox<Membro> comboMembros;
    private JTable tabela;
    private DefaultTableModel model;

    public EmprestimoForm(LivroController lc, MembroController mc, EmprestimoController ec) {
        this.livroController = lc;
        this.membroController = mc;
        this.emprestimoController = ec;

        setTitle("Gestão de Empréstimos");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel de topo - Novo Empréstimo
        JPanel topo = new JPanel(new FlowLayout());

        comboMembros = new JComboBox<>(mc.listarMembros().toArray(new Membro[0]));
        comboLivros = new JComboBox<>(lc.listarLivros().stream()
                .filter(Livro::isDisponivel).toArray(Livro[]::new));

        JButton btnEmprestar = new JButton("Fazer Empréstimo");
        btnEmprestar.addActionListener(this::novoEmprestimo);

        topo.add(new JLabel("Membro:"));
        topo.add(comboMembros);
        topo.add(new JLabel("Livro:"));
        topo.add(comboLivros);
        topo.add(btnEmprestar);

        add(topo, BorderLayout.NORTH);

        // Tabela de empréstimos
        model = new DefaultTableModel(new Object[]{"ID", "Livro", "Membro", "Data Emp.", "Data Prev.", "Data Dev.", "Estado"}, 0);
        tabela = new JTable(model);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Painel de botões inferiores
        JPanel botoes = new JPanel();

        JButton btnDevolver = new JButton("Devolver Selecionado");
        btnDevolver.addActionListener(this::devolverEmprestimo);

        JButton btnExportar = new JButton("Exportar CSV");
        btnExportar.addActionListener(this::exportarCSV);

        botoes.add(btnDevolver);
        botoes.add(btnExportar);

        add(botoes, BorderLayout.SOUTH);

        atualizarTabela();
    }

    private void novoEmprestimo(ActionEvent e) {
        Livro livro = (Livro) comboLivros.getSelectedItem();
        Membro membro = (Membro) comboMembros.getSelectedItem();
        if (livro != null && membro != null) {
            Date dataPrevista = new Date(System.currentTimeMillis() + 14L * 24 * 60 * 60 * 1000); // 14 dias
            emprestimoController.novoEmprestimo(livro, membro, dataPrevista);
            atualizarTabela();
            atualizarComboLivros(); // atualiza a combo após empréstimo
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um livro e um membro.");
        }
    }

    private void atualizarTabela() {
        model.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Emprestimo e : emprestimoController.listarEmprestimos()) {
            model.addRow(new Object[]{
                    e.getId(),
                    e.getLivro().getTitulo(),
                    e.getMembro().getPrimeiroNome() + " " + e.getMembro().getApelido(),
                    sdf.format(e.getDataEmprestimo()),
                    sdf.format(e.getDataDevolucaoPrevista()),
                    e.getDataDevolucaoEfetiva() != null ? sdf.format(e.getDataDevolucaoEfetiva()) : "",
                    e.getEstado()
            });
        }
    }

    private void devolverEmprestimo(ActionEvent e) {
        int linha = tabela.getSelectedRow();
        if (linha != -1) {
            int id = (int) model.getValueAt(linha, 0);
            emprestimoController.devolverEmprestimo(id, new Date());
            atualizarTabela();
            atualizarComboLivros(); // repõe livro devolvido na combo
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um empréstimo para devolver.");
        }
    }

    private void exportarCSV(ActionEvent e) {
        JFileChooser fc = new JFileChooser();
        int op = fc.showSaveDialog(this);
        if (op == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try (PrintWriter pw = new PrintWriter(new FileWriter(file + ".csv"))) {
                pw.println("TituloLivro,NomeCompletoMembro,DataEmprestimo,DataDevolucaoPrevista,Estado");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                for (Emprestimo emp : emprestimoController.listarEmprestimos()) {
                    pw.printf("%s,%s,%s,%s,%s%n",
                            emp.getLivro().getTitulo(),
                            emp.getMembro().getPrimeiroNome() + " " + emp.getMembro().getApelido(),
                            sdf.format(emp.getDataEmprestimo()),
                            sdf.format(emp.getDataDevolucaoPrevista()),
                            emp.getEstado()
                    );
                }
                JOptionPane.showMessageDialog(this, "CSV exportado com sucesso!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao exportar: " + ex.getMessage());
            }
        }
    }

    // 🔁 Método público para atualizar a combo de livros
    public void atualizarComboLivros() {
        if (comboLivros != null) {
            comboLivros.setModel(new DefaultComboBoxModel<>(
                    livroController.listarLivros().stream()
                            .filter(Livro::isDisponivel)
                            .toArray(Livro[]::new)
            ));
        }
    }
}


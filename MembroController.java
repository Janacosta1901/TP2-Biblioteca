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

public class MembroController {
    private List<Membro> membros;
    private int proximoId = 1;

    public MembroController() {
        membros = new ArrayList<>();
    }

    public void adicionarMembro(String numeroSocio, String primeiroNome, String apelido, String email) {
        membros.add(new Membro(proximoId++, numeroSocio, primeiroNome, apelido, email));
    }

    public List<Membro> listarMembros() {
        return membros;
    }

    public Membro buscarPorId(int id) {
        return membros.stream().filter(m -> m.getId() == id).findFirst().orElse(null);
    }

    public void removerMembro(int id) {
        membros.removeIf(m -> m.getId() == id);
    }
}

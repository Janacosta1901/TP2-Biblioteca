/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.estga.bibliotecalp;

/**
 *
 * @author janainagomes
 */

public class Membro {
    private int id;
    private String numeroSocio;
    private String primeiroNome;
    private String apelido;
    private String email;

    public Membro(int id, String numeroSocio, String primeiroNome, String apelido, String email) {
        this.id = id;
        this.numeroSocio = numeroSocio;
        this.primeiroNome = primeiroNome;
        this.apelido = apelido;
        this.email = email;
    }

    public int getId() { return id; }
    public String getNumeroSocio() { return numeroSocio; }
    public String getPrimeiroNome() { return primeiroNome; }
    public String getApelido() { return apelido; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return numeroSocio + " - " + primeiroNome + " " + apelido;
    }
}

    


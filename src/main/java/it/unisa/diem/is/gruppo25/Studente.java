/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.is.gruppo25;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Studente {
    
    private String nome;
    private String cognome;
    private String matricola;           
    private String emailIstituzionale;
    private List<Prestito> prestitiAttivi = new ArrayList<>();

    public Studente(String nome, String cognome, String matricola, String emailIstituzionale) {
        this.nome = nome;
        this.cognome = cognome;
        this.matricola = matricola;
        this.emailIstituzionale = emailIstituzionale;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getMatricola() {
        return matricola;
    }

    public void setEmailIstituzionale(String emailIstituzionale) {
        this.emailIstituzionale = emailIstituzionale;
    }

    public List<Prestito> getPrestitiAttivi() {
        return new ArrayList<>(prestitiAttivi);
    }
    
    void aggiungiPrestito(Prestito p) {
    }

    void chiudiPrestito(Prestito p) {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.matricola);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Studente other = (Studente) obj;
        if (!Objects.equals(this.matricola, other.matricola)) {
            return false;
        }
        return true;
    }
    
    
    
}

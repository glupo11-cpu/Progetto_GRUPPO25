/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.is.gruppo25;

import java.time.LocalDate;
import java.util.Objects;

public class Libro {

    private String titolo;
    private String autore;
    private int annoPubblicazione;
    private String codiceIsbn;
    private int copieDisponibili;

    public Libro(String titolo, String autore, int annoPubblicazione,
                 String codiceIsbn, int copieDisponibili) {
        
        this.titolo = titolo;
        this.autore = autore;
        this.annoPubblicazione = annoPubblicazione;
        this.codiceIsbn = codiceIsbn;
        this.copieDisponibili = copieDisponibili;
    }

    public String getTitolo() { 
        return titolo; 
    }
    
    public void setTitolo(String titolo) { 
        this.titolo = titolo;
    }

    public String getAutore() { 
        return autore; 
    }
    public void setAutore(String autore) { 
        this.autore = autore;
    }

    public int getAnnoPubblicazione() { 
        return annoPubblicazione; 
    }
    
    public void setAnnoPubblicazione(int annoPubblicazione) { 
        this.annoPubblicazione = annoPubblicazione;
    }

    public String getCodiceIsbn() { 
        return codiceIsbn; 
    }

    public int getCopieDisponibili() { 
        return copieDisponibili; 
    }

    public void incrementaCopie() {
    }

    public void decrementaCopie() {
    }

    public void setCopieDisponibili(int nuoveCopie) {
        this.copieDisponibili = copieDisponibili;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.codiceIsbn);
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
        final Libro other = (Libro) obj;
        if (!Objects.equals(this.codiceIsbn, other.codiceIsbn)) {
            return false;
        }
        return true;
    }


}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.is.gruppo25;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
/**
 * @brief Rappresenta un volume della biblioteca.
 * La classe Libro contiene informazioni sul titolo, autore, anno di pubblicazione, codice ISBN
 * e il numero di copie disponibili per il prestito.
 */
public class Libro implements Serializable{

    private static final long serialVersionUID = 1L;
    private String titolo;
    private String autore;
    private int annoPubblicazione;
    private String codiceIsbn;
    private int copieDisponibili;
/**
     * @brief Costruisce un nuovo oggetto Libro.
     * @param [in] titolo il titolo del libro
     * @param [in] autore l'autore del libro
     * @param [in] annoPubblicazione l'anno di pubblicazione
     * @param [in] codiceIsbn il codice ISBN univoco
     * @param [in] copieDisponibili il numero iniziale di copie disponibili
     * @pre titolo, autore, codiceIsbn != null AND copieDisponibili >= 0
     * @post i campi del libro sono inizializzati con i valori passati
     */
    public Libro(String titolo, String autore, int annoPubblicazione,
                 String codiceIsbn, int copieDisponibili) {
        if (titolo == null || titolo.trim().isEmpty())
            throw new IllegalArgumentException("Titolo mancante o nullo.");
        if (autore == null || autore.trim().isEmpty())
            throw new IllegalArgumentException("Autore mancante o nullo.");
        if (codiceIsbn == null || codiceIsbn.trim().isEmpty())
            throw new IllegalArgumentException("Codice ISBN mancante o nullo.");
        if (copieDisponibili < 0)
            throw new IllegalArgumentException("Le copie disponibili non possono essere negative.");
        if (annoPubblicazione > LocalDate.now().getYear())
            throw new IllegalArgumentException("Anno di pubblicazione futuro non valido.");
        this.titolo = titolo;
        this.autore = autore;
        this.annoPubblicazione = annoPubblicazione;
        this.codiceIsbn = codiceIsbn;
        this.copieDisponibili = copieDisponibili;
    }
/**
     * @brief Restituisce il titolo del libro.
     * @return il titolo
     * @post il risultato è il titolo del libro
     */
    public String getTitolo() { 
        return titolo; 
    }
    /**
     * @brief Imposta il titolo del libro.
     * @param [in] titolo il nuovo titolo
     * @post il campo titolo è aggiornato
     */
    public void setTitolo(String titolo) { 
        if (titolo == null || titolo.trim().isEmpty())
            throw new IllegalArgumentException("Il titolo non può essere vuoto.");
        this.titolo = titolo;
    }
/**
     * @brief Restituisce l'autore del libro.
     * @return l'autore
     * @post il risultato è l'autore del libro
     */
    public String getAutore() { 
        return autore; 
    }
    /**
     * @brief Imposta l'autore del libro.
     * @param [in] autore il nuovo autore
     * @post il campo autore è aggiornato
     */
    public void setAutore(String autore) { 
        if (autore == null || autore.trim().isEmpty())
            throw new IllegalArgumentException("L'autore non può essere vuoto.");
        this.autore = autore;
    }
/**
     * @brief Restituisce l'anno di pubblicazione del libro.
     * @return l'anno di pubblicazione
     * @post il risultato è l'anno di pubblicazione del libro
     */
    public int getAnnoPubblicazione() { 
        return annoPubblicazione; 
    }
    /**
     * @brief Imposta l'anno di pubblicazione del libro.
     * @param [in] annoPubblicazione il nuovo anno
     * @post il campo annoPubblicazione è aggiornato
     */
    public void setAnnoPubblicazione(int annoPubblicazione) { 
        if (annoPubblicazione > LocalDate.now().getYear())
            throw new IllegalArgumentException("L'anno di pubblicazione non può essere futuro.");
        this.annoPubblicazione = annoPubblicazione;
    }
/**
     * @brief Restituisce il codice ISBN univoco del libro.
     * @return il codice ISBN
     * @post il risultato è il codice ISBN del libro
     */
    public String getCodiceIsbn() { 
        return codiceIsbn; 
    }
/**
     * @brief Restituisce il numero di copie disponibili per il prestito.
     * @return il numero di copie disponibili
     * @post il risultato è il numero attuale di copie disponibili
     */
    public int getCopieDisponibili() { 
        return copieDisponibili; 
    }
/**
     * @brief Incrementa il numero di copie disponibili.
     * (ad esempio, dopo una restituzione).
     * @post copieDisponibili = copieDisponibili @pre + 1
     */
    public void incrementaCopie() {
        this.copieDisponibili++;
    }
/**
     * @brief Decrementa il numero di copie disponibili.
     * (ad esempio, dopo un prestito).
     * @pre copieDisponibili > 0
     * @post copieDisponibili = copieDisponibili @pre - 1
     */
    public void decrementaCopie() {
        if (this.copieDisponibili <= 0) {
            throw new IllegalStateException("Impossibile decrementare: le copie sono esaurite.");
        }
        this.copieDisponibili--;
    }
/**
     * @brief Imposta direttamente il numero di copie disponibili.
     * @param [in] nuoveCopie il nuovo numero di copie
     * @pre nuoveCopie >= 0
     * @post il campo copieDisponibili è aggiornato con nuoveCopie
     */
    public void setCopieDisponibili(int nuoveCopie) {
        if (nuoveCopie < 0) {
             throw new IllegalArgumentException("Il numero di copie non può essere negativo.");
        }
        this.copieDisponibili = nuoveCopie;
    }
/**
     * @brief Genera l'hashCode basato sul codice ISBN.
     * @return il valore hash code
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.codiceIsbn);
        return hash;
    }
 /**
     * @brief Confronta due oggetti Libro per l'uguaglianza.
     * Il confronto si basa sul codice ISBN.
     * @param [in] obj l'oggetto da confrontare
     * @return true se gli oggetti sono uguali (hanno lo stesso ISBN), false altrimenti
     */
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

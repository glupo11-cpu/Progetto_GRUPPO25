/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.is.gruppo25;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 * @brief Rappresenta uno studente iscritto all'università.
 * La classe Studente contiene dati anagrafici, matricola e la lista dei prestiti attivi.
 */
public class Studente implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private String nome;
    private String cognome;
    private String matricola;           
    private String emailIstituzionale;
    private List<Prestito> prestitiAttivi = new ArrayList<>();
/**
     * @brief Costruisce un nuovo oggetto Studente.
     * @param [in] nome il nome dello studente
     * @param [in] cognome il cognome dello studente
     * @param [in] matricola la matricola univoca dello studente
     * @param [in] emailIstituzionale l'indirizzo email istituzionale dello studente
     * @pre nome, cognome, matricola, emailIstituzionale != null AND matricola è unica
     * @post i campi dello studente sono inizializzati con i valori passati
     * @post prestitiAttivi è una lista vuota
     */
    public Studente(String nome, String cognome, String matricola, String emailIstituzionale) {
        this.nome = nome;
        this.cognome = cognome;
        this.matricola = matricola;
        this.emailIstituzionale = emailIstituzionale;
    }
/**
     * @brief Restituisce il nome dello studente.
     * @return il nome
     * @post il risultato è il nome dello studente
     */
    public String getNome() {
        return nome;
    }
/**
     * @brief Imposta il nome dello studente.
     * @param [in] nome il nuovo nome
     * @pre nome != null
     * @post il campo nome è aggiornato
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
/**
     * @brief Restituisce il cognome dello studente.
     * @return il cognome
     * @post il risultato è il cognome dello studente
     */
    public String getCognome() {
        return cognome;
    }
/**
     * @brief Imposta il cognome dello studente.
     * @param [in] cognome il nuovo cognome
     * @pre cognome != null
     * @post il campo cognome è aggiornato
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
/**
     * @brief Restituisce l'email istituzionale dello studente.
     * @return l'email istituzionale
     * @post il risultato è l'email istituzionale dello studente
     */
    public String getEmailIstituzionale() {
        return emailIstituzionale;
    }
/**
     * @brief Imposta l'email istituzionale dello studente.
     * @param [in] emailIstituzionale la nuova email
     * @pre emailIstituzionale != null
     * @post il campo emailIstituzionale è aggiornato
     */
    public void setEmailIstituzionale(String emailIstituzionale) {
        this.emailIstituzionale = emailIstituzionale;
    }
/**
     * @brief Restituisce la matricola dello studente.
     * @return la matricola
     * @post il risultato è la matricola dello studente
     */
    public String getMatricola() {
        return matricola;
    }
/**
     * @brief Restituisce la lista dei prestiti attualmente attivi.
     * @return la lista dei prestiti attivi
     * @post il risultato è la lista dei prestiti non ancora restituiti associati a questo studente
     */
    public List<Prestito> getPrestitiAttivi() {
        return prestitiAttivi;
    }
    public void aggiungiPrestito(Prestito p) {
        if (prestitiAttivi.size() >= 3) throw new IllegalStateException("Limite prestiti superato");
        prestitiAttivi.add(p);
    }
    public void chiudiPrestito(Prestito p) {
        boolean rimosso = prestitiAttivi.remove(p);
        if (!rimosso) {
            prestitiAttivi.removeIf(curr -> 
                curr.getLibro().getCodiceIsbn().equals(p.getLibro().getCodiceIsbn()) &&
                curr.getDataInizio().equals(p.getDataInizio())
            );
        }
    }
/**
     * @brief Genera l'hashCode basato sulla matricola.
     * @return il valore hash code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.matricola);
        return hash;
    }
/**
     * @brief Confronta due oggetti Studente per l'uguaglianza.
     * Il confronto si basa sulla matricola.
     * @param [in] obj l'oggetto da confrontare
     * @return true se gli oggetti sono uguali (hanno la stessa matricola), false altrimenti
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
        final Studente other = (Studente) obj;
        if (!Objects.equals(this.matricola, other.matricola)) {
            return false;
        }
        return true;
    }

    
}
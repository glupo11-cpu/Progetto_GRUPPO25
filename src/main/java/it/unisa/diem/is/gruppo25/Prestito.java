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
 * @brief Rappresenta un prestito di un libro a uno studente.
 * La classe {@code Prestito} contiene informazioni sul libro, lo studente coinvolto, le date di inizio e
 * di restituzione (prevista ed effettiva).
 */
public class Prestito implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private final Studente studente;
    private final Libro libro;
    private final LocalDate dataInizio;
    private final LocalDate dataPrevistaRestituzione;
    private LocalDate dataRestituzioneEffettiva;
/**
     * @brief Costruisce un nuovo oggetto Prestito.
     * La data di inizio viene impostata automaticamente alla data corrente.
     * @param [in] studente l'oggetto Studente che richiede il prestito
     * @param [in] libro l'oggetto Libro oggetto del prestito
     * @param [in] dataPrevistaRestituzione la data entro cui il libro deve essere restituito
     * @pre studente != null AND libro != null AND dataPrevistaRestituzione != null
     * @post dataInizio è impostata alla data corrente
     * @post dataRestituzioneEffettiva == null (il prestito è attivo)
     */
    public Prestito(Studente studente, Libro libro, LocalDate dataPrevistaRestituzione) {
        this.studente = studente;
        this.libro = libro;
        this.dataInizio = LocalDate.now();
        this.dataPrevistaRestituzione = dataPrevistaRestituzione;
    }
/**
     * @brief Restituisce l'oggetto Studente associato.
     * @return l'oggetto Studente
     * @post il risultato è lo Studente associato al prestito
     */
    public Studente getStudente() {
        return studente;
    }
/**
     * @brief Restituisce l'oggetto Libro associato.
     * @return l'oggetto Libro
     * @post il risultato è il Libro associato al prestito
     */
    public Libro getLibro() {
        return libro;
    }
/**
     * @brief Restituisce la data di inizio del prestito.
     * @return la data di inizio
     * @post il risultato è la data in cui il prestito è stato registrato
     */
    public LocalDate getDataInizio() {
        return dataInizio;
    }
/**
     * @brief Restituisce la data prevista di restituzione.
     * @return la data prevista di restituzione
     * @post il risultato è la data entro cui il libro doveva essere restituito
     */
    public LocalDate getDataPrevistaRestituzione() {
        return dataPrevistaRestituzione;
    }
 /**
     * @brief Restituisce la data di restituzione effettiva.
     * @return la data di restituzione effettiva, o null se il prestito è ancora attivo
     * @post il risultato è la data in cui il libro è stato restituito
     */
    public LocalDate getDataRestituzioneEffettiva() {
        return dataRestituzioneEffettiva;
    }
    
    /**
     * @brief Verifica se il prestito è ancora attivo.
     * Un prestito è attivo se la data di restituzione effettiva è null.
     * @return true se il prestito è attivo, false altrimenti
     * @post il risultato è true se dataRestituzioneEffettiva == null, false altrimenti
     */
    public boolean isAttivo() {
        return dataRestituzioneEffettiva == null;
    }
/**
     * @brief Verifica se il prestito è in ritardo.
     * (Non ancora restituito e la data di restituzione prevista è passata).
     * @return true se il prestito è in ritardo, false altrimenti
     * @post il risultato è true se isAttivo() == true AND dataPrevistaRestituzione è nel passato rispetto alla data corrente
     */
    public boolean isInRitardo() {
        return isAttivo() && LocalDate.now().isAfter(dataPrevistaRestituzione);
    }

/**
     * @brief Registra l'avvenuta restituzione del libro.
     * Imposta la data di restituzione effettiva.
     * @param [in] dataRestituzione la data in cui il libro è stato restituito
     * @pre dataRestituzione != null
     * @post dataRestituzioneEffettiva = dataRestituzione
     * @post isAttivo() == false
     */
    public void registraRestituzione(LocalDate dataRestituzione) {
        if (dataRestituzione == null) throw new IllegalArgumentException("La data di restituzione non può essere nulla.");

        this.dataRestituzioneEffettiva = dataRestituzione;
    }
    
        @Override

    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prestito prestito = (Prestito) o;
        return Objects.equals(studente.getMatricola(), prestito.studente.getMatricola()) &&

               Objects.equals(libro.getCodiceIsbn(), prestito.libro.getCodiceIsbn()) &&

               Objects.equals(dataInizio, prestito.dataInizio);

    }



    @Override

    public int hashCode() {

        return Objects.hash(studente.getMatricola(), libro.getCodiceIsbn(), dataInizio);

    }
    
}

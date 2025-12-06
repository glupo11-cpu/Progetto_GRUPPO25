/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.is.gruppo25;

import java.time.LocalDate;


public class Prestito {
    
    private final Studente studente;
    private final Libro libro;
    private final LocalDate dataInizio;
    private final LocalDate dataPrevistaRestituzione;
    private LocalDate dataRestituzioneEffettiva;

    public Prestito(Studente studente, Libro libro, LocalDate dataPrevistaRestituzione) {
        this.studente = studente;
        this.libro = libro;
        this.dataInizio = LocalDate.now();
        this.dataPrevistaRestituzione = dataPrevistaRestituzione;
    }

    public Studente getStudente() {
        return studente;
    }

    public Libro getLibro() {
        return libro;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public LocalDate getDataPrevistaRestituzione() {
        return dataPrevistaRestituzione;
    }

    public LocalDate getDataRestituzioneEffettiva() {
        return dataRestituzioneEffettiva;
    }
    
    
    public boolean isAttivo() {
        return dataRestituzioneEffettiva == null;
    }

    public boolean isInRitardo() {
        return false;
    }

    public boolean haMulta() {
        return false;
    }

    public void registraRestituzione(LocalDate dataRestituzione) {
    }
    
}

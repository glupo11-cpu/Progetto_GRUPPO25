/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.is.gruppo25;

import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class PrestitoTest {
    private Studente studente;
    private Libro libro;
    private Prestito prestito;
    private LocalDate dataPrevista;

    @BeforeEach
    void setUp() {
        studente = new Studente("Mario", "Rossi", "12345", "m.rossi@unisa.it");
        libro = new Libro("Titolo Test","Autore Test", 2020,"123-456",3);
        dataPrevista = LocalDate.now().plusDays(7);
        prestito = new Prestito(studente, libro, dataPrevista);
    }

    @Test
    void testCostruttoreInizializzaCampi() {
        assertEquals(studente, prestito.getStudente());
        assertEquals(libro, prestito.getLibro());
        assertEquals(LocalDate.now(), prestito.getDataInizio());
        assertEquals(dataPrevista, prestito.getDataPrevistaRestituzione());
        assertNull(prestito.getDataRestituzioneEffettiva());
        assertTrue(prestito.isAttivo());
    }

    @Test
    void testRegistraRestituzioneAggiornaCampi() {
        LocalDate oggi = LocalDate.now();
        prestito.registraRestituzione(oggi);

        assertEquals(oggi, prestito.getDataRestituzioneEffettiva());
        assertFalse(prestito.isAttivo());
    }

    @Test
    void testIsInRitardo() {
        Prestito ritardoPrestito = new Prestito(studente, libro, LocalDate.now().minusDays(1));
        assertTrue(ritardoPrestito.isInRitardo());

        Prestito puntualePrestito = new Prestito(studente, libro, LocalDate.now().plusDays(1));
        assertFalse(puntualePrestito.isInRitardo());
    }

    @Test
    void testEqualsAndHashCode() {
        Prestito altro = new Prestito(studente, libro, dataPrevista);
        assertEquals(prestito, altro);
        assertEquals(prestito.hashCode(), altro.hashCode());
    }

    @Test
    void testRegistraRestituzioneNull() {
        assertThrows(IllegalArgumentException.class, () -> prestito.registraRestituzione(null));
    }
   
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.is.gruppo25;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class StudenteTest {
    
    private Studente studente;
    private Libro libro1;
    private Libro libro2;
    private Prestito prestito1;
    private Prestito prestito2;

    @BeforeEach
    void setUp() {
        studente = new Studente("Mario", "Rossi", "12345", "mario.rossi@unisa.it");
        libro1 = new Libro("Titolo1", "Autore1", 2020, "ISBN1", 2);
        libro2 = new Libro("Titolo2", "Autore2", 2021, "ISBN2", 3);
        prestito1 = new Prestito(studente, libro1, LocalDate.now().plusDays(7));
        prestito2 = new Prestito(studente, libro2, LocalDate.now().plusDays(7));
    }

    @Test
    void testGettersAndSetters() {
        assertEquals("Mario", studente.getNome());
        studente.setNome("Luigi");
        assertEquals("Luigi", studente.getNome());

        assertEquals("Rossi", studente.getCognome());
        studente.setCognome("Bianchi");
        assertEquals("Bianchi", studente.getCognome());

        assertEquals("12345", studente.getMatricola());

        assertEquals("mario.rossi@unisa.it", studente.getEmailIstituzionale());
        studente.setEmailIstituzionale("luigi.bianchi@unisa.it");
        assertEquals("luigi.bianchi@unisa.it", studente.getEmailIstituzionale());
    }

    @Test
    void testAggiungiPrestito() {
        studente.aggiungiPrestito(prestito1);
        assertTrue(studente.getPrestitiAttivi().contains(prestito1));

        studente.aggiungiPrestito(prestito2);
        assertEquals(2, studente.getPrestitiAttivi().size());

        // Aggiungo terzo prestito
        Libro libro3 = new Libro("Titolo3", "Autore3", 2022, "ISBN3", 1);
        Prestito prestito3 = new Prestito(studente, libro3, LocalDate.now().plusDays(5));
        studente.aggiungiPrestito(prestito3);

        // Supero il limite
        Libro libro4 = new Libro("Titolo4", "Autore4", 2023, "ISBN4", 1);
        Prestito prestito4 = new Prestito(studente, libro4, LocalDate.now().plusDays(5));
        assertThrows(IllegalStateException.class, () -> studente.aggiungiPrestito(prestito4));
    }

    @Test
    void testChiudiPrestito() {
        studente.aggiungiPrestito(prestito1);
        studente.aggiungiPrestito(prestito2);

        studente.chiudiPrestito(prestito1);
        assertFalse(studente.getPrestitiAttivi().contains(prestito1));
        assertEquals(1, studente.getPrestitiAttivi().size());

        // Rimuovo prestito non presente
        Prestito nonPresente = new Prestito(studente, new Libro("x","y",2020,"ISBNX",1), LocalDate.now());
        studente.chiudiPrestito(nonPresente); // Non deve fare crash
    }

    @Test
    void testEqualsAndHashCode() {
        Studente copy = new Studente("Mario", "Rossi", "12345", "mario2@unisa.it");
        assertEquals(studente, copy);
        assertEquals(studente.hashCode(), copy.hashCode());

        assertNotEquals(studente, null);
        assertNotEquals(studente, new Object());
    }
    
}

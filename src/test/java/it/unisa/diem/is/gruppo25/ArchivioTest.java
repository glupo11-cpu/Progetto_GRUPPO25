/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.is.gruppo25;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author gianlucarispoli
 */
public class ArchivioTest {
    
    private static final String TEST_LIBRI = "target/test-archivio-libri.bin";
    private static final String TEST_STUDENTI = "target/test-archivio-studenti.bin";
    private static final String TEST_PRESTITI = "target/test-archivio-prestiti.bin";

    @BeforeAll
    static void setupFiles() {
        // reindirizzo Archivio su file di test
        Archivio.fileLibri = TEST_LIBRI;
        Archivio.fileStudenti = TEST_STUDENTI;
        Archivio.filePrestiti = TEST_PRESTITI;
    }

    @BeforeEach
    void cleanBefore() {
        new File(TEST_LIBRI).delete();
        new File(TEST_STUDENTI).delete();
        new File(TEST_PRESTITI).delete();
    }

    @AfterEach
    void cleanAfter() {
        new File(TEST_LIBRI).delete();
        new File(TEST_STUDENTI).delete();
        new File(TEST_PRESTITI).delete();
    }

    @Test
    void testScriviECaricaLibri() {
        ArrayList<Libro> libri = new ArrayList<>();
        libri.add(new Libro("Titolo", "Autore", 2020, "ISBN1", 2));

        Archivio.scriviLibri(libri);
        ArrayList<Libro> caricati = Archivio.caricaLibri();

        assertEquals(1, caricati.size());
        assertEquals(libri.get(0), caricati.get(0));
    }

    @Test
    void testScriviECaricaStudenti() {
        ArrayList<Studente> studenti = new ArrayList<>();
        studenti.add(new Studente("Mario", "Rossi", "M001", "mario@uni.it"));

        Archivio.scriviStudenti(studenti);
        ArrayList<Studente> caricati = Archivio.caricaStudenti();

        assertEquals(1, caricati.size());
        assertEquals(studenti.get(0), caricati.get(0));
    }

    @Test
    void testScriviECaricaPrestiti() {
        Studente s = new Studente("Mario", "Rossi", "M001", "mario@uni.it");
        Libro l = new Libro("Titolo", "Autore", 2020, "ISBN1", 2);

        Prestito p = new Prestito(s, l, LocalDate.now().plusDays(7));

        ArrayList<Prestito> prestiti = new ArrayList<>();
        prestiti.add(p);

        Archivio.scriviPrestiti(prestiti);
        ArrayList<Prestito> caricati = Archivio.caricaPrestiti();

        assertEquals(1, caricati.size());
        assertEquals(p, caricati.get(0));
    }

    @Test
    void testCaricaFileInesistenteRestituisceListaVuota() {
        ArrayList<Libro> libri = Archivio.caricaLibri();
        assertNotNull(libri);
        assertTrue(libri.isEmpty());
    }
}

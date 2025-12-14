package it.unisa.diem.is.gruppo25;

import java.io.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;

class BibliotecaServiceTest {

    private BibliotecaService service;
    private Libro libro1, libro2;
    private Studente studente1, studente2;

    @BeforeEach
    void setUp() {
        service = new BibliotecaService();

        libro1 = new Libro("Titolo1", "Autore1", 2020, "ISBN1", 2);
        libro2 = new Libro("Titolo2", "Autore2", 2021, "ISBN2", 1);

        studente1 = new Studente("Mario", "Rossi", "M001", "mario.rossi@uni.it");
        studente2 = new Studente("Luigi", "Verdi", "M002", "luigi.verdi@uni.it");
    }


    @BeforeAll
    static void initTestFiles() {
        Archivio.fileLibri = "target/test-libri.bin";
        Archivio.fileStudenti = "target/test-studenti.bin";
        Archivio.filePrestiti = "target/test-prestiti.bin";
    }
    @AfterEach
    void cleanup() {
        new File(Archivio.fileLibri).delete();
        new File(Archivio.fileStudenti).delete();
        new File(Archivio.filePrestiti).delete();
}

    @Test
    void testInserisciLibro() {
        service.inserisciLibro(libro1);
        List<Libro> libri = service.elencoLibriOrdinatiPerTitolo();
        assertEquals(1, libri.size());
        assertEquals(libro1, libri.get(0));
    }

    @Test
    void testInserisciLibroDuplicato() {
        service.inserisciLibro(libro1);
        assertThrows(IllegalArgumentException.class, () -> service.inserisciLibro(libro1));
    }

    @Test
    void testModificaLibro() {
        service.inserisciLibro(libro1);
        service.modificaLibro("ISBN1", "NuovoTitolo", null, 2022, 5);
        Libro l = service.elencoLibriOrdinatiPerTitolo().get(0);
        assertEquals("NuovoTitolo", l.getTitolo());
        assertEquals(2022, l.getAnnoPubblicazione());
        assertEquals(5, l.getCopieDisponibili());
    }

    @Test
    void testEliminaLibro() {
        service.inserisciLibro(libro1);
        service.eliminaLibro("ISBN1");
        assertTrue(service.elencoLibriOrdinatiPerTitolo().isEmpty());
    }

    @Test
    void testEliminaLibroConPrestitoAttivo() {
        service.inserisciLibro(libro1);
        service.inserisciStudente(studente1);
        service.registraPrestito("M001", "ISBN1", LocalDate.now().plusDays(7));
        assertThrows(IllegalStateException.class, () -> service.eliminaLibro("ISBN1"));
    }

    @Test
    void testCercaLibri() {
        service.inserisciLibro(libro1);
        service.inserisciLibro(libro2);
        List<Libro> risultati = service.cercaLibri("Titolo1", null, null);
        assertEquals(1, risultati.size());
        assertEquals(libro1, risultati.get(0));
    }



    @Test
    void testInserisciStudente() {
        service.inserisciStudente(studente1);
        List<Studente> studenti = service.elencoStudentiOrdinati();
        assertEquals(1, studenti.size());
        assertEquals(studente1, studenti.get(0));
    }

    @Test
    void testInserisciStudenteDuplicato() {
        service.inserisciStudente(studente1);
        assertThrows(IllegalArgumentException.class, () -> service.inserisciStudente(studente1));
    }

    @Test
    void testModificaStudente() {
        service.inserisciStudente(studente1);
        service.modificaStudente("M001", "Giuseppe", "Bianchi", "giuseppe.bianchi@uni.it");
        Studente s = service.elencoStudentiOrdinati().get(0);
        assertEquals("Giuseppe", s.getNome());
        assertEquals("Bianchi", s.getCognome());
        assertEquals("giuseppe.bianchi@uni.it", s.getEmailIstituzionale());
    }

    @Test
    void testEliminaStudente() {
        service.inserisciStudente(studente1);
        service.eliminaStudente("M001");
        assertTrue(service.elencoStudentiOrdinati().isEmpty());
    }

    @Test
    void testEliminaStudenteConPrestitoAttivo() {
        service.inserisciStudente(studente1);
        service.inserisciLibro(libro1);
        service.registraPrestito("M001", "ISBN1", LocalDate.now().plusDays(7));
        assertThrows(IllegalStateException.class, () -> service.eliminaStudente("M001"));
    }



    @Test
    void testRegistraPrestito() {
        service.inserisciStudente(studente1);
        service.inserisciLibro(libro1);
        Prestito p = service.registraPrestito("M001", "ISBN1", LocalDate.now().plusDays(7));
        assertTrue(p.isAttivo());
        assertEquals(1, studente1.getPrestitiAttivi().size());
        assertEquals(1, libro1.getCopieDisponibili());
    }

    @Test
    void testRegistraPrestitoLimiteCopie() {
        service.inserisciStudente(studente1);
        service.inserisciLibro(libro2); // 1 copia
        service.registraPrestito("M001", "ISBN2", LocalDate.now().plusDays(7));
        assertThrows(IllegalStateException.class,
                () -> service.registraPrestito("M001", "ISBN2", LocalDate.now().plusDays(7)));
    }

    @Test
    void testRegistraPrestitoLimite3Prestiti() {
        service.inserisciStudente(studente1);
        service.inserisciLibro(libro1);
        service.inserisciLibro(libro2);
        Libro libro3 = new Libro("Titolo3", "Autore3", 2022, "ISBN3", 1);
        service.inserisciLibro(libro3);
        Libro libro4 = new Libro("Titolo4", "Autore4", 2022, "ISBN4", 1);
        service.inserisciLibro(libro4);

        service.registraPrestito("M001", "ISBN1", LocalDate.now().plusDays(7));
        service.registraPrestito("M001", "ISBN2", LocalDate.now().plusDays(7));
        service.registraPrestito("M001", "ISBN3", LocalDate.now().plusDays(7));
        assertThrows(IllegalStateException.class,
                () -> service.registraPrestito("M001", "ISBN4", LocalDate.now().plusDays(7)));
    }

    @Test
    void testRegistraRestituzione() {
        service.inserisciStudente(studente1);
        service.inserisciLibro(libro1);
        Prestito p = service.registraPrestito("M001", "ISBN1", LocalDate.now().plusDays(7));
        service.registraRestituzione(p, LocalDate.now());
        assertFalse(p.isAttivo());
        assertEquals(2, libro1.getCopieDisponibili());
        assertTrue(studente1.getPrestitiAttivi().isEmpty());
    }

    @Test
    void testElencoPrestitiAttiviOrdinati() {
        service.inserisciStudente(studente1);
        service.inserisciStudente(studente2);
        service.inserisciLibro(libro1);
        service.inserisciLibro(libro2);

        Prestito p1 = service.registraPrestito("M001", "ISBN1", LocalDate.now().plusDays(5));
        Prestito p2 = service.registraPrestito("M002", "ISBN2", LocalDate.now().plusDays(3));

        List<Prestito> ordinati = service.elencoPrestitiAttiviOrdinatiPerDataPrevista();
        assertEquals(2, ordinati.size());
        assertEquals(p2, ordinati.get(0)); 
        assertEquals(p1, ordinati.get(1));
    }

    @Test
    void testCercaPrestiti() {
        service.inserisciStudente(studente1);
        service.inserisciLibro(libro1);
        Prestito p = service.registraPrestito("M001", "ISBN1", LocalDate.now().plusDays(7));
        List<Prestito> risultati = service.cercaPrestiti("M001", null);
        assertEquals(1, risultati.size());
        assertEquals(p, risultati.get(0));
    }
}

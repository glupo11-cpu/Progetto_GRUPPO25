package it.unisa.diem.is.gruppo25;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class LibroTest {

    @Test
    public void testCostruttoreValido() {
        Libro libro = new Libro(
                "Il Signore degli Anelli",
                "J.R.R. Tolkien",
                1954,
                "9780261102385",
                3
        );

        assertEquals("Il Signore degli Anelli", libro.getTitolo());
        assertEquals("J.R.R. Tolkien", libro.getAutore());
        assertEquals(1954, libro.getAnnoPubblicazione());
        assertEquals("9780261102385", libro.getCodiceIsbn());
        assertEquals(3, libro.getCopieDisponibili());
    }

    @Test
    public void testCostruttoreTitoloNullo() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Libro(null, "Autore", 2000, "ISBN123", 1);
        });
    }

    @Test
    public void testSetTitolo() {
        Libro libro = creaLibroBase();
        libro.setTitolo("Nuovo Titolo");
        assertEquals("Nuovo Titolo", libro.getTitolo());
    }

    @Test
    public void testSetAutore() {
        Libro libro = creaLibroBase();
        libro.setAutore("Nuovo Autore");
        assertEquals("Nuovo Autore", libro.getAutore());
    }

    @Test
    public void testSetAnnoPubblicazione() {
        Libro libro = creaLibroBase();
        libro.setAnnoPubblicazione(1999);
        assertEquals(1999, libro.getAnnoPubblicazione());
    }

    @Test
    public void testIncrementaCopie() {
        Libro libro = creaLibroBase();
        libro.incrementaCopie();
        assertEquals(3, libro.getCopieDisponibili());
    }

    @Test
    public void testDecrementaCopie() {
        Libro libro = creaLibroBase();
        libro.decrementaCopie();
        assertEquals(1, libro.getCopieDisponibili());
    }

    @Test
    public void testDecrementaCopieZero() {
        Libro libro = new Libro("Titolo", "Autore", 2000, "ISBN123", 0);

        assertThrows(IllegalStateException.class, () -> {
            libro.decrementaCopie();
        });
    }

    @Test
    public void testEquals() {
        Libro libro1 = new Libro("Titolo1", "Autore1", 2000, "ISBN123", 1);
        Libro libro2 = new Libro("Titolo2", "Autore2", 2010, "ISBN123", 5);

        assertEquals(libro1, libro2);
    }

    @Test
    public void testHashCode() {
        Libro libro1 = new Libro("Titolo1", "Autore1", 2000, "ISBN123", 1);
        Libro libro2 = new Libro("Titolo2", "Autore2", 2010, "ISBN123", 5);

        assertEquals(libro1.hashCode(), libro2.hashCode());
    }

    private Libro creaLibroBase() {
        return new Libro("Titolo", "Autore", 2000, "ISBN001", 2);
    }
}

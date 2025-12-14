/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.is.gruppo25;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
/**
 * @brief Gestisce la logica di business della biblioteca.
 * La classe BibliotecaService è la classe di servizio che gestisce la logica di business
 * per la gestione di libri, studenti e prestiti.
 */
public class BibliotecaService {

    private Map<String, Libro> libriPerIsbn = new HashMap<>();
    private Map<String, Studente> studentiPerMatricola = new HashMap<>();
    private List<Prestito> prestiti = new ArrayList<>();

 /**
     * @brief Salva tutti i dati correnti su file.
     * @pre le strutture dati interne sono aggiornate
     * @post i dati vengono serializzati e salvati sui rispettivi file tramite Archivio
     */
    void salvaDati() {
        ArrayList<Libro> listaLibri = new ArrayList<>(libriPerIsbn.values());
        ArrayList<Studente> listaStudenti = new ArrayList<>(studentiPerMatricola.values());
        ArrayList<Prestito> listaPrestiti = new ArrayList<>(prestiti);

        Archivio.scriviLibri(listaLibri);
        Archivio.scriviStudenti(listaStudenti);
        Archivio.scriviPrestiti(listaPrestiti);
        System.out.println("Salvataggio completato.");    }
 /**
     * @brief Carica tutti i dati dai file di archivio.
     * @pre i file di archivio possono esistere o essere mancanti
     * @post le strutture dati interne (libriPerIsbn, studentiPerMatricola, prestiti) vengono popolate
     */
    void caricaDati() {
        ArrayList<Libro> listaLibri = Archivio.caricaLibri();
        ArrayList<Studente> listaStudenti = Archivio.caricaStudenti();
        ArrayList<Prestito> listaPrestiti = Archivio.caricaPrestiti();

        this.libriPerIsbn.clear();
        for (Libro l : listaLibri) {
            this.libriPerIsbn.put(l.getCodiceIsbn(), l);
        }

        this.studentiPerMatricola.clear();
        for (Studente s : listaStudenti) {
            s.getPrestitiAttivi().clear();
            this.studentiPerMatricola.put(s.getMatricola(), s);
        }

        this.prestiti.clear();
        for (Prestito p : listaPrestiti) {
            Studente studenteReale = studentiPerMatricola.get(p.getStudente().getMatricola());
            
            if (studenteReale != null) {
                this.prestiti.add(p);
                if (p.isAttivo()) {
                    studenteReale.aggiungiPrestito(p);
                }
            }
        }
        System.out.println("Caricamento completato.");    
    }
    
     /**
     * @brief Inserisce un nuovo libro o aggiorna le copie di uno esistente.
     * @param [in] libro l'oggetto Libro da inserire
     * @pre libro != null
     * @post se il libro non esiste, viene aggiunto; altrimenti le copie vengono aggiornate
     */
    public void inserisciLibro(Libro libro) {
        if (libriPerIsbn.containsKey(libro.getCodiceIsbn())) throw new IllegalArgumentException("ISBN esistente");
        libriPerIsbn.put(libro.getCodiceIsbn(), libro);
    }
/**
     * @brief Modifica i dati di un libro esistente.
     * @param [in] codiceIsbn l'ISBN del libro da modificare
     * @param [in] nuovoTitolo il nuovo titolo
     * @param [in] nuovoAutore il nuovo autore
     * @param [in] nuovoAnno il nuovo anno di pubblicazione
     * @param [in] nuoveCopie il nuovo numero di copie disponibili
     * @pre codiceIsbn != null AND il libro con codiceIsbn esiste
     * @post i campi del libro (titolo, autore, anno, copie) vengono aggiornati
     */
    public void modificaLibro(String codiceIsbn, String nuovoTitolo, String nuovoAutore, Integer nuovoAnno, Integer nuoveCopie) {
        Libro l = getLibroByIsbn(codiceIsbn);
        if(nuovoTitolo!=null) l.setTitolo(nuovoTitolo);
        if(nuovoAutore!=null) l.setAutore(nuovoAutore);
        if(nuovoAnno!=null) l.setAnnoPubblicazione(nuovoAnno);
        if(nuoveCopie!=null) l.setCopieDisponibili(nuoveCopie);   
    }
/**
     * @brief Elimina un libro dalla collezione.
     * L'eliminazione è possibile solo se non ci sono prestiti attivi.
     * @param [in] codiceIsbn l'ISBN del libro da eliminare
     * @pre codiceIsbn != null AND il libro con codiceIsbn esiste
     * @pre non ci sono prestiti attivi per questo libro
     * @post il libro viene rimosso da libriPerIsbn
     */
    public void eliminaLibro(String codiceIsbn) {
        if (!libriPerIsbn.containsKey(codiceIsbn))
            throw new NoSuchElementException("Libro non trovato con ISBN: " + codiceIsbn);
        boolean haPrestitiAttivi = prestiti.stream()
                .anyMatch(p -> p.isAttivo() && p.getLibro().getCodiceIsbn().equals(codiceIsbn));

        if (haPrestitiAttivi) {
            throw new IllegalStateException("Impossibile eliminare: il libro ha prestiti attivi.");
        }
        libriPerIsbn.remove(codiceIsbn);
    }
/**
     * @brief Cerca libri in base a titolo, autore o codice ISBN.
     * (Ricerca per sottostringa).
     * @param [in] titolo il titolo o parte di esso da cercare (può essere null)
     * @param [in] autore l'autore o parte di esso da cercare (può essere null)
     * @param [in] codiceIsbn l'ISBN o parte di esso da cercare (può essere null)
     * @return una lista di oggetti Libro che soddisfano i criteri di ricerca
     * @post il risultato è un sottoinsieme di libriPerIsbn che corrisponde ai parametri forniti
     */
    public List<Libro> cercaLibri(String titolo, String autore, String codiceIsbn) {
        return libriPerIsbn.values().stream()
                .filter(l -> (titolo == null || l.getTitolo().toLowerCase().contains(titolo.toLowerCase())))
                .filter(l -> (autore == null || l.getAutore().toLowerCase().contains(autore.toLowerCase())))
                .filter(l -> (codiceIsbn == null || l.getCodiceIsbn().toLowerCase().contains(codiceIsbn.toLowerCase())))
                .collect(Collectors.toList()); 
    }
/**
     * @brief Restituisce l'elenco completo dei libri ordinati per titolo.
     * @return una lista di oggetti Libro ordinati alfabeticamente per titolo
     * @post il risultato è una lista di tutti i libri, ordinati
     */
    public List<Libro> elencoLibriOrdinatiPerTitolo() {
        return libriPerIsbn.values().stream()
                .sorted(Comparator.comparing(Libro::getTitolo))
                .collect(Collectors.toList());    }
/**
     * @brief Recupera un libro tramite il suo codice ISBN.
     * @param [in] codiceIsbn l'ISBN del libro
     * @return l'oggetto Libro se trovato, null altrimenti
     * @pre codiceIsbn != null
     * @post il risultato è il Libro corrispondente all'ISBN
     */
    private Libro getLibroByIsbn(String codiceIsbn) {
        if (codiceIsbn == null) throw new IllegalArgumentException("ISBN non può essere nullo");
        Libro libro = libriPerIsbn.get(codiceIsbn);
        if (libro == null)
            throw new IllegalArgumentException("Libro non trovato con ISBN: " + codiceIsbn);
        return libro;
    }


/**
     * @brief Inserisce un nuovo studente nel sistema.
     * @param [in] studente l'oggetto Studente da inserire
     * @pre studente != null AND la matricola dello studente non è già presente
     * @post lo studente viene aggiunto alla mappa studentiPerMatricola
     */
    public void inserisciStudente(Studente studente) {   
        if (studente == null || studente.getMatricola() == null)
            throw new IllegalArgumentException("Dati studente non validi.");
        if (studentiPerMatricola.containsKey(studente.getMatricola()))
            throw new IllegalArgumentException("Matricola già presente: " + studente.getMatricola());
        
        studentiPerMatricola.put(studente.getMatricola(), studente);
    }
/**
     * @brief Modifica i dati di uno studente esistente.
     * @param [in] matricola la matricola dello studente da modificare
     * @param [in] nuovoNome il nuovo nome
     * @param [in] nuovoCognome il nuovo cognome
     * @param [in] nuovaEmail la nuova email istituzionale
     * @pre matricola != null AND lo studente con matricola esiste
     * @post i campi dello studente (nome, cognome, email) vengono aggiornati
     */
    public void modificaStudente(String matricola, String nuovoNome, String nuovoCognome, String nuovaEmail) {
        Studente s = getStudenteByMatricola(matricola);
        if(nuovoNome!=null) s.setNome(nuovoNome);
        if(nuovoCognome!=null) s.setCognome(nuovoCognome);
        if(nuovaEmail!=null) s.setEmailIstituzionale(nuovaEmail);
    }
/**
     * @brief Elimina uno studente dal sistema.
     * L'eliminazione è possibile solo se lo studente non ha prestiti attivi.
     * @param [in] matricola la matricola dello studente da eliminare
     * @pre matricola != null AND lo studente con matricola esiste
     * @pre lo studente non ha prestiti attivi
     * @post lo studente viene rimosso da studentiPerMatricola
     */
    public void eliminaStudente(String matricola) {
        Studente s = getStudenteByMatricola(matricola); 
        if (!s.getPrestitiAttivi().isEmpty()) {
            throw new IllegalStateException("Impossibile eliminare: lo studente ha prestiti attivi.");
        } 
        studentiPerMatricola.remove(matricola);
    }
/**
     * @brief Cerca studenti in base a cognome o matricola.
     * (Ricerca per sottostringa).
     * @param [in] cognome il cognome o parte di esso da cercare (può essere null)
     * @param [in] matricola la matricola o parte di essa da cercare (può essere null)
     * @return una lista di oggetti Studente che soddisfano i criteri di ricerca
     * @post il risultato è un sottoinsieme di studentiPerMatricola che corrisponde ai parametri forniti
     */
    public List<Studente> cercaStudente(String cognome, String matricola) {
        return studentiPerMatricola.values().stream()
                .filter(s -> (cognome == null || s.getCognome().toLowerCase().contains(cognome.toLowerCase())))
                .filter(s -> (matricola == null || s.getMatricola().toLowerCase().contains(matricola.toLowerCase())))
                .collect(Collectors.toList());
    }
/**
     * @brief Restituisce l'elenco completo degli studenti ordinati.
     * L'ordinamento avviene alfabeticamente per cognome.
     * @return una lista di oggetti Studente ordinati per cognome
     * @post il risultato è una lista di tutti gli studenti, ordinati
     */
    public List<Studente> elencoStudentiOrdinati() {
        return studentiPerMatricola.values().stream()
                .sorted(Comparator.comparing(Studente::getCognome))
                .collect(Collectors.toList());
    }
/**
     * @brief Recupera uno studente tramite la sua matricola.
     * @param [in] matricola la matricola dello studente
     * @return l'oggetto Studente se trovato, null altrimenti
     * @pre matricola != null
     * @post il risultato è lo Studente corrispondente alla matricola
     */
    private Studente getStudenteByMatricola(String matricola) {
        if (matricola == null) throw new IllegalArgumentException("Matricola non può essere nulla");
        Studente studente = studentiPerMatricola.get(matricola);
        if (studente == null)
            throw new IllegalArgumentException("Studente non trovato con matricola: " + matricola);
        return studente; 
    }

  /**
     * @brief Registra un nuovo prestito.
     * Decrementa le copie disponibili del libro e aggiorna lo studente.
     * @param [in] matricolaStudente la matricola dello studente che effettua il prestito
     * @param [in] codiceIsbn l'ISBN del libro prestato
     * @param [in] dataPrevistaRestituzione la data entro cui il libro deve essere restituito
     * @return l'oggetto Prestito appena creato
     * @pre lo studente e il libro esistono
     * @pre il libro ha almeno una copia disponibile
     * @post viene creato e aggiunto un nuovo Prestito alla lista 'prestiti'
     * @post il Prestito è aggiunto alla lista prestitiAttivi dello Studente
     * @post le copie disponibili del Libro sono decrementate
     */

    public Prestito registraPrestito(String matricolaStudente, String codiceIsbn, LocalDate dataPrevistaRestituzione) {
        Studente studente = getStudenteByMatricola(matricolaStudente);
        Libro libro = getLibroByIsbn(codiceIsbn);

        if (libro.getCopieDisponibili() <= 0)
            throw new IllegalStateException("Nessuna copia disponibile del libro.");
        if (studente.getPrestitiAttivi().size() >= 3)
            throw new IllegalStateException("Limite massimo di 3 prestiti attivi per lo studente raggiunto.");

        libro.decrementaCopie();
        Prestito p = new Prestito(studente, libro, dataPrevistaRestituzione);
        prestiti.add(p);
        studente.aggiungiPrestito(p);
        return p;
    }
/**
     * @brief Registra la restituzione di un libro.
     * Chiude il Prestito e incrementa le copie disponibili del libro.
     * @param [in] prestito l'oggetto Prestito da chiudere
     * @param [in] dataRestituzione la data effettiva di restituzione
     * @pre prestito != null AND il prestito è attivo
     * @pre dataRestituzione != null
     * @post la dataRestituzioneEffettiva del Prestito è impostata
     * @post il Prestito viene rimosso dalla lista prestitiAttivi dello Studente (gestito da Prestito.registraRestituzione())
     * @post le copie disponibili del Libro sono incrementate
     */
    public void registraRestituzione(Prestito prestito, LocalDate dataRestituzione) {
        if (prestito == null) throw new IllegalArgumentException("Prestito non può essere nullo.");
        if (!prestito.isAttivo()) throw new IllegalStateException("Prestito già chiuso.");
        if (dataRestituzione == null) throw new IllegalArgumentException("Data di restituzione non valida.");
        
        prestito.registraRestituzione(dataRestituzione);
        prestito.getLibro().incrementaCopie();
        prestito.getStudente().chiudiPrestito(prestito);
    }
/**
     * @brief Restituisce l'elenco di tutti i prestiti attivi.
     * L'ordinamento avviene per data prevista di restituzione.
     * @return una lista di oggetti Prestito attivi, ordinati per dataPrevistaRestituzione (ascendente)
     * @post il risultato contiene solo i prestiti per cui Prestito.isAttivo() == true
     */
    public List<Prestito> elencoPrestitiAttiviOrdinatiPerDataPrevista() {
        return prestiti.stream()
                .filter(Prestito::isAttivo)
                .sorted(Comparator.comparing(Prestito::getDataPrevistaRestituzione))
                .collect(Collectors.toList());
    }
/**
     * @brief Cerca prestiti in base a matricola dello studente o ISBN del libro.
     * @param [in] matricolaStudente la matricola o parte di essa (può essere null)
     * @param [in] codiceIsbn l'ISBN o parte di esso (può essere null)
     * @return una lista di oggetti Prestito che soddisfano i criteri di ricerca
     * @post il risultato è un sottoinsieme di 'prestiti' che corrisponde ai parametri forniti
     */
    public List<Prestito> cercaPrestiti(String matricolaStudente, String codiceIsbn) {
        return prestiti.stream()
                .filter(Prestito::isAttivo)
                .filter(p -> (matricolaStudente == null || p.getStudente().getMatricola().toLowerCase().contains(matricolaStudente.toLowerCase())))
                .filter(p -> (codiceIsbn == null || p.getLibro().getCodiceIsbn().toLowerCase().contains(codiceIsbn.toLowerCase())))
                .collect(Collectors.toList());
    }
}

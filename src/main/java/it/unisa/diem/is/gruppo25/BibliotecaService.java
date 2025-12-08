/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.is.gruppo25;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class BibliotecaService {

    private Map<String, Libro> libriPerIsbn = new HashMap<>();
    private Map<String, Studente> studentiPerMatricola = new HashMap<>();
    private List<Prestito> prestiti = new ArrayList<>();


    void salvaDati() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void caricaDati() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public void inserisciLibro(Libro libro) {
        
    }

    public void modificaLibro(String codiceIsbn, String nuovoTitolo, String nuovoAutore, Integer nuovoAnno, Integer nuoveCopie) {
        
    }

    public void eliminaLibro(String codiceIsbn) {
        
    }

    public List<Libro> cercaLibri(String titolo, String autore, String codiceIsbn) {
        return null; 
    }

    public List<Libro> elencoLibriOrdinatiPerTitolo() {
        return null; 
    }

    private Libro getLibroByIsbn(String codiceIsbn) {
        return null; 
    }



    public void inserisciStudente(Studente studente) {   
    }

    public void modificaStudente(String matricola, String nuovoNome, String nuovoCognome, String nuovaEmail) {
        
    }

    public void eliminaStudente(String matricola) {
        
    }

    public List<Studente> cercaStudente(String cognome, String matricola) {
        return null; 
    }

    public List<Studente> elencoStudentiOrdinati() {
        return null; 
    }

    private Studente getStudenteByMatricola(String matricola) {
        return null; 
    }



    public Prestito registraPrestito(String matricolaStudente, String codiceIsbn, LocalDate dataPrevistaRestituzione) {
        return null; 
    }

    public void registraRestituzione(Prestito prestito, LocalDate dataRestituzione) {
        
    }

    public List<Prestito> elencoPrestitiAttiviOrdinatiPerDataPrevista() {
        return null; 
    }

    public List<Prestito> cercaPrestiti(String matricolaStudente, String codiceIsbn) {
        return null; 
    }
}

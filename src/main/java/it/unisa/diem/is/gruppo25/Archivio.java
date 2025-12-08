/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unisa.diem.is.gruppo25;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * @brief Gestisce la persistenza dei dati della biblioteca.
 * La classe Archivio gestisce la persistenza dei dati (Libri, Studenti, Prestiti)
 * attraverso la serializzazione su file binari.
 */
public class Archivio {
    public static String fileLibri = "archivioLibri.bin";
    public static String fileStudenti = "archivioStudenti.bin";
    public static String filePrestiti = "archivioPrestiti.bin";
 /**
     * @brief Scrive la lista di libri sul file dedicato.
     * @param [in] l la lista di oggetti Libro da salvare
     * @pre l != null
     * @post i dati della lista sono salvati sul file fileLibri
     */
    public static void scriviLibri(ArrayList<Libro> l) { scrivi(fileLibri, l); }
    /**
     * @brief Carica la lista di libri dal file dedicato.
     * @return una lista di oggetti Libro
     * @post il risultato è una lista contenente i libri precedentemente salvati
     * @post se il file non esiste o è vuoto, viene restituita una lista vuota
     */
    public static ArrayList<Libro> caricaLibri() { return carica(fileLibri); }
/**
     * @brief Scrive la lista di studenti sul file dedicato.
     * @param [in] s la lista di oggetti Studente da salvare
     * @pre s != null
     * @post i dati della lista sono salvati sul file fileStudenti
     */
    public static void scriviStudenti(ArrayList<Studente> s) { scrivi(fileStudenti, s); }
    /**
     * @brief Carica la lista di studenti dal file dedicato.
     * @return una lista di oggetti Studente
     * @post il risultato è una lista contenente gli studenti precedentemente salvati
     * @post se il file non esiste o è vuoto, viene restituita una lista vuota
     */
    public static ArrayList<Studente> caricaStudenti() { return carica(fileStudenti); }
/**
     * @brief Scrive la lista di prestiti sul file dedicato.
     * @param [in] p la lista di oggetti Prestito da salvare
     * @pre p != null
     * @post i dati della lista sono salvati sul file filePrestiti
     */
    public static void scriviPrestiti(ArrayList<Prestito> p) { scrivi(filePrestiti, p); }
     /**
     * @brief Carica la lista di prestiti dal file dedicato.
     * @return una lista di oggetti Prestito
     * @post il risultato è una lista contenente i prestiti precedentemente salvati
     * @post se il file non esiste o è vuoto, viene restituita una lista vuota
     */
    public static ArrayList<Prestito> caricaPrestiti() { return carica(filePrestiti); }
/**
     * @brief Serializza e scrive un oggetto su un file.
     * @param [in] file il nome del file su cui scrivere
     * @param [in] obj l'oggetto serializzabile da scrivere
     * @pre file != null AND obj != null
     * @post l'oggetto è stato scritto sul file specificato
     */
    private static void scrivi(String file, Object obj) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(obj);
        } catch (IOException ex) { Logger.getLogger(Archivio.class.getName()).log(Level.SEVERE, null, ex); }
    }
/**
     * @brief Deserializza e carica un oggetto da un file.
     * @param <T> il tipo di elementi contenuti nella lista (Libro, Studente, Prestito)
     * @param [in] file il nome del file da cui leggere
     * @return una lista di oggetti di tipo T
     * @pre file != null
     * @post il risultato è la lista di oggetti letta dal file, se presente, altrimenti una lista vuota
     */
    private static <T> ArrayList<T> carica(String file) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<T>) ois.readObject();
        } catch (Exception ex) { return new ArrayList<>(); }
    }
}
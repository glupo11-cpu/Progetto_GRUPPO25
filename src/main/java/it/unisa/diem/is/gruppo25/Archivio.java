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

public class Archivio {
    public static String fileLibri = "archivioLibri.bin";
    public static String fileStudenti = "archivioStudenti.bin";
    public static String filePrestiti = "archivioPrestiti.bin";

    public static void scriviLibri(ArrayList<Libro> l) { scrivi(fileLibri, l); }
    public static ArrayList<Libro> caricaLibri() { return carica(fileLibri); }

    public static void scriviStudenti(ArrayList<Studente> s) { scrivi(fileStudenti, s); }
    public static ArrayList<Studente> caricaStudenti() { return carica(fileStudenti); }

    public static void scriviPrestiti(ArrayList<Prestito> p) { scrivi(filePrestiti, p); }
    public static ArrayList<Prestito> caricaPrestiti() { return carica(filePrestiti); }

    private static void scrivi(String file, Object obj) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(obj);
        } catch (IOException ex) { Logger.getLogger(Archivio.class.getName()).log(Level.SEVERE, null, ex); }
    }

    private static <T> ArrayList<T> carica(String file) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (ArrayList<T>) ois.readObject();
        } catch (Exception ex) { return new ArrayList<>(); }
    }
}
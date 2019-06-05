package oope2018ht.tiedostot;

/**
 * OOPE 2018 harkkatyö
 * Abstrakti Tiedosto-luokka, käsittelee viestiketjun tiedostot
 * @author Heidi Happonen, 424364, Happonen.Heidi.K@student.uta.fi
 */

import oope2018ht.apulaiset.*;

public abstract class Tiedosto {

   /* Attribuutit */
   
   /** Tiedoston nimi */
   private String nimi;
   
   /** Tiedoston koko */
   private int koko;
   
   /* Rakentajat */
   
   /** Tiedoston rakentaja
    * Luodaan tiedosto-olio annetuista parametreista
    *
    * @param n Tiedoston nimi
    * @param k Tiedoston koko
    * @throws IllegalArgumentException jos parametreissa oli virhe
    */
    
   public Tiedosto(String n, int k) {
      try {
         nimi(n);
         koko(k);
      }
      catch (Exception e) {
         throw new IllegalArgumentException("Error!");
      }
   }
   
   /* Aksessorit */
   
   // Nimi
   
   @Setteri
   public void nimi(String n) throws IllegalArgumentException {
      if ( n.length() >= 1 ) {
         nimi = n;
      }
      else {
         throw new IllegalArgumentException("Error!"); // virhe kun nimi on liian lyhyt
      }
   }
   
   @Getteri
   public String nimi() {
      return nimi;
   }
   
   // Koko
   
   @Setteri
   public void koko(int k) throws IllegalArgumentException {
      if ( k >= 1 ) {
         koko = k;
      }
      else {
         throw new IllegalArgumentException("Error!"); // virhe kun koko on liian pieni
      }
   }
   
   @Getteri
   public int koko() {
      return koko;
   }
   
   /* toString metodin korvaus */
   
   @Override
   public String toString() {
      return nimi + " " + koko + " " + "B ";
   }
}
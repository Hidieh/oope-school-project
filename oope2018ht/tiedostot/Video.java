package oope2018ht.tiedostot;

/**
 * OOPE 2018 harkkatyö
 * Video-luokka, käsittelee viestiketjun videot. Periytyy Tiedosto-luokasta
 * @author Heidi Happonen, 424364, Happonen.Heidi.K@student.uta.fi
 */

import oope2018ht.apulaiset.*;

public class Video extends Tiedosto {

   /* Attribuutit */
   
   /** Videon pituus */
   private double pituus; // videon pituus
   
   /* Rakentajat */
   
   /** Videon rakentaja
    * Luodaan video-olio annetuista parametreista
    *
    * @param n Video nimi
    * @param s Videon koko
    * @param p Video pituus
    */
   public Video(String n, int s, double p) {
      super(n, s); // kutsutaan yliluokan rakentajaa
      pituus(p);
   }
   
   /* Aksessorit */
   
   @Setteri
   public void pituus(double p) throws IllegalArgumentException {
      if ( p > 0 ) {
         pituus = p;
      }
      else {
         throw new IllegalArgumentException("Error!"); // virhe kun videon pituus liian lyhyt
      }
   }
   
   @Getteri
   public double pituus() {
      return pituus;
   }
   
   /* toString metodin korvaus */
   
   @Override
   public String toString() {
      return super.toString() + pituus + " s";
   }
}
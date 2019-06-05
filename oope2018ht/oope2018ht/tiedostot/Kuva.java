package oope2018ht.tiedostot;

/**
 * OOPE 2018 harkkatyö
 * Kuva-luokka, käsittelee viestiketjun kuvat. Periytyy Tiedosto-luokasta
 * @author Heidi Happonen, 424364, Happonen.Heidi.K@student.uta.fi
 */

import oope2018ht.apulaiset.*;

public class Kuva extends Tiedosto {

   /* Attribuutit */
   
   /** Kuvan leveys */
   private int leveys;
   
   /** Kuvan korkeus */
   private int korkeus;
   
   /* Rakentajat */
   
   /** Kuvan rakentaja
    * Luodaan kuva-olio annetuista parametreista
    *
    * @param n Kuvan nimi
    * @param s Kuvan koko
    * @param l Kuvan leveys
    * @param k Kuvan korkeys
    */
   
   public Kuva(String n, int s, int l, int k){
      super(n, s); // kutsutaan yliluokan rakentajaa
      leveys(l);
      korkeus(k);
   }
   
   /* Aksessorit */
   
   // Leveys
   
   @Setteri
   public void leveys(int l) throws IllegalArgumentException {
      if ( l > 0 ){
         leveys = l;
      } 
      else {
         throw new IllegalArgumentException("Error!"); // virhe kun kuvalla ei ole leveyttä
      }
   }
   
   @Getteri
   public int leveys() {
      return leveys;
   }
   
   // Korkeus
   
   @Setteri
   public void korkeus(int k) throws IllegalArgumentException {
      if ( k > 0 ) {
         korkeus = k;
      } 
      else {
         throw new IllegalArgumentException ("Error!"); // virhe kun kuva ei ole korkeutta
      }
   }
   
   @Getteri
   public int korkeus() {
      return korkeus;
   }
   
   /* toString metodin korvaus */
   
   @Override
   public String toString() {
      return super.toString() + leveys + "x" + korkeus;
   }
}
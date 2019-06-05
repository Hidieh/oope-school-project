package oope2018ht.viestit;

/**
 * OOPE 2018 harkkatyö
 * Viesti-luokka.
 * @author Heidi Happonen, 424364, Happonen.Heidi.K@student.uta.fi
 */

import oope2018ht.apulaiset.*;
import oope2018ht.tiedostot.*;
import oope2018ht.omalista.*;

public class Viesti implements Komennettava<Viesti>, Comparable<Viesti> {
   
   /* Attribuutit */
   
   /** Viestin tunniste */
   private int tunniste; 
   
   /** Viestin sisältö */
   private String sisalto;
   
   /** Viestin liitetiedosto */
   private Tiedosto liite;
   
   /** Viestiä edeltänyt viesti */
   private Viesti aikaisempi;
   
   /** Viestiin liittyvät vastaukset listana */
   private OmaLista vastaukset;
   
   /* Rakentajat */
   
   /** Viestin rakentaja
    * Luodaan viesti annetuista parametreista
    *
    * @param t Viestin tunniste
    * @param s Viestin sisltö
    * @param a Edeltävä viesti
    * @param f Liitetiedosto  
    * @throws IllegalArgumentException jos parametreissa oli virhe
    */
   public Viesti (int t, String s, Viesti a, Tiedosto f) {
      try {
         tunniste(t);
         sisalto(s);
         aikaisempi(a);
         liite(f);
         vastaukset = new OmaLista();
      } 
      catch (Exception e) {
         throw new IllegalArgumentException("Error!");
      }
   }
   
   /* Aksessorit */
   
   // Tunniste
   
   @Setteri
   public void tunniste(int t) throws IllegalArgumentException {
      if (t > 0) {
         tunniste = t;
      }
      else {
         throw new IllegalArgumentException("Error!"); // virhe kun tunniste negatiivinen tai 0
      }
   }
   
   @Getteri
   public int tunniste() {
      return tunniste;
   }
   
   // Viestisisältö

   @Setteri
   public void sisalto(String s) throws IllegalArgumentException {
      if (s.length() >= 1) {
         sisalto = s;
      }
      else {
         throw new IllegalArgumentException("Error!"); // virhe kun sisätlöä ei ole
      }
   }
   
   @Getteri
   public String sisalto() {
      return sisalto;
   }
   
   // Aikaisemmat viestit
   
   @Setteri
   public void aikaisempi(Viesti a) throws IllegalArgumentException {
      aikaisempi = a;
   }
   
   @Getteri
   public Viesti aikaisempi() {
      return aikaisempi;
   }
   
   // Liite
   
   @Setteri
   public void liite(Tiedosto f) throws IllegalArgumentException {
      liite = f;
   }
   
   @Getteri
   public Tiedosto liite() {
      return liite;
   }
   
   // Liittyvät vastaukset
   
   @Setteri
   public void vastaukset(OmaLista l) throws IllegalArgumentException {
      if (l != null) {
         vastaukset = l;
      }
      // Virhe kun sisältöä ei ole
      else {
         throw new IllegalArgumentException("Error!");
      }
   }
   
   @Getteri
   public OmaLista vastaukset() {
      return vastaukset;
   }
   
   /* "Comparable"-rajapinnan metodit */
   
   @Override
   public int compareTo(Viesti kohde) {
      if (tunniste > kohde.tunniste()) {
         return 1;
      }
      else if (tunniste < kohde.tunniste()) {
         return -1;
      }
      else {
         return 0;
      }
   }
   
   /* "Object"-luokan metodit */
   
   @Override
   public String toString() {
      String paluuarvo = "#" + tunniste + " " + sisalto;
      if (liite != null) {
         paluuarvo += " (" + liite.toString() + ")";
      }
      return paluuarvo;
   }
   
   @Override
   public boolean equals(Object o) {
      try {
         Viesti v = (Viesti)o;

         // Oliot ovat samat, jos attribuuttien arvot ovat samat
         if (tunniste == v.tunniste()) {
            return true;
         }
         else {
            return false;
         }
      }
      catch (Exception e) {
         return false;
      }
   }
   
   /* "Komennettava"-rajapinnan metodit */
   
   @Override
   public Viesti hae (Viesti haettava) throws IllegalArgumentException {
      if (haettava != null) {
         Viesti v = (Viesti)vastaukset.hae(haettava);
         return v;
      }
      else {
         throw new IllegalArgumentException("Error!");
      }
   }
   
   @Override
   public void lisaaVastaus (Viesti lisattava) throws IllegalArgumentException {
      if (lisattava!= null && vastaukset.hae(lisattava) == null) {
         vastaukset.lisaaLoppuun(lisattava);
      }
      else {
         throw new IllegalArgumentException("Error!");
      }
   }
   
   @Override
   public void tyhjenna() {
      sisalto = Komennettava.POISTETTUTEKSTI;
      liite = null;
   }
}
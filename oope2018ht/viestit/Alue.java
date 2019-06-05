package oope2018ht.viestit;

/**
 * OOPE 2018 harkkatyö
 * Alue-luokka.
 * @author Heidi Happonen, 424364, Happonen.Heidi.K@student.uta.fi
 */

import java.io.File;
import java.util.Scanner;
import oope2018ht.apulaiset.*;
import oope2018ht.omalista.OmaLista;
import oope2018ht.tiedostot.*;

public class Alue {
   
   /* Attribuutit */
   
   /** Viestiketjujen lukumäärä */
   private int ketjuLkm;
   /** Viestien lukumäärä */
   private int viestiLkm;
   /** Aktiivinen ketju */
   private Ketju aktiivinenKetju;
   /** Lista viestiketjuista */
   private OmaLista ketjut;

   /* Rakentajat */
   
   /** Alueen rakentaja
    * Luodaan alue
    */
   public Alue() {
     ketjuLkm = 0;
     viestiLkm = 0;
     ketjut = new OmaLista();
   }
   
   /* Aksessorit */
   
   // Ketjujen lkm
  
   @Setteri
   public void ketjuLkm(int k) throws IllegalArgumentException {
     if( k > 0) {
       ketjuLkm = k;
     }
    else {
       throw new IllegalArgumentException("Error!");
    }
   }
   
   @Getteri
   public int ketjuLkm(){
      return ketjuLkm;
   }
   
   // Viestien lkm 
   
   @Setteri
   public void viestiLkm(int v) throws IllegalArgumentException {
     if (v > 0) {
       ketjuLkm = v;
     }
    else {
       throw new IllegalArgumentException("Error!");
     }
   }
   
   @Getteri
   public int viestiLkm(){
      return viestiLkm;
   }
   
   // Aktiivinen ketju
   
   @Setteri
   public void aktiivinenKetju(int ak) throws IllegalArgumentException {
      boolean found = false;
      for (int i = 0; i < ketjuLkm; i++) {
         Ketju temp = (Ketju)ketjut.alkio(i);
         if (temp.tunniste() == ak) {
            aktiivinenKetju = temp;
            found = true;
         }
      }
      if (!found) {
         throw new IllegalArgumentException("Error!");
      }
   }
   
   @Getteri
   public Ketju aktiivinenKetju() {
      return aktiivinenKetju;
   }
   
   @Getteri
   public OmaLista ketjut(){
      return ketjut;
   }
   
   /** Uuden viestiketjun lisääminen
    * Lisätään foorumille uusi viestiketju ja asetetaan se aktiiviseksi jos on ensimmäinen
    *
    * @param merkkijono 
    */
   public void add(String merkkijono) {
      Ketju temp = new Ketju(ketjuLkm +1, merkkijono);
      if (ketjut.koko() == 0) {
         aktiivinenKetju = temp;
      }
      ketjut.lisaaLoppuun(temp);
      ketjuLkm++;
   }
   
   /** Uuden viestin lisääminen viestiketjuun
    * Luodaan uusi viesti olemassa olevaan viestiketjuun
    *
    * @param merkkijono Viesti
    * @param merkkijono Liitetiedosto
    */
   public void newMessage(String merkkijono, Tiedosto liite) {
      Viesti temp = new Viesti(viestiLkm +1, merkkijono, null, null);
      if (liite != null) {
         temp.liite(liite);
      }
      aktiivinenKetju.oksaViestit().lisaaLoppuun(temp);
      aktiivinenKetju.viestit(aktiivinenKetju.viestit() + 1);
      viestiLkm++;
   }
   
   /** Lista viestiketjuista
    * Listataan keskustelualueen ketjut
    * @return lista viestiketjuista
    */
   public void catalog() {
      for ( int i = 0; i < ketjut.koko(); i++ ) {
         Ketju temp = (Ketju)ketjut.alkio(i);
         System.out.println("#" + temp.tunniste() + " " + temp.merkkijono() + " "
               + "(" + temp.viestit() + " messages)");
      }
   }
   
   /** Aktiivisen ketjun vaihtaminen
    * @param id Sen ketjun tunniste, johon vaihdetaan
    */
   public void select(int id) {
      aktiivinenKetju(id);
   }
   
   /** Liitteen olemassaolon varmistus ja välitys
    * Tarkistetaan liitteen olemassaolo ja välitetään se viestin luomis operaatiolle
    * @param merkkijono Uusi viestisisältö
    * @param liite Välitettävä liite
    * @param v Viesti, jotka tarkastetaan
    * @throws IllegalArgumentException jos parametreissa oli virhe
    */
   public void liite(String merkkijono, String liite, Viesti v) {
      try{
         Tiedosto tiedosto = null;
         File file = new File(liite);
         Scanner sc = new Scanner(file);
         String tiedot = "";
         while (sc.hasNextLine()) {
            tiedot = sc.nextLine();
         }
         String[] temp = tiedot.split(" ");
         int koko = Integer.parseInt(temp[1]);
         
         if("Kuva".equals(temp[0])) {
            int leveys = Integer.parseInt(temp[2]);
            int korkeus = Integer.parseInt(temp[3]);
            tiedosto = new Kuva(liite, koko, leveys, korkeus);
         }
       else if ("Video".equals(temp[0])) {
            double pituus = Double.parseDouble(temp[2]);
            tiedosto = new Video(liite, koko, pituus);
         }
         // Tarkistetaan vastataanko aiempaan viestiin vai luodaanko uusi keskuteluoksa
         if (v != null){
            reply(merkkijono, tiedosto, v);
         }
       else {
            newMessage(merkkijono, tiedosto);
         }
         
      } catch (Exception e) {
         throw new IllegalArgumentException("Error!");
      }
   }
   
   /** Viestihaku tunnisteen perusteella
    * @param id Haettavan viestin tunniste
    * @return Viesti Palauttaa viestin, jonka tunniste on id
    */
   public Viesti etsi(int id) {
      Viesti paluuarvo = new Viesti(Integer.MAX_VALUE, "haha", null, null);
      for ( int i = 0; i < aktiivinenKetju.oksaViestit().koko(); i++ ) {
         Viesti v = (Viesti)aktiivinenKetju.oksaViestit().alkio(i);
         if (v.tunniste() == id) {
            return v;
         }
         if (v.vastaukset() != null) {
            paluuarvo = etsi(id, v);
            if (paluuarvo.tunniste() != Integer.MAX_VALUE) {
               return paluuarvo;
            }
         }
      }
      return paluuarvo;
   }
   
   /** Viestihaku vastauslistalta tunnisteen perusteella
    * @param id Haettavan viestin tunniste
    * @param v Viesti jonka vastauslistalta haetaan
    * @return Viesti Palauttaa viestin, jonka tunniste on id
    */
   public Viesti etsi(int id, Viesti v) {
      Viesti paluuarvo = new Viesti(Integer.MAX_VALUE, "haha", null, null);
      for (int i = 0; i < v.vastaukset().koko(); i++) {
         Viesti t = (Viesti)v.vastaukset().alkio(i);
         if (t.tunniste() == id) {
            return t;
         }
         if (t.vastaukset()!= null) {
            paluuarvo = etsi(id, t);
            if (paluuarvo.tunniste() != Integer.MAX_VALUE) {
               return paluuarvo;
            }
         }
      }
      return paluuarvo;
   }
   
   /** Viestiin vastaaminen
    * @param s Uuden viestin sisältö
    * @param t Uuden viestin liitetiedosto
    * @param v Viesti, johon vastaus liittyy
    */
   public void reply(String s, Tiedosto t, Viesti v) {
      Viesti temp = new Viesti(viestiLkm +1, s, v, null);
      if (t != null) {
         temp.liite(t);
      }
      v.lisaaVastaus(temp);
      aktiivinenKetju.viestit(aktiivinenKetju.viestit() + 1);
      viestiLkm++;
   }
   
   public void tree() {
      System.out.println("=");
      System.out.println("== #" + aktiivinenKetju.tunniste() + " " + aktiivinenKetju.merkkijono() + " "
            + "(" + aktiivinenKetju.viestit() + " messages)");
      System.out.println("===");
      int i = 0;
      while (i < aktiivinenKetju.oksaViestit().koko()) {
         Viesti temp = (Viesti)aktiivinenKetju.oksaViestit().alkio(i);
         tree(temp, 0);
         i++;
      }
   }
   
   /** Rekursion apuoperaatio
    * @param s Viestin sisältö
    * @param t Viestin liitetiedosto
    */
   public void tree(Viesti v, int s) {
      int i = 0;
      String tuloste = "";
      String sisennys = "   ";
      while (i < s) {
         tuloste += sisennys;
         i++;
      }
      tuloste += v.toString();
      System.out.println(tuloste);
      int j = 0;
      if (v.vastaukset() != null) {
         while (j < v.vastaukset().koko()) {
            Viesti temp = (Viesti)v.vastaukset().alkio(j);
            tree(temp, s + 1);
            j++;
         }
      }
   }
   
   /** Viestitulostus
    * Tulosta kaikki ketjun viestit
    * @return lista ketjuun kuuluvista viesteistä
    */
   public void list() {
      OmaLista tulostettavat = aktiivinenKetju.kaikkiViestit();
      
      // Tulosta ketjun aihe muotoiltuna
      System.out.println("=");
      System.out.println("== #" + aktiivinenKetju.tunniste() + " " + aktiivinenKetju.merkkijono() + " "
            + "(" + aktiivinenKetju.viestit() + " messages)");
      System.out.println("===");
      
      // Tulosta ketjun viestit järjestyksessä
      tulosta(tulostettavat);
   }
   
   /** Ketjun vanhimmat viestit tulostava operaatio
    * @param range Tulostusalueen koko
    */
   public void head(int range) {
      OmaLista tulostettavat = aktiivinenKetju.kaikkiViestit();
      tulostettavat = tulostettavat.annaAlku(range);
      tulosta(tulostettavat);
   }
   
   /** Ketjun uusimmat viestit tulostava operaatio
    * @param range Tulostusalueen koko
    */
   public void tail(int range) {
      OmaLista tulostettavat = aktiivinenKetju.kaikkiViestit();
      tulostettavat = tulostettavat.annaLoppu(range);
      tulosta(tulostettavat);
   }
   
   /** Ketjun viestien listan tulostamisen apuoperaatio
    * @param lista Viestilista
    */
   public void tulosta(OmaLista lista) {
      for ( int i = 0; i < lista.koko(); i++ ) {
         Viesti temp = (Viesti)lista.alkio(i);
         System.out.println(temp.toString());
      }
   }
   
   /** Poista viestin sisältö ja mahdollinen liite
    * @param v Poistettava viesti
    */
   public void empty(Viesti v) {
      v.tyhjenna();
   }
   
   /** Hakutoiminto
    * @param merkkijono Hakulauseke
    */
   public void find(String merkkijono) {
      OmaLista kaikki = aktiivinenKetju.kaikkiViestit();
      OmaLista tulostettavat = new OmaLista();
      for ( int i = 0; i < kaikki.koko(); i++ ) {
         Viesti temp = (Viesti)kaikki.alkio(i);
         String full = temp.toString();
         if (tarkista(merkkijono, full)) {
            tulostettavat.lisaa(temp);
         }
      }
      tulosta(tulostettavat);
   }
   
   /** Etsi merkkijonoa viestistä ja liitteestä
    * @param merkkijono 
    * @param viesti 
    * @return 
    */
   public boolean tarkista(String merkkijono, String viesti) {
      for ( int i = 0; i < viesti.length(); i++ ) {
         boolean erimerkki = false;
         if (viesti.charAt(i) == merkkijono.charAt(0)) {
            for ( int j = 0; j < merkkijono.length(); j++ ) {
               if (i+j >= viesti.length()) {
                  return false;
               }
               if (viesti.charAt(i + j) != merkkijono.charAt(j)) {
                  erimerkki = true;
               }
            }
            if (!erimerkki) {
               return true;
            }
         }
      }
      return false;
   }
}
package oope2018ht.kayttoliittyma;

/**
 * OOPE 2018 harkkatyö
 * Ohjelman käyttöliittymä
 * @author Heidi Happonen, 424364, Happonen.Heidi.K@student.uta.fi
 */

import oope2018ht.apulaiset.*;
import oope2018ht.viestit.Alue;
import oope2018ht.viestit.Viesti;

public class Kayttoliittyma {
   /** Vakio komennolle Exit */
   public static final String EXIT = "exit";
   
   /** Vakio komennolle Add */
   public static final String ADD = "add ";
   
   /** Vakio komennolle Catalog */
   public static final String CATALOG = "catalog";
   
   /** Vakio komennolle Select */
   public static final String SELECT = "select ";
   
   /** Vakio komennolle New */
   public static final String NEW = "new ";
   
   /** Vakio komennolle Reply */
   public static final String REPLY = "reply ";
   
   /** Vakio komennolle Tree */
   public static final String TREE = "tree";
   
   /** Vakio komennolle List */
   public static final String LIST = "list";
   
   /** Vakio komennolle Head */
   public static final String HEAD = "head";
   
   /** Vakio komennolle Tail */
   public static final String TAIL = "tail";
   
   /** Vakio komennolle Empty */
   public static final String EMPTY = "empty";
   
   /** Vakio komennolle Find */
   public static final String FIND = "find";
   
   /** Käyttöliittymän pääsilmukka */
   public void aja() {
      
      // Tervehditään käyttäjää
      System.out.println("Welcome to S.O.B.");
      
      // Määritellään alue ja aloitetaan ohjelma
      Alue alue = new Alue();
      boolean aja = true;
      
      while(aja) {
         System.out.print(">");
         String syote = In.readString();
         String[] temp;
         
         // Jos käyttäjä antaa oikeanlaisen poistumispyynnön, poistutaan ohjelmasta
         if (syote.startsWith(EXIT) && syote.length() == 4) {
               aja = false;
               System.out.println("Bye! See you soon.");
         }
         // Jos käyttäjä antaa komennon "Add" ja sisältöä komennon jälkeen, lisätään annettu sisältö
         else if (syote.startsWith(ADD) && syote.length() > 4) {
            
            // Katkaistaan syote kahteen osaan: komento ja loput
            temp = syote.split(" ", 2); 
            
            // Päivitetään syötteen olevan vain merkkijono-osuus
            syote = temp[1];
            if (check(syote)) {
               alue.add(syote);
            }
            else {
               valita();
            }
         }
         // Jos käyttäjä antaa oikeanlaisen "Catalog"-komennon, tulostetaan käyttäjälle alueen katalogi
         else if (syote.startsWith(CATALOG) && syote.length() == 7) {
            alue.catalog();
         }
         // Jos käyttäjä antaa syötteen "Select" ja sisältöä sen jälkeen, toteutetaan valinta
         else if (syote.startsWith(SELECT) && syote.length() > 6) {
            temp = syote.split(" ", 2);
            
            if ((temp[1].charAt(temp[1].length() -1) != ' ') && 
            (temp[1].charAt(0) != ' ')) {
               try {
                  int id = Integer.parseInt(temp[1]);
                  alue.select(id);
               }
               catch (Exception e) {
                  valita();
               }
            }
            else {
               valita();
            }
         }
         // Jos käyttäjä antaa oikeanlaisen "New"-komennon, lisätään uusi viesti
         else if (syote.startsWith(NEW) && syote.length() > 4) {
            
            // Jaetaan syöte kahteen osaan: viesti ja mahdollinen liite
            temp = syote.split(" ", 2); 
            syote = temp[1];
            
            if (check(syote)) {
               try {
                  if (checkL(syote)) {
                     temp = syote.split(" &");
                     alue.liite(temp[0], temp[1], null);
                  }
                  else {
                     alue.newMessage(syote, null);
                  }
               }
               catch (Exception e) {
                  valita();
               }
            }
            else {
               valita();
            }
         }
         // Jos käyttäjä antaa oikeanlaisen "Reply"-komennon, lisätään uusi vastaus
         else if (syote.startsWith(REPLY) && syote.length() > 6) {
            try {
               // Jaetaan syöte kolmeen osaan: viesti, mahdollinen liite ja viesti johon vastataan
               temp = syote.split(" ", 3);
               int id = Integer.parseInt(temp[1]);
               syote = temp[2];
               
               // Jos vastattava viesti löytyy ja syötteen merkkijono ok
               Viesti test  = alue.etsi(id);
               if (test.tunniste() != Integer.MAX_VALUE && check(syote)) {
                  if (checkL(syote)) {
                     temp = syote.split(" &");
                     alue.liite(temp[0], temp[1], test);
                  }
                  // Tapaukset joissa ei liitettä
                  else { 
                     alue.reply(syote, null, test);
                  }
               }
               else {
                  valita();
               }
            }
            catch (Exception e) {
               valita();
            }
         }
         else if (syote.startsWith(TREE) && syote.length() == 4) {
            try {
               alue.tree();
            }
            catch (Exception e) {
               valita();
            }
         }
         // Jos käyttäjä antaa oikeanlaisen "List"-komennon, tulostetaan lista
         else if (syote.startsWith(LIST) && syote.length() == 4) {
            try {
               alue.list();
            }
            catch (Exception e) {
               valita();
            }
         }
         // Jos käyttäjä antaa oikeanlaisen "Head"-komennon, tulostetaan annettu määrä
         // viestejä alusta
         else if (syote.startsWith(HEAD) && syote.length() > 5) {
            temp = syote.split(" ", 2);
            try {
               alue.head(Integer.parseInt(temp[1]));
            }
            catch (Exception e) {
               valita();
            }
         }
         // Jos käyttäjä antaa oikeanlaisen "Tail"-komennon, tulostetaan annettu määrä
         // viestejä lopusta
         else if (syote.startsWith(TAIL) && syote.length() > 5) {
            temp = syote.split(" ", 2);
            try {
               alue.tail(Integer.parseInt(temp[1]));
            }
            catch (Exception e) {
               valita();
            }
         }
         // Jos käyttäjä antaa oikeanlaisen "Empty"-komennon, tyhjennetään lista
         else if (syote.startsWith(EMPTY) && syote.length() > 6) {
            temp = syote.split(" ", 2);
            int id = Integer.parseInt(temp[1]);
            Viesti test = alue.etsi(id);
            if (test.tunniste() != Integer.MAX_VALUE) {
               try {
                  alue.empty(test);
               }
               catch (Exception e) {
                  valita();
               }
            }
            else {
               valita();
            }
         }
         // Jos käyttäjä antaa oikeanlaisen "Find"-komennon, etsitään haettu sisältö
         else if (syote.startsWith(FIND) && syote.length() > 5) {
            temp = syote.split(" ", 2);
            if (check(temp[1])) {
               try {
                  alue.find(temp[1]);
               }
               catch (Exception e) {
                  valita();
               }
            }
            else{
               valita();
            }
         }
         else {
            valita();
         }
      }
   }
   
   // Erroriin käytettävä operaatio, nopeuttaa valituksen muokkaamista
   
   public void valita() {
      System.out.println("Error!");
   }
   
   /** Tarkistetaan syötteen oikeellisuus
    *
    * @param merkkijono Tarkistetava syöte
    * @return boolean true jos syöte oikea, false jos ei
    */
   public boolean check(String merkkijono) {
      boolean paluuarvo = true;
      
      // Jos tyhjä
      if (merkkijono.length() == 0) {
         return false;
      }
      
      // Loppuu välilyöntiin
      if (merkkijono.charAt(merkkijono.length() - 1) == ' ') {
         return false;
      }
      return paluuarvo;
   }
   
   /** Pituuden tarkistus
    *
    * @param merkkijono Tarkistetava syöte
    * @return boolean true jos pituus ok, false jos ei
    */
   
   public boolean checkL(String merkkijono) {
      boolean liite = false;
         for ( int i = 0; i + 1 < merkkijono.length(); i++ ) {
            if (merkkijono.charAt(i) == ' ' && merkkijono.charAt(i+1) == '&') {
               return true;
         }
      }
      return liite;
   }
}
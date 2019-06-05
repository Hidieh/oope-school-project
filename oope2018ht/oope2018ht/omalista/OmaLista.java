package oope2018ht.omalista;

/**
 * OOPE 2018 harkkatyš
 * OmaLista-luokka
 * @author Heidi Happonen, 424364, Happonen.Heidi.K@student.uta.fi
 */

import oope2018ht.apulaiset.*;
import fi.uta.csjola.oope.lista.*;

public class OmaLista extends LinkitettyLista implements Ooperoiva<OmaLista> {
   
   /* Hae-operaatio, "ooperoiva"-rajapinnasta */
   
   @Override
   public Object hae(Object haettava) {
      for ( int i=0; i<koko(); i++ ) {
         if ( alkio(i).equals(haettava) )
            return alkio(i);
      }
      return null;
   }
   
   /* LisŠŠ-operaatio, "ooperoiva"-rajapinnasta */
   
   @SuppressWarnings("unchecked")
   @Override
   public boolean lisaa(Object lisattava) {
      if ( lisattava != null && hae(lisattava) == null ) {
         try {
            Comparable temp = (Comparable)lisattava;
            // lisŠtŠŠn alkuun, jos lista on tyhjŠ
            if ( koko < 1 ) {
               lisaaAlkuun(lisattava);
               return true;
            } 
            // jos lista ei ole tyhjŠ, sijoitetaan lisŠttŠvŠ oikealle paikalle
            else {
               for ( int i=0; i<=koko; i++ ) {
                  if ( i == koko ) {
                     lisaaLoppuun(lisattava); // lisŠtŠŠn loppuun  
                     return true;  // sijoitus onnistui, antaa paluuarvon "true"  
                  }
                  else if ( temp.compareTo(alkio(i)) < 0 ) {
                     lisaa(i, lisattava); // lisŠtŠŠn keskelle
                     return true; // sijoitus onnistui, antaa paluuarvon "true"
                  }
               }
            }
         }
         catch (Exception e) {
            return false;
         }
      }
      return false;
   }
   
   /* annaAlku-operaatio "ooperoiva"-rajapinnasta */
   
   public OmaLista annaAlku(int n) {
      if ( koko > 0 ) {
         if ( n <= koko && n >= 1 ) {
            OmaLista paluuarvo = new OmaLista();
            for ( int i=0; i<n; i++ ) {
               paluuarvo.lisaaLoppuun(alkio(i));
            }
            return paluuarvo;
         }
         else {
            return null;
         }
      }
      else {
         return null;
      }
   }
   
   /* annaLoppu-operaatio "ooperoiva"-rajapinnasta */
   
   public OmaLista annaLoppu(int n) {
      if ( koko > 0 ) {
         if ( n <= koko && n >= 1 ) {
            OmaLista paluuarvo = new OmaLista();
            for ( int i=koko-n; i<koko; i++ ) {
               paluuarvo.lisaaLoppuun(alkio(i));
            }
            return paluuarvo;
         }
         else {
            return null;
         }
      }
      else {
         return null;
      }
   }
}
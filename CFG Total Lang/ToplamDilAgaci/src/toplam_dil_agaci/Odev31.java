package toplam_dil_agaci;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author gunay
 */
class Kelime {

    String sembol; // S
    ArrayList<String> kelimeler; // aa|bX|aXX

    public Kelime() {
        kelimeler = new ArrayList();
    }
}

public class Odev31 {

    public static ArrayList<String> semboller = new ArrayList();
    public static ArrayList<Kelime> kelimeNesneleri = new ArrayList();

    public static void degerAl() throws FileNotFoundException {

        try {
            BufferedReader br = new BufferedReader(new FileReader("bilgi.txt"));
            String alfabe = br.readLine();
            String girilen = br.readLine();
            System.out.println("Dosyadan okunuyor...\nGirilen Deger:"+girilen+"\n");
            String virguleGore[] = girilen.trim().split(",");
            int i = 0;

            for (String s : virguleGore) {
                String okaGore[] = s.trim().split("-->"); //0. index sembol:S, 1. index kelimeler:aa|bX|aXX
                okaGore[0] = okaGore[0].trim();
                semboller.add(okaGore[0]);
                String kelimelerGecici[] = okaGore[1].trim().split("\\|"); // 0 : aa 1: bX... 
                kelimeNesneleri.add(new Kelime());
                for (String k : kelimelerGecici) {
                    k = k.trim();
                    if (kelimeNesneleri.get(i) != null) {
                        kelimeNesneleri.get(i).kelimeler.add(k);
                    }
                }
                kelimeNesneleri.get(i).sembol = okaGore[0];

                i++;
            }

        } catch (IOException e) {

        }

    }

    public static void main(String[] args) throws FileNotFoundException {
        degerAl();

        ArrayList<String> kelimeListesi = new ArrayList(); // oluşacak kelimeler
        ArrayList<String> tekrarEdenler = new ArrayList();

        Boolean iceriyor = true, break_K = false, break_K2 = false;

        while (iceriyor) {

            iceriyor = false;

            for (String kelime : kelimeNesneleri.get(0).kelimeler) {
                for (String sembol : semboller) {
                    if (kelime.contains(sembol)) {
                        iceriyor = true;
                        break;
                    }

                }
            }

            if (iceriyor == false) {
                break;
            }

            for (String kelime : kelimeNesneleri.get(0).kelimeler) {
                boolean kontrol = false; //if'e girdi mi

                if (kelime.length() > 30) { //sonsuz
                    break_K = true;
                }

                int i = 0;
                for (char harf : kelime.toCharArray()) {

                    if (semboller.contains(String.valueOf(harf))) {
                        int index = semboller.indexOf(String.valueOf(harf)); // semboller[0] = S ise, sembolü S olan nesne de kelimeNesneleri.get(0)'da.
                        Kelime geciciNesne = kelimeNesneleri.get(index);

                        kontrol = true;
                        if (geciciNesne.kelimeler.size() > 40) {
                            break_K2 = true;
                            break;
                        }

                        for (String k : geciciNesne.kelimeler) {
                            String yeni = kelime.substring(0, i) + k + kelime.substring(i + 1);
                            if (yeni.length() > 30) {
                                break_K2 = true;
                                break;
                            }
                            if (!(kelimeListesi.contains(yeni))) {
                                kelimeListesi.add(yeni);

                            } else if (!(tekrarEdenler.contains(yeni))) {
                                tekrarEdenler.add(yeni);
                            }

                        }

                    }
                    if (break_K2) {
                        break;
                    }
                    i += 1;
                }

                if (!kontrol) {

                    if (!(kelimeListesi.contains(kelime))) {
                        kelimeListesi.add(kelime);
                    } else if (!(tekrarEdenler.contains(kelime))) {
                        tekrarEdenler.add(kelime);
                    }

                }
            }

            if (break_K || break_K2) { // sonsuz kontrol
                break;
            }

            ArrayList<String> geciciListe = new ArrayList<>(kelimeListesi);
            kelimeNesneleri.get(0).kelimeler = geciciListe;
            kelimeListesi.clear();

        }
        

        System.out.println("Uretilen Kelimeler:");
        if (break_K || break_K2) {
            System.out.println("Sonsuz sayıda kelime oluşuyor. Bunlardan birkaçı:");
            int sayac = 0;

            Collections.sort(kelimeNesneleri.get(0).kelimeler, Comparator.comparing(String::length));
            for (String kelime : kelimeNesneleri.get(0).kelimeler) {
                int yazilsinMiSay = 0;
                for (String sembol : semboller) {
                    if (!(kelime.contains(sembol))) {
                        yazilsinMiSay += 1;
                    }
                }
                if (yazilsinMiSay == semboller.size()) {
                    if (sayac == 9) {
                        System.out.print(kelime + "...");
                    } else {
                        System.out.print(kelime + ", ");
                    }

                    sayac++;
                    if (sayac == 10) {
                        break;
                    }
                }
            }

            Collections.sort(tekrarEdenler, Comparator.comparing(String::length));
            System.out.println("\nTekrarlanan Kelimeler:");
            sayac = 0;
            
            for (String kelime : tekrarEdenler) {
                int yazilsinMiSay2 = 0;
                for (String sembol : semboller) {
                    if (!(kelime.contains(sembol))) {
                        yazilsinMiSay2+= 1;
                    }
                }
                if (yazilsinMiSay2 == semboller.size()) {
                    if (sayac == 9) {
                        System.out.print(kelime + "...");
                    } else {
                        System.out.print(kelime + ", ");
                    }

                    sayac++;
                    if (sayac == 10) {
                        break;
                    }
                }
            }
            
     
        } else {
            for (String s : kelimeNesneleri.get(0).kelimeler) {
                if (s.matches(kelimeNesneleri.get(0).kelimeler.get(kelimeNesneleri.get(0).kelimeler.size() - 1))) { // son kelimede virgul olmasin diye
                    System.out.print(s);
                } else {
                    System.out.print(s + ", ");
                }
            }

            System.out.println("\nTekrarlanan Kelimeler:");
            for (String k : tekrarEdenler) {
                if (k.matches(tekrarEdenler.get(tekrarEdenler.size() - 1))) {
                    System.out.print(k);
                } else {
                    System.out.print(k + ", ");
                }
            }

        }
        System.out.println("");

    }
}

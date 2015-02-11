package parkme.projectm.hr.parkme.Helpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


/**
 * Created by Cveki on 9.11.2014..
 */
public class JavaJsonHelper {}

    /*{
        "brojKarata":"String content",
            "dodatneInformacije":"String content",
            "idKoncert":2147483647,
            "idKorisnik":2147483647,
            "idLokacija":2147483647,
            "izvodjac":"String content",
            "naziv":"String content",
            "status":2147483647,
            "vrijemeOdrzavanja":"String content",
            "webIzvodjac":"String content"
    }*/

    //{"phonetype":"N95","cat":"WP"}
    //JSONObject jsonObj = new JSONObject("{\"phonetype\":\"N95\",\"cat\":\"WP\"}");
/*
    public static Koncert[] izStringaNapraviKoncerte(String koncerti) throws JSONException {

        koncerti = "{ \"koncerti\": " + koncerti + " }";

        JSONArray jsonKoncerti = new JSONObject(koncerti).getJSONArray("koncerti");
        for (int i = 0; i < jsonKoncerti.length(); i++) {
            JSONObject koncert = jsonKoncerti.getJSONObject(i);
            //Log.d("MOJE", i + "----" + koncert);
        }
        Koncert[] sviKoncerti = new Koncert[jsonKoncerti.length()];

        for (int i = 0; i < jsonKoncerti.length(); i++) {
            sviKoncerti[i] = izJsonaNapraviKoncert(jsonKoncerti.getJSONObject(i));
        }

        return sviKoncerti;
    }

    public static Drzava[] izStringaNapraviDrzave(String drzava) throws JSONException {
        drzava="{ \"drzave\": " + drzava + " }";
        JSONArray jsonDrzave= new JSONObject(drzava).getJSONArray("drzave");

        Drzava[]sveDrzave=new Drzava[jsonDrzave.length()];

        for (int i = 0; i < jsonDrzave.length(); i++) {
            sveDrzave[i] = izJsonaNapraviDrzavu(jsonDrzave.getJSONObject(i));
        }

        return sveDrzave;

    }

    public static Mjesto[] izStringaNapraviMjesta(String mjesta) throws JSONException {

        mjesta="{ \"mjesta\": " + mjesta + " }";
        JSONArray jsonMjesta= new JSONObject(mjesta).getJSONArray("mjesta");

        Mjesto[]svaMjesta=new Mjesto[jsonMjesta.length()];

        for (int i = 0; i < jsonMjesta.length(); i++) {
            svaMjesta[i] = izJsonaNapraviMjesto(jsonMjesta.getJSONObject(i));
        }

        return svaMjesta;
    }

    public static Lokacija[] izStringaNapraviLokacije(String lokacije) throws JSONException {
        lokacije="{ \"lokacije\": " + lokacije + " }";
        JSONArray jsonLokacija= new JSONObject(lokacije).getJSONArray("lokacije");

        Lokacija[]sveLokacije=new Lokacija[jsonLokacija.length()];

        for (int i = 0; i < jsonLokacija.length(); i++) {
            sveLokacije[i] = izJsonaNapraviLokaciju(jsonLokacija.getJSONObject(i));
        }

        return sveLokacije;

    }

    public static Lokacija izJsonaNapraviLokaciju(JSONObject json) throws JSONException {

        String adresa= json.getString("adresa");
        String email=json.getString("email");
        int idKorisnik=json.getInt("idKorisnik");
        int idLokacija=json.getInt("idLokacija");
        int idMjesto=json.getInt("idMjesto");
        String latitude=json.getString("latitude");
        String longitude=json.getString("longitude");
        String naziv=json.getString("naziv");
        String telefon=json.getString("telefon");
        String web=json.getString("web");

        Lokacija lokacija= new Lokacija(idLokacija,naziv,longitude,latitude,adresa,web,email,telefon,idMjesto,idKorisnik);

        return lokacija;



    }

    public static Mjesto izJsonaNapraviMjesto(JSONObject json) throws JSONException {
        int idDrzava=json.getInt("idDrzava");
        int idMjesto=json.getInt("idMjesto");
        String nazivMjesto= json.getString("mjesto");
        String pbr=json.getString("pbr");

        Mjesto mjesto=new Mjesto(idMjesto,nazivMjesto,pbr,idDrzava);

        return mjesto;

    }

    public static Drzava izJsonaNapraviDrzavu(JSONObject json) throws JSONException {

        String imeDrzave=json.getString("drzava");
        int idDrzava=json.getInt("idDrzava");

        Drzava drzava= new Drzava(idDrzava,imeDrzave);

        return drzava;



    }

    public static JSONObject izStringaNapraviJson(String string) throws JSONException {
        JSONObject jsonObject= new JSONObject(string);
        return  jsonObject;
    }


    public static JSONObject izKoncertaNapraviJson(Koncert koncert) throws IOException, JSONException {

        JSONObject novi = new JSONObject();
        novi.put("brojKarata", koncert.getBrojKarata());
        novi.put("dodatneInformacije", koncert.getDodatneInformacije());
        novi.put("idKoncert", koncert.getIdKoncert());
        novi.put("idKorisnik", koncert.getIdKorisnik());
        novi.put("idLokacija", koncert.getIdLokacija());
        novi.put("izvodjac", koncert.getIzvodjac());
        novi.put("status", koncert.getStatus());
        novi.put("vrijemeOdrzavanja", koncert.getVrijemeOdrzavanja());
        novi.put("webIzvodjac", koncert.getWebIzvodjac());

        return novi;

    }

    public static Koncert izJsonaNapraviKoncert(JSONObject json) throws JSONException {


        String brojKarata = json.getString("brojKarata");
        String dodatneInformacije = json.getString("dodatneInformacije");
        String naziv = json.getString("naziv");
        int idKoncert = json.getInt("idKoncert");
        int idKorisnik = json.getInt("idKorisnik");
        int idLokacija = json.getInt("idLokacija");
        String izvodjac = json.getString("izvodjac");
        int status = json.getInt("status");
        String vrijemeOdrzavanja = json.getString("vrijemeOdrzavanja");
        String webIzvodjac = json.getString("webIzvodjac");

        Koncert novi = new Koncert(idKoncert, naziv, izvodjac, webIzvodjac, vrijemeOdrzavanja, dodatneInformacije, brojKarata, status, idLokacija, idKorisnik);
        //Log.d("MOJE", "----" + novi.getNaziv());
        return novi;

    }


}
*/
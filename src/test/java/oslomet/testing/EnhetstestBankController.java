package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentKundeInfo_loggetInn() {

        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertEquals(enKunde, resultat);
    }

    @Test
    public void hentKundeInfo_IkkeloggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }

    // Test hent Konta (Logget Inn)
    @Test
    public void hentKonti_LoggetInn()  {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_IkkeLoggetInn()  {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }

    // tester hent Transaksjoner (Logget inn OK)
    @Test
    public void hentTransaksjoner_LoggetInn_OK(){
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Transaksjon betaling_1 = new Transaksjon(1, "12341234",
                100.1, "05-02-2024", "SiO" , "1",
                "56785678");

        Transaksjon betaling_2 = new Transaksjon(2, "12341234",
                200.2, "05-02-2024", "if" , "1",
                "56785678");

        transaksjoner.add(betaling_1);
        transaksjoner.add(betaling_2);

        Konto konta = new Konto("16099425702", "56785678",
                1000, "StandartKonto", "NOK", transaksjoner);

        when(sjekk.loggetInn()).thenReturn("56785678");
        when(repository.hentTransaksjoner(anyString(),anyString(),anyString())).thenReturn(konta);

        Konto resultat = bankController.hentTransaksjoner("","","");

        assertEquals(konta,resultat);
    }

    // tester hent Transaksjoner (ikkeLoggetInn)
    @Test
    public void hentTransaksjoner_IkkeLoggetInn(){
        when(sjekk.loggetInn()).thenReturn(null);

        Konto resultat = bankController.hentTransaksjoner("","","");

        assertNull(resultat);
    }

    @Test
    public void hentSaldi_LoggetInn_OK(){
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentSaldi(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentSaldi_IkkeLoggetInn(){
        when(sjekk.loggetInn()).thenReturn(null);

        List<Konto> resultat = bankController.hentSaldi();

        assertNull(resultat);
    }

    @Test
    public void registrerBetaling_LoggetInn_OK(){
        Transaksjon betalinger = new Transaksjon(1, "12341234",
                100.1, "05-02-2024", "SiO" , "1",
                "56785678");

        when(sjekk.loggetInn()).thenReturn("");
        when(repository.registrerBetaling(betalinger)).thenReturn("OK");

        String resultat = bankController.registrerBetaling(betalinger);

        assertEquals("OK", resultat);
    }

    @Test
    public void registrerBetaling_IkkeLoggetInn(){
        Transaksjon betalinger = new Transaksjon(1, "12341234",
                100.1, "05-02-2024", "SiO" , "1",
                "56785678");

        when(sjekk.loggetInn()).thenReturn(null);

        String resultat = bankController.registrerBetaling(betalinger);

        assertNull(resultat);
    }

    @Test
    public void hentBetalinger_LoggetInn(){
        List<Transaksjon> transaksjoner = new ArrayList<>();

        Transaksjon betaling_1 = new Transaksjon(1, "12341234",
                100.1, "05-02-2024", "SiO" , "1",
                "56785678");

        Transaksjon betaling_2 = new Transaksjon(2, "12341234",
                200.2, "05-02-2024", "if" , "1",
                "56785678");

        transaksjoner.add(betaling_1);
        transaksjoner.add(betaling_2);

        when(sjekk.loggetInn()).thenReturn("56785678");
        when(repository.hentBetalinger(anyString())).thenReturn(transaksjoner);

        List<Transaksjon> resultat = bankController.hentBetalinger();

        assertEquals(transaksjoner,resultat);
    }

    @Test
    public void hentBetalinger_IkkeLoggetInn(){
        when(sjekk.loggetInn()).thenReturn(null);

        List<Transaksjon> resultat = bankController.hentBetalinger();

        assertNull(resultat);
    }
}
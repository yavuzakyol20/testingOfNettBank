package oslomet.testing;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKontoController {
    @InjectMocks
    //denne skal testes
    private AdminKontoController adminKontoController;

    @Mock
    // denne skal Mock'es
    private AdminRepository adminRepository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    // hent alle Konto Informasjon

    //Tester hent alle Konto (Logger Inn-OK)
    @Test
    public void  hentAlleKonti_LoggetInn_OK(){
        //arrange
        List<Transaksjon> transaksjonList = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(1, "2024050124", 210, "2024-02-05", "Nord Trøndelag", "2",
                "32423895375");
        Transaksjon transaksjon2 = new Transaksjon(2, "2024050124", 250, "2024-02-05", "Nord Trøndelag", "2",
                "32423895375");

        transaksjonList.add(transaksjon1);
        transaksjonList.add(transaksjon2);

        List<Konto> kontoList = new ArrayList<>();

        Konto konti = new Konto("10109934567", "32423895375", 450, "Lønnskonto", "NOK", transaksjonList);

        kontoList.add(konti);

        when(sjekk.loggetInn()).thenReturn("10109934567");
        when(adminRepository.hentAlleKonti()).thenReturn(kontoList);
        //act
        List<Konto> result = adminKontoController.hentAlleKonti();
        //assert
        assertEquals(kontoList,result);
    }


    @Test
    public void hentAlleKonti_IkkeLoggetInn(){
        //arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        //assert
        assertNull(resultat);
    }

    @Test
    public void registrerKonto_LoggetInn_OK(){

        //arrange
        List<Transaksjon> transaksjonList = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(1, "2024050124", 210, "2024-02-05", "Nord Trøndelag", "2",
                "32423895375");
        Transaksjon transaksjon2 = new Transaksjon(1, "2024050124", 250, "2024-02-05", "Nord Trøndelag", "2",
                "32423895375");

        transaksjonList.add(transaksjon1);
        transaksjonList.add(transaksjon2);

        Konto konti = new Konto("10109934567",
                "32423895375", 450, "Lønnskonto", "NOK", transaksjonList);

        when(sjekk.loggetInn()).thenReturn("32423895375");
        when(adminRepository.registrerKonto(konti)).thenReturn("OK");

        // act
        String result = adminKontoController.registrerKonto(konti);

        // Assert
        assertEquals("OK",result);

    }

    //Tester registrer konto Ikke LoggetInn
    @Test
    public void registrerKonto_IkkeLoggetInn(){
        //arrange
        List<Transaksjon> transaksjonList = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(1, "2024050124", 210, "2024-02-05", "Nord Trøndelag", "2",
                "32423895375");
        Transaksjon transaksjon2 = new Transaksjon(1, "2024050124", 250, "2024-02-05", "Nord Trøndelag", "2",
                "32423895375");

        transaksjonList.add(transaksjon1);
        transaksjonList.add(transaksjon2);

        List<Konto> kontoList = new ArrayList<>();

        Konto konti = new Konto("10109934567", "32423895375", 450, "Lønnskonto", "NOK", transaksjonList);

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String result = adminKontoController.registrerKonto(konti);

        //assert
        assertEquals("Ikke innlogget",result);
    }

    @Test
    public void endreKonto_LoggetInn_OK(){
        //arrange
        List<Transaksjon> transaksjonList = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(1, "2024050124", 210, "2024-02-05", "Nord Trøndelag", "2",
                "32423895375");
        Transaksjon transaksjon2 = new Transaksjon(1, "2024050124", 250, "2024-02-05", "Nord Trøndelag", "2",
                "32423895375");

        transaksjonList.add(transaksjon1);
        transaksjonList.add(transaksjon2);

        List<Konto> kontoList = new ArrayList<>();

        Konto konti = new Konto("10109934567", "32423895375", 450, "Lønnskonto", "NOK", transaksjonList);

        when(sjekk.loggetInn()).thenReturn("32423895375");
        when(adminRepository.endreKonto(any(Konto.class))).thenReturn("OK");
        //act
        String result = adminKontoController.endreKonto(konti);

        //assert

        assertEquals("OK",result);
    }

    @Test
    public void endreKonto_IkkeLoggetInn(){
        //arrange
        List<Transaksjon> transaksjonList = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(1, "2024050124", 210, "2024-02-05", "Nord Trøndelag", "2",
                "32423895375");
        Transaksjon transaksjon2 = new Transaksjon(1, "2024050124", 250, "2024-02-05", "Nord Trøndelag", "2",
                "32423895375");

        transaksjonList.add(transaksjon1);
        transaksjonList.add(transaksjon2);

        List<Konto> kontoList = new ArrayList<>();

        Konto konti = new Konto("10109934567", "32423895375", 450, "Lønnskonto", "NOK", transaksjonList);

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String result = adminKontoController.endreKonto(konti);

        //assert
        assertEquals("Ikke innlogget",result);
    }

    //tester slett konto- LoggetInn_OK

    @Test
    public void slettKonto_LoggetInn_OK(){
        //arrange
        List<Transaksjon> transaksjonList = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(1, "2024050124", 210, "2024-02-05", "Nord Trøndelag", "2",
                "32423895375");
        Transaksjon transaksjon2 = new Transaksjon(1, "2024050124", 250, "2024-02-05", "Nord Trøndelag", "2",
                "32423895375");

        transaksjonList.add(transaksjon1);
        transaksjonList.add(transaksjon2);

        List<Konto> kontoList = new ArrayList<>();

        Konto konti = new Konto("10109934567", "32423895375", 450, "Lønnskonto", "NOK", transaksjonList);
        when(sjekk.loggetInn()).thenReturn("10109934567");
        when(adminRepository.slettKonto(anyString())).thenReturn("OK");

        //act
        String result =adminKontoController.slettKonto(konti.getKontonummer());

        //assert

        assertEquals("OK",result);

    }

    //tester slett konto (Ikke LoggetInn)

    @Test
    public void slettKonto_IkkeLoggetInn(){
        //arrange
        List<Transaksjon> transaksjonList = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(1, "2024050124", 210, "2024-02-05", "Nord Trøndelag", "2",
                "32423895375");
        Transaksjon transaksjon2 = new Transaksjon(1, "2024050124", 250, "2024-02-05", "Nord Trøndelag", "2",
                "32423895375");

        transaksjonList.add(transaksjon1);
        transaksjonList.add(transaksjon2);

        List<Konto> kontoList = new ArrayList<>();

        Konto konti = new Konto("10109934567", "32423895375", 450, "Lønnskonto", "NOK",transaksjonList);

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String result = adminKontoController.slettKonto(konti.getKontonummer());

        // assert

        assertEquals("Ikke innlogget",result);
    }



}
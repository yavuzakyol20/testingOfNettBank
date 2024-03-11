package oslomet.testing;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKundeController {

    @InjectMocks
//denne skal testes
    private AdminKundeController kundeController;

    @Mock
    //denne skal Mock'es
    private AdminRepository adminRepository;

    @Mock
    //denne skal Mock'es
    private Sikkerhet sjekk;

    //------hentAlleKunder------
    //tester hentAlleKUnder_LoggetInn
    @Test
    public void hentAlleKunder_LoggetInn_OK() {
        //arrange

        List<Kunde> kundeList = new ArrayList<>();

        Kunde kunde1 = new Kunde("0101950523", "Per",
                "Hansen", "Askerveien 22", "4455",
                "Oslo", "22223333", "heiPer");

        Kunde kunde2 = new Kunde("12129834456", "Ole",
                "Olsen", "Gamleveien 82", "5544",
                "Oslo", "12345678", "heiOle");

        kundeList.add(kunde1);
        kundeList.add(kunde2);

        when(sjekk.loggetInn()).thenReturn("0101950523");
        when(adminRepository.hentAlleKunder()).thenReturn(kundeList);

        //act
        List<Kunde> result = kundeController.hentAlle();
        //assert
        assertEquals(result, kundeList);
    }

    //tester hentAlleKunder_IkkeLoggetInn
    @Test
    public void hentAlleKunder_IkkeLoggetInn() {
        //arrenge
        when(sjekk.loggetInn()).thenReturn(null);
        //act
        List<Kunde> result = kundeController.hentAlle();
        //assert
        assertNull(result);
    }

    //-----LagreKunde-------
    //Tester lagreKunde_LoggetInn_OK

    @Test
    public void lagreKunde_LoggetInn_OK() {
        //arrange
        Kunde kunde1 = new Kunde("0101950523", "Per",
                "Hansen", "Askerveien 22", "4455",
                "Oslo", "22223333", "heiPer");

        when(sjekk.loggetInn()).thenReturn("0101950523");
        when(adminRepository.registrerKunde(kunde1)).thenReturn("OK");

        //act
        String result = kundeController.lagreKunde(kunde1);
        //assert
        assertEquals(result, "OK");
    }

    //Tester lagreKunde-IkkeLoggetInn
    @Test
    public void lagreKunde_IkkeLoggetInn() {
        //arrange
        Kunde kunde1 = new Kunde("0101950523", "Per",
                "Hansen", "Askerveien 22", "4455",
                "Oslo", "22223333", "heiPer");
        when(sjekk.loggetInn()).thenReturn(null);

        //act

        String result = kundeController.lagreKunde(kunde1);
        //assert
        assertEquals("Ikke logget inn", result);
    }


    //-------Endre Kunde------------
    //tester endreKunde_LoggetInn_OK

    @Test
    public void endreKunde_LoggetInn_OK(){

        //arrange
        Kunde kunde1 = new Kunde("0101950523", "Per",
                "Hansen", "Askerveien 22", "4455",
                "Oslo", "22223333", "heiPer");
        when(sjekk.loggetInn()).thenReturn("0101950523");
        when(adminRepository.endreKundeInfo(kunde1)).thenReturn("OK");

        //act
        String result = kundeController.endre(kunde1);

        //assert

        assertEquals(result,"OK");
    }

    //tester endreKunde_IkkeloggetInn

    @Test
    public void endreKunde_IkkeLoggetInn(){

        //arrange
        Kunde kunde1 = new Kunde("01019505423", "Per",
                "Hansen", "Askerveien 22", "4455",
                "Oslo", "22223333", "heiPer");


        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String result = kundeController.endre(kunde1);

        //assert

        assertEquals("Ikke logget inn",result);

    }

    //--------Slett kunde-----------
    //tester slettKunde_LoggetInn_OK
    @Test
    public void slettKunde_LoggetInn_Ok(){
        //arrange
        when(sjekk.loggetInn()).thenReturn("01019505423");
        when(adminRepository.slettKunde(anyString())).thenReturn("OK");

        //act
        String result = kundeController.slett("01019505423");

        //assert

        assertEquals("OK",result);
    }

    //tester slettKunde_IkkeLoggetInn
    @Test
    public void slettKunde_IkkeLoggetInn(){

        //arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String result = kundeController.slett("0101950523");

        //assert
        assertEquals("Ikke logget inn",result);

    }
}
package info.eecc.intellipack.epcis.extensions;

import info.eecc.commons.epcis.extensions.BasicExtension;
import info.eecc.intellipack.epcis.events.*;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 27.07.2021
 */
public class IntellipackExtension extends BasicExtension {

    public IntellipackExtension() {
        super(IntellipackNamespace.PREFIX);

        //https://epcisworkbench.gs1.org/ui/dataset/0746a0bc9ea741d883f18eea44991a08/show

        //Events:
        addFactory(ProduktionEvent1.getFactory());
        addFactory(WarenEinlagernEvent2.getFactory());
        addFactory(WarenausgangEvent3.getFactory());
        addFactory(WareneingangEvent4.getFactory());
        addFactory(VerkaufsflaecheImStoreEvent5.getFactory());
        addFactory(ImStoreImLagerEvent6.getFactory());
        addFactory(AbgelaufeneWareImLadenEvent7.getFactory());
        addFactory(MHDcheckEvent8.getFactory());
        addFactory(VerkaufKasseEvent9.getFactory());
        addFactory(MADcheckAbgelaufenEvent10.getFactory());
        addFactory(KonsumentVerbrauchtEvent11.getFactory());

        //Extensions:
        addField(Datenquelle.class);
        addField(Produktbezeichnung.class);
        addField(lotNumber.class);
        addField(Verpackungsdatum.class);
        addField(bestBeforeDate.class);
        addField(AufladewertTTI.class);
        addField(Zieltemperatur.class);
        addField(P1.class);
        addField(P2.class);
        addField(P3.class);
        addField(P4.class);
        addField(P5.class);
        addField(P6.class);
        addField(P7.class);
        addField(P8.class);
        addField(P9.class);
        addField(P10.class);
        addField(Zeitstempel.class);
        addField(Laenge.class);
        addField(Breite.class);
        addField(Rot.class);
        addField(Gruen.class);
        addField(Blau.class);
        addField(erwTemperatur.class);
        addField(RemainingLive.class);
        addField(BactCount.class);
    }
}

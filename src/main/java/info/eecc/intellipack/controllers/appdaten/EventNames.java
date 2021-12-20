package info.eecc.intellipack.controllers.appdaten;

import lombok.Data;

@Data
public class EventNames {

        private String event1 = "Produktion";

        private String event2= "Logistik: Einlagern";

        private String event3= "Logistik: Warenausgang";

        private String event4= "Logistik: Wareneingang ";

        private String event5= "Handel: Einlagern im Backroom";

        private String event6= "Handel: In den Verkauf bringen";

        private String event7 = "Handel: Ware ist abgelaufen ";

        private String event8 = "Handel: Prüfung der Restlaufzeit";

        private String event9 = "Handel: Verkauf an der Kasse";

        private String event10 = "Konsument: Restlaufzeit bestimmen";

        private String event11 = "Konsument:  Ware verbrauchen";

}

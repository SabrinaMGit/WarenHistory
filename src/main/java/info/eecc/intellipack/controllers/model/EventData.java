package info.eecc.intellipack.controllers.model;

import lombok.Data;

@Data
public class EventData {
    private String zeitstempel;

    private Double laenge;

    private Double breite;

    private Integer rot;

    private Integer grün;

    private Integer blau;

    private Integer erwarteteTemperatur;

    private Integer resthaltbarkeit;
}

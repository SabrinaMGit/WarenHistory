package info.eecc.intellipack.controllers.appdaten;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 12.08.2021
 */
@Entity
@Data
public class ProductDataStrings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    //@ColumnDefault("'Wolf Qualitätskontrolle'")
    //@Column(columnDefinition = "varchar(255) default 'John Snow'")
    private String datenquelle;

    //@ColumnDefault("'01/04014116000003/21/0221T4YE'")
    private String digitallink;

    //@ColumnDefault("'Original Thueringer Rostbratwurst'")
    private String produktbezeichnung;

    //@ColumnDefault("4711")
    private String produktionscharge;

    //@ColumnDefault("'16.06.2021'")
    private String verpackungsdatum;

    //@CreationTimestamp
    private String mhd;

    //@ColumnDefault("11")
    private String aufladewertTTI;

    //@ColumnDefault("11")
    private String zieltemperatur;

    //@ColumnDefault("11")
    private String p1;

    //@ColumnDefault("11")
    private String p2;

    //@ColumnDefault("11")
    private String p3;

    //@ColumnDefault("11")
    private String p4;

    //@ColumnDefault("11")
    private String p5;

    //@ColumnDefault("11")
    private String p6;

    //@ColumnDefault("11")
    private String p7;

    //@ColumnDefault("11")
    private String p8;

    //@ColumnDefault("11")
    private String p9;

    //@ColumnDefault("11")
    private String p10;

    //@ColumnDefault("'20210301_204823'")
    private String zeitstempel;

    //@ColumnDefault("6.906")
    private String laenge;

    //@ColumnDefault("50.834")
    private String breite;

    //@ColumnDefault("11")
    private String rot;

    //@ColumnDefault("11")
    private String grün;

    //@ColumnDefault("11")
    private String blau;

    //@ColumnDefault("11")
    private String erwarteteTemperatur;

    //@ColumnDefault("11")
    private String resthaltbarkeit;
}

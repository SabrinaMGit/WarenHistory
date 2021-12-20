package info.eecc.intellipack.controllers.status;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 21.06.2021
 */
@Entity
@Data
public class ProductData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //@ColumnDefault("'Wolf Qualitätskontrolle'")
    //@Column(columnDefinition = "varchar(255) default 'John Snow'")
    private String datenquelle;

    //@ColumnDefault("'01/04014116000003/21/0221T4YE'")
    private String sgtin;

    //@ColumnDefault("'Original Thueringer Rostbratwurst'")
    private String produktbezeichnung;

    //@ColumnDefault("4711")
    private Integer produktionscharge;

    //@ColumnDefault("'16.06.2021'")
    private LocalDate verpackungsdatum;

    //@CreationTimestamp
    private LocalDate mhd;

    //@ColumnDefault("11")
    private Integer aufladewertTTI;

    //@ColumnDefault("11")
    private Integer zieltemperatur;

    //@ColumnDefault("11")
    private Integer p1;

    //@ColumnDefault("11")
    private Integer p2;

    //@ColumnDefault("11")
    private Integer p3;

    //@ColumnDefault("11")
    private Integer p4;

    //@ColumnDefault("11")
    private Integer p5;

    //@ColumnDefault("11")
    private Integer p6;

    //@ColumnDefault("11")
    private Integer p7;

    //@ColumnDefault("11")
    private Integer p8;

    //@ColumnDefault("11")
    private Integer p9;

    //@ColumnDefault("11")
    private Integer p10;

    //@ColumnDefault("'20210301_204823'")
    private String zeitstempel;

    //@ColumnDefault("6.906")
    private Double laenge;

    //@ColumnDefault("50.834")
    private Double breite;

    //@ColumnDefault("11")
    private Integer rot;

    //@ColumnDefault("11")
    private Integer grün;

    //@ColumnDefault("11")
    private Integer blau;

    //@ColumnDefault("11")
    private Integer erwarteteTemperatur;

    //@ColumnDefault("11")
    private Integer resthaltbarkeit;
}

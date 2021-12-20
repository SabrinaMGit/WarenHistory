package info.eecc.intellipack.controllers.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ILMD {
    private Integer produktionscharge;

    //@ColumnDefault("'16.06.2021'")
    private LocalDate verpackungsdatum;

    //@CreationTimestamp
    private LocalDate mhd;

    //@ColumnDefault("11")
    private Integer aufladewertTTI;
}

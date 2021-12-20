package info.eecc.intellipack.controllers.appdaten;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventTypeModel {
    private String action;
    private String bizStep;
    private String disposition;
}

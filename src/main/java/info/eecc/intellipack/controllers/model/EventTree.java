package info.eecc.intellipack.controllers.model;

import info.eecc.commons.epcis.core.EpcisEvent;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// represents the tree of events in the supply "chain" for a given epc
@Getter
@Setter
@RequiredArgsConstructor
public class EventTree {

    // epc that is used in the nextEvent / ultimately queried
    @NonNull
    private String epc;

    private List<Produktstammdaten> produktstammdaten;

    private List<ILMD> ilmd;

    private List<EventData> eventData;

    // the current event
    private List<EpcisEvent> events;


    public static EventTree of(String epc, List<EpcisEvent> events) {
        EventTree root = new EventTree(epc);
        root.setEvents(events);
        return root;
    }

    protected String toStringWithoutPointers() {
        return "EventTree{epc='" +
            this.getEpc() +
            "', event=" +
            getEvents() +
            "}";
    }

    // next + previous and creationEvent are expected to form cycles
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("EventTree{epc='")
            .append(getEpc())
            .append("', event=")
            .append(getEvents())
            .append(", previousEvents=[");
        builder.append(", creatingEvent=");
        builder.append("}");
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventTree eventTree = (EventTree) o;
        return toString().equals(eventTree.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(toString());
    }
}

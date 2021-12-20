package info.eecc.intellipack.controllers;

import info.eecc.commons.epcis.core.EpcisEvent;
import info.eecc.commons.epcis.writer.EpcisModelXmlWriter;
import info.eecc.commons.gs1.Gtin;
import info.eecc.commons.gs1.epc.Sgtin;
import info.eecc.intellipack.controllers.model.EventTree;
import info.eecc.intellipack.epcat.EpcatQuerySender;
import info.eecc.intellipack.epcis.extensions.IntellipackExtension;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping
public class AccessController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessController.class);

    @Autowired
    private EpcatQuerySender epcatQuerySender;

    @GetMapping(path = "/api/eventId/{eventId}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getMyInputData(@PathVariable String eventId) throws NotFoundException {
        Optional<EpcisEvent> eventOptional = epcatQuerySender.eeccEventIdQuery(eventId);
        if (eventOptional.isEmpty()) {
            throw new NotFoundException("No event with id " + eventId + " found.");
        }
        return ResponseEntity.ok(eventOptional.get().toXml(EpcisModelXmlWriter.withExtensions(new IntellipackExtension())));
    }

    @GetMapping(path = {"/api/access/digitalLink"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventTree> getEpcatDataViaSgtin(
            @RequestParam(value = "sgtin") String sgtin) throws NotFoundException {
        return ResponseEntity.ok(gatherEventTree(sgtin));
    }

    @GetMapping(path = {"/admin/api/access/all"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EpcisEvent>> getEpcatData() throws NotFoundException {
        return ResponseEntity.ok(gatherEventTree());
    }

    public EventTree gatherEventTree(String sgtin) throws NotFoundException {
        List<EpcisEvent> intellipackEvents;

        intellipackEvents = new ArrayList<>(epcatQuerySender.queryEvents(sgtin, false)); // now mutable
        if (intellipackEvents.isEmpty()) {
            throw new NotFoundException("No relevant intellipack events found for epc " + sgtin);
        }
        EventTree root = new EventTree(sgtin);
        root.setEvents(intellipackEvents);
        // intellipackEvents.remove(0);

        return root;
    }

    public List<EpcisEvent> gatherEventTree() throws NotFoundException {
        List<EpcisEvent> intellipackEvents;

        intellipackEvents = new ArrayList<>(epcatQuerySender.queryEvents(null, true)); // now mutable
        if (intellipackEvents.isEmpty()) {
            throw new NotFoundException("No relevant intellipack events found for epc ");
        }

        return intellipackEvents;
    }

}

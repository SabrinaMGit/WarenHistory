package info.eecc.intellipack.controllers;

import info.eecc.commons.epcis.core.EpcisEvent;
import info.eecc.commons.epcis.core.EventContainer;
import info.eecc.commons.epcis.parser.EpcisModelParseException;
import info.eecc.commons.epcis.parser.EpcisModelParser;
import info.eecc.intellipack.controllers.model.GenericApiResponse;
import info.eecc.intellipack.exceptions.*;
import info.eecc.intellipack.epcat.EpcatEventSender;
import info.eecc.intellipack.epcis.extensions.IntellipackExtension;
import info.eecc.intellipack.logging.TraceContext;
import info.eecc.intellipack.logging.TraceId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 27.07.2021
 */
@RestController
@RequestMapping(path = "/api")
public class CaptureController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CaptureController.class);

    private final EpcatEventSender eventSender;

    // No dependency injection needed / not useful atm:
    private final EpcisModelParser parser = EpcisModelParser.withExtensions(new IntellipackExtension()); //Here withExtensions

    public CaptureController(EpcatEventSender eventSender) {
        this.eventSender = eventSender;
    }

    /**
     *
     * @param epcisDocument
     * @param request
     * @return
     */
    @PostMapping(path = "capture", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericApiResponse> addEvent(
            @RequestBody String epcisDocument,
            HttpServletRequest request) {
        LOGGER.debug("capturing '{}'", epcisDocument);
        String result = TraceContext.use(ctx -> {
            TraceId traceId = TraceId.random();
            try {
                return process(epcisDocument, traceId);
            } catch (EpcisModelParseException e) {
                throw new BadRequestException("Error parsing / validating event: " + e.getMessage());
            }
        });
        return GenericApiResponse.buildResponse(HttpStatus.OK, result, request.getRequestURI(), "", LocalDateTime.now());
    }

    public String addEventWithoutMapping(String epcisDocument) {
        LOGGER.debug("capturing '{}'", epcisDocument);
        return TraceContext.use(ctx -> {
            TraceId traceId = TraceId.random();
            try {
                return process(epcisDocument, traceId);
            } catch (EpcisModelParseException e) {
                throw new BadRequestException("Error parsing / validating event: " + e.getMessage());
            }
        });
    }

    /**
     *
     * @param eventContainerAsString
     * @param traceId
     * @return
     */
    private String process(String eventContainerAsString, TraceId traceId) {
        Instant begin = Instant.now();
        Instant stepBegin = Instant.now();
        LOGGER.info("processing xml [{}] ", traceId.getAsArgument());
        EventContainer eventContainer;
        try {
            eventContainer = EventContainer.parse(parser, eventContainerAsString);
        } catch (Exception e) {
            LOGGER.error("Error in xml processing {} [{}]", e, traceId.getAsArgument());
            throw new BadRequestException("Error parsing XML: " + e.getMessage());
        }
        LOGGER.info("{} events received. [{}] ", eventContainer.cntEvents(), traceId.getAsArgument());
        LOGGER.debug("document parsed after: {}ms [{}] ", Duration.between(stepBegin, Instant.now()).toMillis(), traceId.getAsArgument());
        stepBegin = Instant.now();
        if (eventContainer.cntEvents() < 1) {
            LOGGER.info("Empty event container [{}] ", traceId.getAsArgument());
            throw new BadRequestException("Empty event container");
        }
        LOGGER.debug("event processed by all handlers after {}ms [{}] ", Duration.between(stepBegin, Instant.now()).toMillis(), traceId.getAsArgument());
        String epcatResponse = epcatSend(traceId, eventContainer.eventList());

        LOGGER.info("event process completed, total run time: {}ms [{}] ", Duration.between(begin, Instant.now()).toMillis(), traceId.getAsArgument());
        return epcatResponse;
    }

    /**
     *
     * @param traceId
     * @param epcisEventList
     * @return
     */
    // returns epcats answer on success, else throws -> 500
    private String epcatSend(TraceId traceId, List<EpcisEvent> epcisEventList) {
        Instant stepBegin = Instant.now();
        try {
            ResponseEntity<String> responseEntity = eventSender.postEvents(epcisEventList, traceId.getValue());
            LOGGER.debug("event sent to epcat after {}ms. [{}] ", Duration.between(stepBegin, Instant.now()).toMillis(), traceId.getAsArgument());
            if (!responseEntity.getStatusCode().is2xxSuccessful()) {
                throw new ResponseStatusException(responseEntity.getStatusCode(), "Error storing events in EPCIS Repository: " + responseEntity.getBody());
            }

            return responseEntity.getBody();
        } catch (Exception ex) {
            LOGGER.info("ERROR");
            throw new InternalServerErrorException("Could not store events in EPCIS Repository: " + ex.getMessage());
        }
    }
}

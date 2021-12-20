package info.eecc.intellipack.epcat;

import epcglobal.epcis.ActionType;
import epcglobal.epcis.query.Poll;
import epcglobal.epcis.query.QueryResults;
import info.eecc.commons.epcis.cbv.BizStep;
import info.eecc.commons.epcis.cbv.Disposition;
import info.eecc.commons.epcis.core.EpcisEvent;
import info.eecc.commons.epcis.extensions.eecc.EventId;
import info.eecc.commons.epcis.parser.EpcisModelParser;
import info.eecc.commons.epcis.query.seq.*;
import info.eecc.commons.epcis.writer.EpcisModelXmlWriter;
import info.eecc.intellipack.epcis.handlers.IntellipackEventsHandlingService;
import info.eecc.intellipack.epcis.extensions.IntellipackExtension;
import info.eecc.intellipack.logging.TraceContext;
import info.eecc.intellipack.logging.TraceId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.WebServiceIOException;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Developer: Sabrina Meier
 * Company: EECC
 * Created: 27.07.2021
 */
public class EpcatQuerySender {

    private static final Logger LOGGER = LoggerFactory.getLogger(EpcatQuerySender.class);

    // wired
    private final EpcatProperties epcatProperties;
    private final IntellipackEventsHandlingService intellipackEventsHandlingService;

    // created from new IntellipackExtension
    private final EpcisModelXmlWriter epcisModelXmlWriter;
    private final EpcisModelParser epcisModelParser;
    // created from epcatProperties in constructor
    private final WebServiceTemplate webServiceTemplate;

    public EpcatQuerySender(EpcatProperties epcatProperties, IntellipackEventsHandlingService intellipackEventsHandlingService, WebServiceTemplate webServiceTemplate) {
        this.intellipackEventsHandlingService = intellipackEventsHandlingService;
        this.webServiceTemplate = webServiceTemplate;
        this.epcatProperties = epcatProperties;


        this.epcisModelXmlWriter = EpcisModelXmlWriter.withExtensions(new IntellipackExtension()); //Here withExtensions
        this.epcisModelParser = EpcisModelParser.withExtensions(new IntellipackExtension()); //Here withExtensions

    }

    public List<EpcisEvent> queryEvents(String sgtin, Boolean getALl) {
        List<EpcisEvent> events;
        if(!getALl){
            LOGGER.info("QueryEpcat SgtinClass");
            events = epcatEpcClassQuery(sgtin);
            System.out.println("events = " + events);
        } else {
            LOGGER.info("QueryEpcat All");
            events = epcatEpcClassGetAll();
        }

        if (events.isEmpty()) {
            LOGGER.debug("No events found for epc {}",sgtin);
            return Collections.emptyList();
        }

        return events;
    }

    private List<EpcisEvent> convertToIntellipackEvents(List<EpcisEvent> events) {
        List<EpcisEvent> intellipackEvents = new ArrayList<>();


        for (EpcisEvent event : events) {
            EpcisEvent intellipackEvent = intellipackEventsHandlingService.convertEvent(event);
            if (intellipackEvent == null) {
                LOGGER.error("FIXME: Could not determine intellipack event type of an event {} which is stored in EPCAT", event);
            } else {
                intellipackEvents.add(intellipackEvent);
            }
        }
        return intellipackEvents;
    }

    /**
     * Wire to EPCAT.
     * <p>
     * Query all events involving the given EPC Class.
     * Returned in desc RecordTime Order.
     */
    private List<EpcisEvent> epcatEpcClassGetAll() {
        return TraceContext.use(ctx -> {
            try {
                SimpleEventQuery simpleEventQuery = SimpleEventQuery.of(
                        OrderByParameter.byRecordTime(),
                        OrderDirectionParameter.desc());
                return executeSimpleEventQuery(simpleEventQuery).collect(Collectors.toList());
            } catch (Exception e) {
                LOGGER.error("Error occurred in EPCAT Query");
            }
            return List.of();
        });
    }


    /**
     * Wire to EPCAT.
     * <p>
     * Query all events involving the given EPC Class.
     * Returned in desc RecordTime Order.
     */
    private List<EpcisEvent> epcatEpcClassQuery(String sgtin) {
        AnyEpcParameter queryParameter = new AnyEpcParameter(sgtin);
        TraceId traceId = TraceId.fromValue(queryParameter.getValue().toString());
        return TraceContext.use(ctx -> {
            Instant stepBegin = Instant.now();
            LOGGER.info("Incoming request for intellipack event [{}] ", traceId.getAsArgument());
            try {
                SimpleEventQuery simpleEventQuery = SimpleEventQuery.of(
                    OrderByParameter.byRecordTime(),
                    OrderDirectionParameter.desc(),
                        queryParameter);
                List<EpcisEvent> epcisEvents = executeSimpleEventQuery(simpleEventQuery).collect(Collectors.toList());
                LOGGER.debug("EPCAT Query Duration: {}ms [{}] ", Duration.between(stepBegin, Instant.now()).toMillis(), traceId.getAsArgument());
                return epcisEvents;
            } catch (Exception e) {
                LOGGER.error("Error occurred in EPCAT Query{}: {} [{}] ", queryParameter, e, traceId.getAsArgument());
            }
            return List.of();
        });
    }

    /**
     * Wire to EPCAT.
     * <p>
     * Query all events involving the given EPC Class.
     * Returned in desc RecordTime Order.
     */
    private List<EpcisEvent> epcatEpcClassQueryForActionBizDispo(ActionType action, BizStep bizStep, Disposition disposition) {
        ActionParameter actionParameter = new ActionParameter(action);
        BizStepParameter bizStepParameter = new BizStepParameter(bizStep);
        DispositionParameter dispositionParameter = new DispositionParameter(disposition);
        TraceId traceId = TraceId.fromValue(actionParameter.getValue().toString());
        return TraceContext.use(ctx -> {
            Instant stepBegin = Instant.now();
            LOGGER.info("Incoming request for intellipack event [{}] ", traceId.getAsArgument());
            try {
                SimpleEventQuery simpleEventQuery = SimpleEventQuery.of(
                        OrderByParameter.byRecordTime(),
                        OrderDirectionParameter.desc(),
                        actionParameter,
                        bizStepParameter,
                        dispositionParameter);
                List<EpcisEvent> epcisEvents = executeSimpleEventQuery(simpleEventQuery).collect(Collectors.toList());
                LOGGER.debug("EPCAT Query Duration: {}ms [{}] ", Duration.between(stepBegin, Instant.now()).toMillis(), traceId.getAsArgument());
                return epcisEvents;
            } catch (Exception e) {
                LOGGER.error("Error occurred in EPCAT Query{}: {} [{}] ", actionParameter, e, traceId.getAsArgument());
            }
            return List.of();
        });
    }

    /**
     * Query for the event with the given EECC event ID.
     */
    public Optional<EpcisEvent> eeccEventIdQuery(String eventId) {
        TraceId traceId = TraceId.fromValue(eventId);
        return TraceContext.use(ctx -> {
            Instant stepBegin = Instant.now();
            LOGGER.info("Incoming request for intellipack event [{}] ", traceId.getAsArgument());
            SimpleEventQuery simpleEventQuery = SimpleEventQuery.of(new EventId(eventId));
            try {
                List<EpcisEvent> epcisEvents = executeSimpleEventQuery(simpleEventQuery).collect(Collectors.toList());
                LOGGER.debug("EPCAT Query Duration: {}ms [{}] ", Duration.between(stepBegin, Instant.now()).toMillis(), traceId.getAsArgument());
                epcisEvents = convertToIntellipackEvents(epcisEvents);
                if (epcisEvents.isEmpty()) {
                    return Optional.empty();
                }
                if (epcisEvents.size() > 1) {
                    LOGGER.error("FIXME: Got more than one event when querying by EECC event id " + eventId);
                }
                return Optional.of(epcisEvents.get(0));
            } catch (Exception e) {
                LOGGER.error("Error occurred in EPCAT Query: {} [{}] ", e, traceId.getAsArgument());
            }
            return Optional.empty();
        });
    }

    private Stream<EpcisEvent> executeSimpleEventQuery(SimpleEventQuery seq) {

        Poll poll = new Poll();
        poll.setQueryName("SimpleEventQuery");
        poll.setParams(seq.getQueryParams());

        String body = epcisModelXmlWriter.writeToString(poll);

        StringWriter sw = new StringWriter();
        StreamSource source = new StreamSource(new StringReader(body));
        StreamResult result = new StreamResult(sw);

        String resultBody = "";
        try {
            webServiceTemplate.sendSourceAndReceiveToResult(source, result);
            resultBody = sw.toString();
        } catch (WebServiceIOException e) {
            LOGGER.error("Timeout when sending query to epcat", e);
        }

        QueryResults queryResults = epcisModelParser.parseQueryResults(resultBody);

        return queryResults.events();
    }


}

package com.example.service;

import com.example.model.EventData;
import com.example.model.State;
import com.example.persistence.EventEntity;
import com.example.persistence.HsqldbRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class AlertService {

    private static final Logger log = LoggerFactory.getLogger(AlertService.class);

    private final HsqldbRepository dbRepo;

    private final Map<String, EventData> id2EventData = new HashMap<>();

    // could be externalized in properties
    private final Long durationThreshold;

    public AlertService(HsqldbRepository dbRepo, Long durationThreshold) {
        this.dbRepo = dbRepo;
        this.durationThreshold = durationThreshold;
    }

    public void process(EventData eventData) {
        EventData prevEvent = id2EventData.get(eventData.getId());

        if (prevEvent != null) {
            if (eventData.getState() == State.FINISHED) {
                Long duration = eventData.getTimestamp() - prevEvent.getTimestamp();
                Boolean alert = duration > durationThreshold;

                if(alert) {
                    log.warn("Event '{}' took {}ms", eventData.getId(), duration);
                }

                EventEntity entity = new EventEntity(eventData.getId(), duration, alert, eventData.getType(), eventData.getHost());
                dbRepo.insert(entity);

                // remove it to save memory
                id2EventData.remove(eventData.getId());
            }
        } else {
            // just add it to the event-tracking map
            id2EventData.put(eventData.getId(), eventData);
        }
    }
}

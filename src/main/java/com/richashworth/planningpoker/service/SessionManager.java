package com.richashworth.planningpoker.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.richashworth.planningpoker.model.Estimate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Rich Ashworth on 09/04/2016.
 */
@Component
public class SessionManager {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ListMultimap<Long, Estimate> sessionsMap = ArrayListMultimap.create();
    private final AtomicLong sessionSequence = new AtomicLong(0L);

    public boolean isSessionActive(Long sessionId) {
        return sessionId <= sessionSequence.get();
    }

    public Long createSession() {
        return sessionSequence.incrementAndGet();
    }

    public void registerEstimate(Long sessionID, Estimate estimate) {
        sessionsMap.put(sessionID, estimate);
    }

    public List<Estimate> getResults(Long sessionId) {
        return sessionsMap.get(sessionId);
    }

    public void clearSessions() {
        logger.info("Clearing all sessions");
        sessionsMap.clear();
        sessionSequence.set(0L);
    }

    public void resetSession(Long sessionId) {
        sessionsMap.removeAll(sessionId);
    }

}

package com.example.service;

import com.example.model.EventData;
import com.example.model.State;
import com.example.persistence.EventEntity;
import com.example.persistence.HsqldbRepository;
import org.mockito.Mockito;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AlertServiceTest {

    static Long DURATION_THRESHOLD = 4L;

    @Test
    void testNoFinishedEvents() {
        HsqldbRepository dbRepoMock = Mockito.mock(HsqldbRepository.class);
        AlertService alertService = new AlertService(dbRepoMock, DURATION_THRESHOLD);

        EventData ed1 = new EventData("a", State.STARTED, 1L, null, "host");
        EventData ed2 = new EventData("b", State.STARTED, 2L, null, "host");
        EventData ed3 = new EventData("c", State.STARTED, 3L, null, "host");

        alertService.process(ed1);
        alertService.process(ed2);
        alertService.process(ed3);

        verify(dbRepoMock, times(0)).insert(any());
    }

    @Test
    void testSingleFinishedEventNoAlert() {
        HsqldbRepository dbRepoMock = Mockito.mock(HsqldbRepository.class);
        AlertService alertService = new AlertService(dbRepoMock, DURATION_THRESHOLD);

        EventData ed1 = new EventData("a", State.STARTED, 1L, null, "host");
        EventData ed2 = new EventData("b", State.STARTED, 2L, null, "host");
        EventData ed3 = new EventData("a", State.FINISHED, 3L, null, "host");

        alertService.process(ed1);
        alertService.process(ed2);
        alertService.process(ed3);

        verify(dbRepoMock).insert(new EventEntity("a", 2L, false, null, "host"));
        verifyNoMoreInteractions(dbRepoMock);
    }

    @Test
    void testSingleFinishedEventWithAlert() {
        HsqldbRepository dbRepoMock = Mockito.mock(HsqldbRepository.class);
        AlertService alertService = new AlertService(dbRepoMock, DURATION_THRESHOLD);

        EventData ed1 = new EventData("a", State.STARTED, 1L, null, "host1");
        EventData ed2 = new EventData("b", State.STARTED, 2L, null, "host2");
        EventData ed3 = new EventData("a", State.FINISHED, 1L + DURATION_THRESHOLD + 1L, null, "host1");

        alertService.process(ed1);
        alertService.process(ed2);
        alertService.process(ed3);

        verify(dbRepoMock).insert(new EventEntity("a", DURATION_THRESHOLD + 1L, true, null, "host1"));
        verifyNoMoreInteractions(dbRepoMock);
    }
}
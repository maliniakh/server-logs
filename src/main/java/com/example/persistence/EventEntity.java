package com.example.persistence;

import com.example.model.Type;

import java.util.Objects;

public class EventEntity {
    private final String id;
    private final Long duration;
    private final Boolean alert;
    private final Type type;
    private final String host;

    public EventEntity(String id, Long duration, Boolean alert, Type type, String host) {
        this.id = id;
        this.duration = duration;
        this.alert = alert;
        this.type = type;
        this.host = host;
    }

    public String getId() {
        return id;
    }

    public Long getDuration() {
        return duration;
    }

    public Boolean getAlert() {
        return alert;
    }

    public Type getType() {
        return type;
    }

    public String getHost() {
        return host;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventEntity that = (EventEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(duration, that.duration) && Objects.equals(alert, that.alert) && type == that.type && Objects.equals(host, that.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, duration, alert, type, host);
    }
}

package com.example.model;

public class EventData {
    private final String id;
    private final State state;
    private final Long timestamp;
    private final Type type;
    private final String host;

    public EventData(String id, State state, Long timestamp, Type type, String host) {
        this.id = id;
        this.state = state;
        this.timestamp = timestamp;
        this.type = type;
        this.host = host;
    }

    public String getId() {
        return id;
    }

    public State getState() {
        return state;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Type getType() {
        return type;
    }

    public String getHost() {
        return host;
    }

    @Override
    public String toString() {
        return "EventData{" +
                "id='" + id + '\'' +
                ", state=" + state +
                ", timestamp=" + timestamp +
                ", type=" + type +
                ", host='" + host + '\'' +
                '}';
    }
}

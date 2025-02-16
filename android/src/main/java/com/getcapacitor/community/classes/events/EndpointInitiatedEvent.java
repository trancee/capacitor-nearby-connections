package com.getcapacitor.community.classes.events;

import androidx.annotation.NonNull;
import com.getcapacitor.community.classes.Connection;
import com.getcapacitor.community.classes.Endpoint;

public class EndpointInitiatedEvent extends EndpointEvent {

    @NonNull
    Connection connection;

    public EndpointInitiatedEvent(@NonNull Endpoint endpoint, @NonNull Connection connection) {
        super(endpoint);
        this.connection = connection;
    }
}

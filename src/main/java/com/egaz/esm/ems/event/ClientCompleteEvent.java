package com.egaz.esm.ems.event;

import com.egaz.esm.ems.clients.Client;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class ClientCompleteEvent extends ApplicationEvent {
    private Client client;
    private String applicationUrl;

    public ClientCompleteEvent(Client client, String applicationUrl) {
        super(client);
        this.client = client;
        this.applicationUrl = applicationUrl;
    }
}

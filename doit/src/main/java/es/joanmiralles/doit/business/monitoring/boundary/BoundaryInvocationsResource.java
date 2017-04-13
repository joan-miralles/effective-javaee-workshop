package es.joanmiralles.doit.business.monitoring.boundary;

import es.joanmiralles.doit.business.monitoring.entity.CallEvent;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

@Stateless
@Path("boundary-invocations")
public class BoundaryInvocationsResource {

    @Inject
    MonitorSink ms;

    @GET
    public List<CallEvent> expose() {
        return this.ms.getRecentEvents();
    }
}

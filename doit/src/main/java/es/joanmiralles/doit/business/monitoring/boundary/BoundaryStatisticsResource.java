package es.joanmiralles.doit.business.monitoring.boundary;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.LongSummaryStatistics;

@Path("boundary-statistics")
public class BoundaryStatisticsResource {

    @Inject
    MonitorSink ms;

    @GET
    public JsonObject statistics() {
        LongSummaryStatistics statistics = this.ms.getStatistics();
        return Json.createObjectBuilder().
                add("average-duration", statistics.getAverage()).
                add("invocation-count", statistics.getCount()).
                add("min-duration", statistics.getMin()).
                add("max-duration", statistics.getMax()).
                build();
    }
}

package es.joanmiralles.doit.business.logging.boundary;

@FunctionalInterface
public interface LogSink {

    void log(String msg);
}

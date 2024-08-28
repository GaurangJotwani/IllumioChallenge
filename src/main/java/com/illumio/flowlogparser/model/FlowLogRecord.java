package main.java.com.illumio.flowlogparser.model;

public abstract class FlowLogRecord {
  public abstract Integer getDstPort();
  public abstract Integer getProtocol();
}

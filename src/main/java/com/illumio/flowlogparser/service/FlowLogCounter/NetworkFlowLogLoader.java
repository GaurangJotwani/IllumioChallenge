package main.java.com.illumio.flowlogparser.service.FlowLogCounter;

import java.util.List;

import main.java.com.illumio.flowlogparser.model.FlowLogRecord;
import main.java.com.illumio.flowlogparser.utility.Parser;

public class NetworkFlowLogLoader {
  private List<? extends FlowLogRecord> entries;
  private final String networkFlowLogFIlePath;
  private final Parser parser;
  private final Class<? extends FlowLogRecord> type;

  public NetworkFlowLogLoader(String networkFlowLogFIlePath, Parser parser, Class<? extends FlowLogRecord> type) throws Exception {
    this.networkFlowLogFIlePath = networkFlowLogFIlePath;
    this.parser = parser;
    this.type = type;
    loadNetworkLogEntries();
  }

  private void loadNetworkLogEntries() throws Exception {
    entries =   parser.parse(networkFlowLogFIlePath, type);
  }

  public List<? extends FlowLogRecord> getEntries() {
    return entries;
  }
}

package main.java.com.illumio.flowlogparser.service.FlowLogCounter;
import java.util.List;
import main.java.com.illumio.flowlogparser.model.LookupEntry;
import main.java.com.illumio.flowlogparser.utility.Parser;

public class LookupTable {
  private List<LookupEntry> entries;
  private final Parser parser;
  private final String lookUpTableFilePath;

  public LookupTable(String lookUpTableFilePath, Parser parser) throws Exception {
    this.lookUpTableFilePath = lookUpTableFilePath;
    this.parser = parser;
    loadNetworkFlowLogEntries();
  }
  private void loadNetworkFlowLogEntries() throws Exception {
    entries = parser.parse(lookUpTableFilePath, LookupEntry.class);
  }

  public List<LookupEntry> getEntries() {
    return entries;
  }
}

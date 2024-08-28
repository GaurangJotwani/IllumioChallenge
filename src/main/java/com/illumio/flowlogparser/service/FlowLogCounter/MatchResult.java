package main.java.com.illumio.flowlogparser.service.FlowLogCounter;

import java.util.HashMap;

class MatchResult {
  private HashMap<String, Integer> tagMatchCounts;
  private HashMap<String, Integer> protocolMatchCounts;

  public MatchResult(HashMap<String, Integer> tagMatchCounts, HashMap<String, Integer> protocolMatchCounts) {
    this.tagMatchCounts = tagMatchCounts;
    this.protocolMatchCounts = protocolMatchCounts;
  }

  public HashMap<String, Integer> getTagMatchCounts() {
    return tagMatchCounts;
  }

  public HashMap<String, Integer> getProtocolMatchCounts() {
    return protocolMatchCounts;
  }
}

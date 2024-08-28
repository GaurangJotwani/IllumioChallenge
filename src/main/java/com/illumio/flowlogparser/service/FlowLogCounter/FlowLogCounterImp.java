package main.java.com.illumio.flowlogparser.service.FlowLogCounter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FlowLogCounterImp implements FlowLogCounter {
  private final LookupTable lookupTable;
  private final NetworkFlowLogLoader networkFlowLogLoader;

  public FlowLogCounterImp(LookupTable lookupTable,
                           NetworkFlowLogLoader networkFlowLogLoader) {
    this.lookupTable = lookupTable;
    this.networkFlowLogLoader = networkFlowLogLoader;
  }

  @Override
  public void outputMatchCountFile(String lookUpSrcFilePath, String networkFlowLogFilePath,
                                   String destinationFilePath) {
    try {
      var matchingService = new MatchingService(lookupTable, networkFlowLogLoader);
      var matchResult = matchingService.matchRecords();
      writeTagCountsToFile(matchResult.getTagMatchCounts(), destinationFilePath, "Tag Counts:",
              "Tag,Count");
      writeTagCountsToFile(matchResult.getProtocolMatchCounts(), destinationFilePath,
              "Port/Protocol Combination Counts:", " Port,Protocol,Count ");
    } catch (Exception e) {
      throw new RuntimeException("Failed to output match count file", e);
    }
  }

  private void writeTagCountsToFile(Map<String, Integer> map, String filePath,
                                    String header, String subHeader) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
      writer.write(header);
      writer.newLine();
      writer.write(subHeader);
      writer.newLine();
      map.forEach((key, val) -> {
        try {
          writer.write(key + "," + val);
          writer.newLine();
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
      writer.newLine();
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Error writing to file: " + filePath, e);
    }
  }
}

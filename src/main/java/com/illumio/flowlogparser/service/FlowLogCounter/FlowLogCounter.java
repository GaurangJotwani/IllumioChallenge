package main.java.com.illumio.flowlogparser.service.FlowLogCounter;

public interface FlowLogCounter {

  /**
   * Outputs a file of tag matches based on a lookup table and flow log file.
   *
   * @param lookUpSrcFilePath      The file path to the lookup table CSV file.
   * @param networkFlowLogFilePath  The file path to the flow log file.
   * @param destinationFilePath    The file path where the output  should be saved.
   *
   * Input: A lookup table CSV file and a flow log file.
   * Output: A report file containing the count of matches for each tag saved at dst path.
   */
  void outputMatchCountFile(String lookUpSrcFilePath, String networkFlowLogFilePath,
                           String destinationFilePath) throws Exception;
}

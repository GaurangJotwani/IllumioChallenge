package runner;

import main.java.com.illumio.flowlogparser.model.FlowLogRecord;
import main.java.com.illumio.flowlogparser.model.FlowLogRecordV2;
import main.java.com.illumio.flowlogparser.model.FlowLogRecordV3;
import main.java.com.illumio.flowlogparser.service.FlowLogCounter.FlowLogCounterImp;
import main.java.com.illumio.flowlogparser.service.FlowLogCounter.LookupTable;
import main.java.com.illumio.flowlogparser.service.FlowLogCounter.NetworkFlowLogLoader;
import main.java.com.illumio.flowlogparser.utility.CsvParser;

import java.util.Scanner;

public class FlowLogCounterRunner {

  public static void main(String[] args) {
    var scanner = new Scanner(System.in);
    var csvParser = new CsvParser();

    while (true) {
      try {
        var lookUpSrcFilePath = promptForInput(scanner, "Enter the path for the lookup file:");
        var networkFlowLogFilePath = promptForInput(scanner, "Enter the path for the network flow log file:");
        var destinationFilePath = promptForInput(scanner, "Enter the path for the output file destination:");
        var versionInput = promptForInput(scanner, "Enter the version number (2 or 3, default is 2):");

        var version = versionInput.isEmpty() ? 2 : Integer.parseInt(versionInput);
        var flowLogRecordClass = getFlowLogRecordClass(version);

        var lookupTable = new LookupTable(lookUpSrcFilePath, csvParser);
        var networkFlowLogLoader = new NetworkFlowLogLoader(networkFlowLogFilePath, csvParser, flowLogRecordClass);
        var flowLogCounter = new FlowLogCounterImp(lookupTable, networkFlowLogLoader);

        flowLogCounter.outputMatchCountFile(lookUpSrcFilePath, networkFlowLogFilePath, destinationFilePath);

        System.out.println("Output written to " + destinationFilePath);

        if (!shouldContinue(scanner)) break;
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println("An error occurred. Please try again.");
      }
    }
    scanner.close();
  }

  private static String promptForInput(Scanner scanner, String promptMessage) {
    System.out.println(promptMessage);
    return scanner.nextLine();
  }

  private static Class<? extends FlowLogRecord> getFlowLogRecordClass(int version) {
    return (version == 3) ? FlowLogRecordV3.class : FlowLogRecordV2.class;
  }

  private static boolean shouldContinue(Scanner scanner) {
    var continueInput = promptForInput(scanner, "Do you want to process another file? (yes/no)");
    return continueInput.equalsIgnoreCase("yes");
  }
}

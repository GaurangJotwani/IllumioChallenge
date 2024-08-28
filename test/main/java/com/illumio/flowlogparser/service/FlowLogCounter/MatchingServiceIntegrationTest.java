package main.java.com.illumio.flowlogparser.service.FlowLogCounter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import main.java.com.illumio.flowlogparser.model.FlowLogRecordV2;
import main.java.com.illumio.flowlogparser.utility.CsvParser;


public class MatchingServiceIntegrationTest {

  private CsvParser csvParser;
  private static final String LOOKUP_ENTRY_CSV_PATH = "test/lookup_entry.csv";
  private static final String FLOW_LOG_RECORD_CSV_PATH = "test/flow_log_record.csv";

  @Before
  public void setUp() {
    csvParser = new CsvParser();
  }

  @Test
  public void testMatchRecordsWithComplexData() throws Exception {
    createLookupEntryCsvFile();
    createFlowLogRecordCsvFile();

    var lookupTable = new LookupTable(LOOKUP_ENTRY_CSV_PATH, csvParser);
    var networkFlowLogs = new NetworkFlowLogLoader(FLOW_LOG_RECORD_CSV_PATH, csvParser, FlowLogRecordV2.class);
    var matchingService = new MatchingService(lookupTable, networkFlowLogs);

    var result = matchingService.matchRecords();

    assertEquals(3, (int) result.getTagMatchCounts().get("sv_P1"));
    assertEquals(1, (int) result.getTagMatchCounts().get("sv_P2"));
    assertEquals(2, (int) result.getTagMatchCounts().get("SV_P3"));
    assertEquals(3, (int) result.getTagMatchCounts().get("email"));
    assertEquals(10, (int) result.getTagMatchCounts().get("Untagged"));

    assertEquals(2, (int) result.getProtocolMatchCounts().get("25,tcp"));
    assertEquals(1, (int) result.getProtocolMatchCounts().get("443,tcp"));
    assertEquals(1, (int) result.getProtocolMatchCounts().get("23,tcp"));
    assertEquals(1, (int) result.getProtocolMatchCounts().get("110,tcp"));
    assertEquals(1, (int) result.getProtocolMatchCounts().get("143,tcp"));
    assertEquals(3, (int) result.getProtocolMatchCounts().get("1030,tcp"));
    assertEquals(1, (int) result.getProtocolMatchCounts().get("993,tcp"));
    assertEquals(1, (int) result.getProtocolMatchCounts().get("1024,tcp"));
    assertEquals(1, (int) result.getProtocolMatchCounts().get("80,tcp"));
    assertEquals(1, (int) result.getProtocolMatchCounts().get("56000,tcp"));
    assertEquals(2, (int) result.getProtocolMatchCounts().get("31,udp"));

    deleteFile(LOOKUP_ENTRY_CSV_PATH);
    deleteFile(FLOW_LOG_RECORD_CSV_PATH);
  }

  @Test
  public void testMatchRecordsWithEmptyLookupTable() throws Exception {
    try (FileWriter writer = new FileWriter(LOOKUP_ENTRY_CSV_PATH)) {
      writer.write("dstPort,protocol,tag\n");
    }
    createFlowLogRecordCsvFile();

    var lookupTable = new LookupTable(LOOKUP_ENTRY_CSV_PATH, csvParser);
    var networkFlowLogs = new NetworkFlowLogLoader(FLOW_LOG_RECORD_CSV_PATH,
            csvParser, FlowLogRecordV2.class);
    var matchingService = new MatchingService(lookupTable, networkFlowLogs);

    var result = matchingService.matchRecords();

    assertEquals(0, result.getTagMatchCounts().getOrDefault("sv_P1", 0).intValue());
    assertEquals(0, result.getTagMatchCounts().getOrDefault("sv_P2", 0).intValue());
    assertEquals(0, result.getTagMatchCounts().getOrDefault("email", 0).intValue());
    assertEquals(19, result.getTagMatchCounts().get("Untagged").intValue());

    deleteFile(LOOKUP_ENTRY_CSV_PATH);
    deleteFile(FLOW_LOG_RECORD_CSV_PATH);
  }

  @Test
  public void testMatchRecordsWithEmptyFlowLog() throws Exception {
    try (FileWriter writer = new FileWriter(FLOW_LOG_RECORD_CSV_PATH)) {
      writer.write("version,accountId,interfaceId,srcAddr,dstAddr,srcPort,dstPort,protocol,packets,bytes,startTime,endTime,action,logStatus\n");
    }
    createLookupEntryCsvFile();

    var lookupTable = new LookupTable(LOOKUP_ENTRY_CSV_PATH, csvParser);
    var networkFlowLogs = new NetworkFlowLogLoader(FLOW_LOG_RECORD_CSV_PATH,
            csvParser, FlowLogRecordV2.class);
    var matchingService = new MatchingService(lookupTable, networkFlowLogs);

    var result = matchingService.matchRecords();

    assertEquals(0, result.getTagMatchCounts().getOrDefault("sv_P1", 0).intValue());
    assertEquals(0, result.getTagMatchCounts().getOrDefault("sv_P2", 0).intValue());
    assertEquals(0, result.getTagMatchCounts().getOrDefault("email", 0).intValue());
    assertEquals(0, result.getTagMatchCounts().getOrDefault("Untagged", 0).intValue()); // No logs

    deleteFile(LOOKUP_ENTRY_CSV_PATH);
    deleteFile(FLOW_LOG_RECORD_CSV_PATH);
  }

  @Test
  public void testMatchRecordsWithNoMatchingLookupEntry() throws Exception {
    try (FileWriter writer = new FileWriter(LOOKUP_ENTRY_CSV_PATH)) {
      writer.write("dstPort,protocol,tag\n");
      writer.write("1000,tcp,sv_P1\n"); // Non-matching ports
      writer.write("2000,tcp,sv_P2\n");
      writer.write("3000,tcp,sv_P3\n");
    }
    createFlowLogRecordCsvFile();

    var lookupTable = new LookupTable(LOOKUP_ENTRY_CSV_PATH, csvParser);
    var networkFlowLogs = new NetworkFlowLogLoader(FLOW_LOG_RECORD_CSV_PATH,
            csvParser, FlowLogRecordV2.class);
    var matchingService = new MatchingService(lookupTable, networkFlowLogs);
    var result = matchingService.matchRecords();

    assertEquals(0, result.getTagMatchCounts().getOrDefault("sv_P1", 0).intValue());
    assertEquals(0, result.getTagMatchCounts().getOrDefault("sv_P2", 0).intValue());
    assertEquals(0, result.getTagMatchCounts().getOrDefault("sv_P3", 0).intValue());
    assertEquals(19, result.getTagMatchCounts().get("Untagged").intValue());

    deleteFile(LOOKUP_ENTRY_CSV_PATH);
    deleteFile(FLOW_LOG_RECORD_CSV_PATH);
  }

  @Test
  public void testMatchRecordsWithCaseInsensitiveProtocolAndPort() throws Exception {
    try (FileWriter writer = new FileWriter(LOOKUP_ENTRY_CSV_PATH)) {
      writer.write("dstPort,protocol,tag\n");
      writer.write("25,TCP,sv_P1\n");
      writer.write("443,TCP,sv_P2\n");
      writer.write("110,tcp,email\n");
    }
    createFlowLogRecordCsvFile();

    var lookupTable = new LookupTable(LOOKUP_ENTRY_CSV_PATH, csvParser);
    var networkFlowLogs = new NetworkFlowLogLoader(FLOW_LOG_RECORD_CSV_PATH,
            csvParser, FlowLogRecordV2.class);
    var matchingService = new MatchingService(lookupTable, networkFlowLogs);

    var result = matchingService.matchRecords();

    assertEquals(2, (int) result.getTagMatchCounts().get("sv_P1")); // Port 25
    assertEquals(1, (int) result.getTagMatchCounts().get("sv_P2")); // Port 443
    assertEquals(1, (int) result.getTagMatchCounts().get("email")); // Port 110

    deleteFile(LOOKUP_ENTRY_CSV_PATH);
    deleteFile(FLOW_LOG_RECORD_CSV_PATH);
  }

  @Test
  public void testMatchRecordsWithInvalidData() throws Exception {
    try (FileWriter writer = new FileWriter(FLOW_LOG_RECORD_CSV_PATH)) {
      writer.write("version,accountId,interfaceId,srcAddr,dstAddr,srcPort,dstPort,protocol,packets,bytes,startTime,endTime,action,logStatus\n");
      writer.write("2,abcd00,eni-0a1b2c3d,10.0.1.201,198.51.100.2,49153,443,INVALID_PROTOCOL,25,20000,1620140761,1620140821,ACCEPT,OK\n");
      writer.write("2,abcd00,eni-0a1b2c3d,10.0.1.201,198.51.100.2,49153,443,16,25,20000,1620140761,1620140821,ACCEPT,OK\n");
    }

    createLookupEntryCsvFile();

    var lookupTable = new LookupTable(LOOKUP_ENTRY_CSV_PATH, csvParser);
    var networkFlowLogs = new NetworkFlowLogLoader(FLOW_LOG_RECORD_CSV_PATH, csvParser,
            FlowLogRecordV2.class);
    var matchingService = new MatchingService(lookupTable, networkFlowLogs);

    var result = matchingService.matchRecords();

    assertEquals(1, (int)result.getTagMatchCounts().get("Untagged"));

    deleteFile(LOOKUP_ENTRY_CSV_PATH);
    deleteFile(FLOW_LOG_RECORD_CSV_PATH);
  }

  private void createLookupEntryCsvFile() throws IOException {
    try (FileWriter writer = new FileWriter(LOOKUP_ENTRY_CSV_PATH)) {
      writer.write("dstPort,protocol,tag\n");
      writer.write("25,tcp,sv_P1\n");
      writer.write("68,udp,sv_P2\n");
      writer.write("23,tcp,sv_P1\n");
      writer.write("31,udp,SV_P3\n");
      writer.write("443,tcp,sv_P2\n");
      writer.write("22,tcp,sv_P4\n");
      writer.write("3389,tcp,sv_P5\n");
      writer.write("0,icmp,sv_P5\n");
      writer.write("110,tcp,email\n");
      writer.write("993,tcp,email\n");
      writer.write("143,tcp,email\n");
    }
  }

  private void createFlowLogRecordCsvFile() throws IOException {
    try (FileWriter writer = new FileWriter(FLOW_LOG_RECORD_CSV_PATH)) {
      writer.write("version,accountId,interfaceId,srcAddr,dstAddr,srcPort,dstPort,protocol,packets,bytes,startTime,endTime,action,logStatus\n");
      writer.write("2,123456789012,eni-0a1b2c3d,10.0.1.201,198.51.100.2,49153,443,6,25,20000,1620140761,1620140821,ACCEPT,OK\n");
      writer.write("2,123456789012,eni-4d3c2b1a,192.168.1.100,203.0.113.101,49154,23,6,15,12000,1620140761,1620140821,REJECT,OK\n");
      writer.write("2,123456789012,eni-5e6f7g8h,192.168.1.101,198.51.100.3,49155,25,6,10,8000,1620140761,1620140821,ACCEPT,OK\n");
      writer.write("2,123456789012,eni-9h8g7f6e,172.16.0.100,203.0.113.102,49156,110,6,12,9000,1620140761,1620140821,ACCEPT,OK\n");
      writer.write("2,123456789012,eni-7i8j9k0l,172.16.0.101,192.0.2.203,49157,993,6,8,5000,1620140761,1620140821,ACCEPT,OK\n");
      writer.write("2,123456789012,eni-6m7n8o9p,10.0.2.200,198.51.100.4,49158,143,6,18,14000,1620140761,1620140821,ACCEPT,OK\n");
      writer.write("2,123456789012,eni-6m7n8o9p,10.0.2.200,198.51.100.4,49158,31,17,18,14000,1620140761,1620140821,ACCEPT,OK\n");
      writer.write("2,123456789012,eni-6m7n8o9p,10.0.2.200,198.51.100.4,49158,31,17,18,14000,1620140761,1620140821,ACCEPT,OK\n");
      writer.write("2,123456789012,eni-1a2b3c4d,192.168.0.1,203.0.113.12,80,1024,6,10,5000,1620140661,1620140721,ACCEPT,OK\n");
      writer.write("2,123456789012,eni-1a2b3c4d,203.0.113.12,192.168.0.1,1024,80,6,12,6000,1620140661,1620140721,ACCEPT,OK\n");
      writer.write("2,123456789012,eni-1a2b3c4d,10.0.1.102,172.217.7.228,443,1030,6,8,4000,1620140661,1620140721,ACCEPT,OK\n");
      writer.write("2,123456789012,eni-1a2b3c4d,10.0.1.102,172.217.7.228,443,1030,6,8,4000,1620140661,1620140721,ACCEPT,OK\n");
      writer.write("2,123456789012,eni-1a2b3c4d,10.0.1.102,172.217.7.228,443,1030,6,8,4000,1620140661,1620140721,ACCEPT,OK\n");
      writer.write("2,123456789012,eni-5f6g7h8i,10.0.2.103,52.26.198.183,23,56000,6,15,7500,1620140661,1620140721,REJECT,OK\n");
      writer.write("2,123456789012,eni-5e6f7g8h,192.168.1.101,198.51.100.3,49155,25,6,10,8000,1620140761,1620140821,ACCEPT,OK\n");
      writer.write("2,123456789012,eni-9k10l11m,192.168.1.5,51.15.99.115,25,49321,6,20,10000,1620140661,1620140721,ACCEPT,OK\n");
      writer.write("2,123456789012,eni-1a2b3c4d,192.168.1.6,87.250.250.242,110,49152,6,5,2500,1620140661,1620140721,ACCEPT,OK\n");
      writer.write("2,123456789012,eni-2d2e2f3g,192.168.2.7,77.88.55.80,993,49153,6,7,3500,1620140661,1620140721,ACCEPT,OK\n");
      writer.write("2,123456789012,eni-4h5i6j7k,172.16.0.2,192.0.2.146,143,49154,6,9,4500,1620140661,1620140721,ACCEPT,OK\n");
    }
  }

  private void deleteFile(final String filePath) {
    var file = new File(filePath);
    if (file.exists()) file.delete();
  }
}

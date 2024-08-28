package main.java.com.illumio.flowlogparser.utility;

import main.java.com.illumio.flowlogparser.model.LookupEntry;
import main.java.com.illumio.flowlogparser.model.FlowLogRecordV2;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class CsvParserTest {

  private CsvParser csvParser;
  private static final String LOOKUP_ENTRY_CSV_PATH = "test/lookup_entry.csv";
  private static final String FLOW_LOG_RECORD_CSV_PATH = "test/flow_log_record.csv";
  private static final String INVALID_CSV_PATH = "test/invalid_data.csv";
  private static final String MISSING_FILE_PATH = "test/missing_file.csv";

  @Before
  public void setUp() {
    csvParser = new CsvParser();
  }

  @Test
  public void testValidLookupEntryCsvFile() throws Exception {
    createValidLookupEntryCsvFile();

    List<LookupEntry> entries = csvParser.parse(LOOKUP_ENTRY_CSV_PATH, LookupEntry.class);

    assertNotNull(entries);
    assertEquals(3, entries.size());

    final var firstEntry = entries.get(0);
    assertEquals(Integer.valueOf(25), firstEntry.dstPort);
    assertEquals("tcp", firstEntry.protocol);
    assertEquals("sv_P1", firstEntry.tag);

    deleteFile(LOOKUP_ENTRY_CSV_PATH);
  }

  @Test(expected = Exception.class)
  public void testMissingLookupEntryFile() throws Exception {
    csvParser.parse(MISSING_FILE_PATH, LookupEntry.class);
  }

  @Test
  public void testInvalidLookupEntryCsvFileFormat() throws Exception {
    createInvalidCsvFile();

    List<LookupEntry> entries = csvParser.parse(INVALID_CSV_PATH, LookupEntry.class);

    assertTrue(entries.isEmpty());
    deleteFile(INVALID_CSV_PATH);
  }

  @Test
  public void testValidFlowLogRecordCsvFile() throws Exception {
    createValidFlowLogRecordCsvFile();

    List<FlowLogRecordV2> records =
            csvParser.parse(FLOW_LOG_RECORD_CSV_PATH, FlowLogRecordV2.class);

    assertNotNull(records);
    assertEquals(2, records.size());

    var firstRecord = records.get(0);
    assertEquals(Integer.valueOf(2), firstRecord.version);
    assertEquals("111122223333", firstRecord.accountId);
    assertEquals(Integer.valueOf(25), firstRecord.srcPort);
    assertEquals(Long.valueOf(1234567890), firstRecord.startTime);
    assertEquals("ACCEPT", firstRecord.action);

    deleteFile(FLOW_LOG_RECORD_CSV_PATH);
  }

  @Test(expected = Exception.class)
  public void testMissingFlowLogRecordFile() throws Exception {
    csvParser.parse(MISSING_FILE_PATH, FlowLogRecordV2.class);
  }

  @Test
  public void testInvalidFlowLogRecordCsvFileFormat() throws Exception {
    createInvalidCsvFile();
    List<FlowLogRecordV2> records = csvParser.parse(INVALID_CSV_PATH, FlowLogRecordV2.class);
    assertTrue(records.isEmpty());
    deleteFile(INVALID_CSV_PATH);
  }

  private void createValidLookupEntryCsvFile() {
    try (var writer = new FileWriter(LOOKUP_ENTRY_CSV_PATH)) {
      writer.write("dstPort,protocol,tag\n");
      writer.write("25,tcp,sv_P1\n");
      writer.write("68,udp,sv_P2\n");
      writer.write("443,tcp,sv_P2\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void createValidFlowLogRecordCsvFile() {
    try (var writer = new FileWriter(FLOW_LOG_RECORD_CSV_PATH)) {
      writer.write("version,accountId,interfaceId,srcAddr,dstAddr,srcPort,dstPort,protocol,packets,bytes,startTime,endTime,action,logStatus\n");
      writer.write("2,111122223333,eni-12345,10.0.0.1,10.0.0.2,25,12345,6,10,500,1234567890,1234567891,ACCEPT,OK\n");
      writer.write("2,111122223334,eni-12346,10.0.0.3,10.0.0.4,443,12346,6,20,1000,1234567892,1234567893,REJECT,OK\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void createInvalidCsvFile() {
    try (var writer = new FileWriter(INVALID_CSV_PATH)) {
      writer.write("dstPort protocol tag\n"); // Invalid format, no commas
      writer.write("25 tcp sv_P1\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void deleteFile(final String filePath) {
    var file = new File(filePath);
    if (file.exists()) file.delete();
  }
}

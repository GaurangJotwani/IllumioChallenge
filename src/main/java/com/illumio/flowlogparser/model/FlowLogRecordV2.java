package main.java.com.illumio.flowlogparser.model;

public class FlowLogRecordV2 extends FlowLogRecord {
  public Integer version;
  public String accountId;
  public String interfaceId;
  public String srcAddr;
  public String dstAddr;
  public Integer srcPort;
  public Integer dstPort;
  public Integer protocol;
  public Long packets;
  public Long bytes;
  public Long startTime;
  public Long endTime;
  public String action;
  public String logStatus;

  public FlowLogRecordV2() {}

  public FlowLogRecordV2(Integer version, String accountId, String interfaceId, String srcAddr, String dstAddr,
                         Integer srcPort, Integer dstPort, Integer protocol, Long packets,
                         Long bytes, Long startTime, Long endTime, String action, String logStatus) {
    this.version = version;
    this.accountId = accountId;
    this.interfaceId = interfaceId;
    this.srcAddr = srcAddr;
    this.dstAddr = dstAddr;
    this.srcPort = srcPort;
    this.dstPort = dstPort;
    this.protocol = protocol;
    this.packets = packets;
    this.bytes = bytes;
    this.startTime = startTime;
    this.endTime = endTime;
    this.action = action;
    this.logStatus = logStatus;
  }

  @Override
  public Integer getDstPort() { return dstPort; }

  @Override
  public Integer getProtocol() {
    return protocol;
  }
}

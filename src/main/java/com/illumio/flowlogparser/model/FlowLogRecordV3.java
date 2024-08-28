package main.java.com.illumio.flowlogparser.model;

public class FlowLogRecordV3 extends FlowLogRecordV2 {
  public String vpcId;
  public String subnetId;
  public String instanceId;
  public Integer tcpFlags;
  public String type;
  public String pktSrcAddr;
  public String pktDstAddr;

  public FlowLogRecordV3() {}

  public FlowLogRecordV3(Integer version, String accountId, String interfaceId, String srcAddr, String dstAddr,
                         Integer srcPort, Integer dstPort, Integer protocol, Long packets,
                         Long bytes, Long startTime, Long endTime, String action, String logStatus,
                         String vpcId, String subnetId, String instanceId, Integer tcpFlags,
                         String type, String pktSrcAddr, String pktDstAddr) {
    super(version, accountId, interfaceId, srcAddr, dstAddr, srcPort, dstPort, protocol, packets,
            bytes, startTime, endTime, action, logStatus);
    this.vpcId = vpcId;
    this.subnetId = subnetId;
    this.instanceId = instanceId;
    this.tcpFlags = tcpFlags;
    this.type = type;
    this.pktSrcAddr = pktSrcAddr;
    this.pktDstAddr = pktDstAddr;
  }
}

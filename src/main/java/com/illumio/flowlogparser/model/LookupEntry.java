package main.java.com.illumio.flowlogparser.model;

public class LookupEntry {
  public Integer dstPort;
  public String protocol;
  public String tag;

  public LookupEntry() {}

  public LookupEntry(Integer dstPort, String protocol, String tag) {
    this.dstPort = dstPort;
    this.protocol = protocol;
    this.tag = tag;
  }
}

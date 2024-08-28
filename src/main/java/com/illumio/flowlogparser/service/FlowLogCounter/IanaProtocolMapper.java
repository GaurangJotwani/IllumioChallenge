package main.java.com.illumio.flowlogparser.service.FlowLogCounter;

import java.util.HashMap;
import java.util.Map;

class IanaProtocolMapper {

  private static final IanaProtocolMapper INSTANCE = new IanaProtocolMapper();
  private final Map<Integer, String> protocolIanaMap;

  private IanaProtocolMapper() {
    protocolIanaMap = new HashMap<>();
    fillProtocolIanaMap();
  }

  public static IanaProtocolMapper getInstance() {
    return INSTANCE;
  }

  public String getProtocolName(int protocolNumber) {
    return protocolIanaMap.getOrDefault(protocolNumber, "Unknown");
  }

  private void fillProtocolIanaMap() {
    protocolIanaMap.put(0, "HOPOPT");
    protocolIanaMap.put(1, "ICMP");
    protocolIanaMap.put(2, "IGMP");
    protocolIanaMap.put(3, "GGP");
    protocolIanaMap.put(4, "IPv4");
    protocolIanaMap.put(5, "ST");
    protocolIanaMap.put(6, "TCP");
    protocolIanaMap.put(7, "CBT");
    protocolIanaMap.put(8, "EGP");
    protocolIanaMap.put(9, "IGP");
    protocolIanaMap.put(10, "BBN-RCC-MON");
    protocolIanaMap.put(11, "NVP-II");
    protocolIanaMap.put(12, "PUP");
    protocolIanaMap.put(13, "ARGUS");
    protocolIanaMap.put(14, "EMCON");
    protocolIanaMap.put(15, "XNET");
    protocolIanaMap.put(16, "CHAOS");
    protocolIanaMap.put(17, "UDP");
    protocolIanaMap.put(18, "MUX");
    protocolIanaMap.put(19, "DCN-MEAS");
    protocolIanaMap.put(20, "HMP");
    protocolIanaMap.put(21, "PRM");
    protocolIanaMap.put(22, "XNS-IDP");
    protocolIanaMap.put(23, "TRUNK-1");
    protocolIanaMap.put(24, "TRUNK-2");
    protocolIanaMap.put(25, "LEAF-1");
    protocolIanaMap.put(26, "LEAF-2");
    protocolIanaMap.put(27, "RDP");
    protocolIanaMap.put(28, "IRTP");
    protocolIanaMap.put(29, "ISO-TP4");
    protocolIanaMap.put(30, "NETBLT");
    protocolIanaMap.put(31, "MFE-NSP");
    protocolIanaMap.put(32, "MERIT-INP");
    protocolIanaMap.put(33, "DCCP");
    protocolIanaMap.put(34, "3PC");
    protocolIanaMap.put(35, "IDPR");
    protocolIanaMap.put(36, "XTP");
    protocolIanaMap.put(37, "DDP");
    protocolIanaMap.put(38, "IDPR-CMTP");
    protocolIanaMap.put(39, "TP++");
    protocolIanaMap.put(40, "IL");
    protocolIanaMap.put(41, "IPv6");
    protocolIanaMap.put(42, "SDRP");
    protocolIanaMap.put(43, "IPv6-Route");
    protocolIanaMap.put(44, "IPv6-Frag");
    protocolIanaMap.put(45, "IDRP");
    protocolIanaMap.put(46, "RSVP");
    protocolIanaMap.put(47, "GRE");
    protocolIanaMap.put(48, "DSR");
    protocolIanaMap.put(49, "BNA");
    protocolIanaMap.put(50, "ESP");
    protocolIanaMap.put(51, "AH");
    protocolIanaMap.put(52, "I-NLSP");
    protocolIanaMap.put(53, "SWIPE");
    protocolIanaMap.put(54, "NARP");
    protocolIanaMap.put(55, "MOBILE");
    protocolIanaMap.put(56, "TLSP");
    protocolIanaMap.put(57, "SKIP");
    protocolIanaMap.put(58, "IPv6-ICMP");
    protocolIanaMap.put(59, "IPv6-NoNxt");
    protocolIanaMap.put(60, "IPv6-Opts");
    protocolIanaMap.put(62, "CFTP");
    protocolIanaMap.put(64, "SAT-EXPAK");
    protocolIanaMap.put(65, "KRYPTOLAN");
    protocolIanaMap.put(66, "RVD");
    protocolIanaMap.put(67, "IPPC");
    protocolIanaMap.put(69, "SAT-MON");
    protocolIanaMap.put(70, "VISA");
    protocolIanaMap.put(71, "IPCV");
    protocolIanaMap.put(72, "CPNX");
    protocolIanaMap.put(73, "CPHB");
    protocolIanaMap.put(74, "WSN");
    protocolIanaMap.put(75, "PVP");
    protocolIanaMap.put(76, "BR-SAT-MON");
    protocolIanaMap.put(77, "SUN-ND");
    protocolIanaMap.put(78, "WB-MON");
    protocolIanaMap.put(79, "WB-EXPAK");
    protocolIanaMap.put(80, "ISO-IP");
    protocolIanaMap.put(81, "VMTP");
    protocolIanaMap.put(82, "SECURE-VMTP");
    protocolIanaMap.put(83, "VINES");
    protocolIanaMap.put(84, "TTP");
    protocolIanaMap.put(85, "NSFNET-IGP");
    protocolIanaMap.put(86, "DGP");
    protocolIanaMap.put(87, "TCF");
    protocolIanaMap.put(88, "EIGRP");
    protocolIanaMap.put(89, "OSPFIGP");
    protocolIanaMap.put(90, "Sprite-RPC");
    protocolIanaMap.put(91, "LARP");
    protocolIanaMap.put(92, "MTP");
    protocolIanaMap.put(93, "AX.25");
    protocolIanaMap.put(94, "IPIP");
    protocolIanaMap.put(95, "MICP");
    protocolIanaMap.put(96, "SCC-SP");
    protocolIanaMap.put(97, "ETHERIP");
    protocolIanaMap.put(98, "ENCAP");
    protocolIanaMap.put(100, "GMTP");
    protocolIanaMap.put(101, "IFMP");
    protocolIanaMap.put(102, "PNNI");
    protocolIanaMap.put(103, "PIM");
    protocolIanaMap.put(104, "ARIS");
    protocolIanaMap.put(105, "SCPS");
    protocolIanaMap.put(106, "QNX");
    protocolIanaMap.put(107, "A/N");
    protocolIanaMap.put(108, "IPComp");
    protocolIanaMap.put(109, "SNP");
    protocolIanaMap.put(110, "Compaq-Peer");
    protocolIanaMap.put(111, "IPX-in-IP");
    protocolIanaMap.put(112, "VRRP");
    protocolIanaMap.put(113, "PGM");
    protocolIanaMap.put(115, "L2TP");
    protocolIanaMap.put(116, "DDX");
    protocolIanaMap.put(117, "IATP");
    protocolIanaMap.put(118, "STP");
    protocolIanaMap.put(119, "SRP");
    protocolIanaMap.put(120, "UTI");
    protocolIanaMap.put(121, "SMP");
    protocolIanaMap.put(122, "SM");
    protocolIanaMap.put(123, "PTP");
    protocolIanaMap.put(124, "ISIS over IPv4");
    protocolIanaMap.put(125, "FIRE");
    protocolIanaMap.put(126, "CRTP");
    protocolIanaMap.put(127, "CRUDP");
    protocolIanaMap.put(128, "SSCOPMCE");
    protocolIanaMap.put(129, "IPLT");
    protocolIanaMap.put(130, "SPS");
    protocolIanaMap.put(131, "PIPE");
    protocolIanaMap.put(132, "SCTP");
    protocolIanaMap.put(133, "FC");
    protocolIanaMap.put(134, "RSVP-E2E-IGNORE");
    protocolIanaMap.put(135, "Mobility Header");
    protocolIanaMap.put(136, "UDPLite");
    protocolIanaMap.put(137, "MPLS-in-IP");
    protocolIanaMap.put(138, "manet");
    protocolIanaMap.put(139, "HIP");
    protocolIanaMap.put(140, "Shim6");
    protocolIanaMap.put(141, "WESP");
    protocolIanaMap.put(142, "ROHC");
  }
}

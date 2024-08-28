package main.java.com.illumio.flowlogparser.service.FlowLogCounter;
import java.util.HashMap;


class MatchingService {
    private final LookupTable lookupTable;
    private final NetworkFlowLogLoader networkFlowLogs;
    private final HashMap<String, String> protocolPortToTagMap;

    public MatchingService(LookupTable lookupTable, NetworkFlowLogLoader networkFlowLogs) {
        this.lookupTable = lookupTable;
        this.networkFlowLogs = networkFlowLogs;
        this.protocolPortToTagMap = new HashMap<>();
    }

    public MatchResult matchRecords() {
        var tagMatchCounts = new HashMap<String, Integer>();
        var protocolMatchCounts = new HashMap<String, Integer>();

        var lookupEntries = lookupTable.getEntries();
        lookupEntries.forEach(entry -> {
            var key = (entry.dstPort + "," + entry.protocol).toLowerCase();
            protocolPortToTagMap.put(key, entry.tag);
        });

        var logEntries = networkFlowLogs.getEntries();
        logEntries.forEach(entry -> {
            var key =
                    (entry.getDstPort() + "," + IanaProtocolMapper.getInstance().getProtocolName(entry.getProtocol())).toLowerCase();
            var tag = protocolPortToTagMap.getOrDefault(key, "Untagged");
            protocolMatchCounts.put(key, protocolMatchCounts.getOrDefault(key, 0) + 1);
            tagMatchCounts.put(tag, tagMatchCounts.getOrDefault(tag, 0) + 1);
        });

        return new MatchResult(tagMatchCounts, protocolMatchCounts);
    }
}

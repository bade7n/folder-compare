import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ComparisonResultMap {
    private final ConcurrentHashMap<String, RootStringExtraInfo> resultSet = new ConcurrentHashMap<>();
    private final Comparator<Map.Entry<String, RootStringExtraInfo>> comp = Comparator.comparing(Map.Entry<String, RootStringExtraInfo>::getKey, String::compareTo);

    private Set<String> ignoredKeys = Set.of("bin/teamcity-server-configurator.jar/org/apache/commons/io/input/BOMInputStream$1.class");

    private Stream<Map.Entry<String, RootStringExtraInfo>> getInternalStream() {
        return resultSet.entrySet().stream().filter(it -> !ignoredKeys.contains(it.getKey()));
    }

    public List<Map.Entry<String, RootStringExtraInfo>> findMissingInResult2() {
        return getInternalStream().filter(it -> it.getValue().getOccurence() < 0).sorted(comp).collect(Collectors.toList());
    }

    Pattern agentDescriptor = Pattern.compile("^webapps/ROOT/WEB-INF/plugins/([\\w\\-\\.]+)/agent/([\\w\\-\\.]+)/teamcity-plugin.xml$");
    Pattern serverKotlinDsl = Pattern.compile("^webapps/ROOT/WEB-INF/plugins/([\\w\\-\\.]+)/server/kotlin-dsl/(.+)\\.xml$");

    public List<Map.Entry<String, RootStringExtraInfo>> findMissingInResult1() {
        return getInternalStream().filter(it -> it.getValue().getOccurence() > 0)
                .filter(it -> !it.getKey().contains("/server/kotlin-dsl"))
                .filter(it -> !agentDescriptor.matcher(it.getKey()).find())
                .filter(it -> !serverKotlinDsl.matcher(it.getKey()).find())
                .sorted(comp).collect(Collectors.toList());
    }

    public void addResult2(String root, String r1) {
        RootStringExtraInfo rsei = resultSet.putIfAbsent(root, RootStringExtraInfo.makeResult2(r1));
        if (rsei != null)
            rsei.addResult2(r1);
    }

    public void addResult1(String root, String r1) {
        RootStringExtraInfo rsei = resultSet.putIfAbsent(root, RootStringExtraInfo.makeResult1(r1));
        if (rsei != null)
            rsei.addResult1(r1);
    }

    public List<Map.Entry<String, RootStringExtraInfo>> getNonMatchResults() {
        return getInternalStream().filter(it -> it.getValue().getOccurence() != 0).sorted(comp).collect(Collectors.toList());
    }

    @NotNull
    public List<Map.Entry<String, RootStringExtraInfo>> findByPrefix(@NotNull String prefix) {
        return getInternalStream().filter(it -> it.getKey().startsWith(prefix) && it.getValue().getOccurence() != 0).sorted(comp).collect(Collectors.toList());
    }

    @NotNull
    public List<Map.Entry<String, RootStringExtraInfo>> findAllByPrefix(@NotNull String prefix) {
        return getInternalStream().filter(it -> it.getKey().startsWith(prefix)).sorted(comp).collect(Collectors.toList());
    }
}

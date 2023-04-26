import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ComparisonResultMap {
    private final ConcurrentHashMap<String, RootStringExtraInfo> resultSet = new ConcurrentHashMap<>();
    private final Comparator<Map.Entry<String, RootStringExtraInfo>> comp = Comparator.comparing(Map.Entry<String, RootStringExtraInfo>::getKey, String::compareTo);


    public List<Map.Entry<String, RootStringExtraInfo>> findMissingInResult2() {
        return resultSet.entrySet().stream().filter(it -> it.getValue().getOccurence() < 0).sorted(comp).collect(Collectors.toList());
    }

    public List<Map.Entry<String, RootStringExtraInfo>> findMissingInResult1() {
        return resultSet.entrySet().stream().filter(it -> it.getValue().getOccurence() > 0).sorted(comp).collect(Collectors.toList());
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
        return resultSet.entrySet().stream().filter(it -> it.getValue().getOccurence() != 0).sorted(comp).collect(Collectors.toList());
    }

    @NotNull
    public List<Map.Entry<String, RootStringExtraInfo>> findByPrefix(@NotNull String prefix) {
        return resultSet.entrySet().stream().filter(it -> it.getKey().startsWith(prefix) && it.getValue().getOccurence() != 0).sorted(comp).collect(Collectors.toList());
    }

    @NotNull
    public List<Map.Entry<String, RootStringExtraInfo>> findAllByPrefix(@NotNull String prefix) {
        return resultSet.entrySet().stream().filter(it -> it.getKey().startsWith(prefix)).sorted(comp).collect(Collectors.toList());
    }
}

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RootStringExtraInfo {
    // positive - match in result1
    // negative - match in result2
    private final AtomicInteger occurences = new AtomicInteger(0);
    private final List<String> origins1 = new ArrayList<>();
    private final List<String> origins2 = new ArrayList<>();

    public static RootStringExtraInfo makeResult1(String r1) {
        RootStringExtraInfo rsei = new RootStringExtraInfo();
        rsei.addResult1(r1);
        return rsei;
    }

    public static RootStringExtraInfo makeResult2(String r1) {
        RootStringExtraInfo rsei = new RootStringExtraInfo();
        rsei.addResult2(r1);
        return rsei;
    }

    void addResult1(String r1) {
        occurences.incrementAndGet();
        origins1.add(r1);
    }

    void addResult2(String r1) {
        occurences.decrementAndGet();
        origins2.add(r1);
    }

    public List<String> getOrigins1() {
        return origins1;
    }

    public List<String> getOrigins2() {
        return origins2;
    }

    public int getOccurence() {
        return occurences.get();
    }

    public String toString() {
        return "#" + occurences;
    }
}

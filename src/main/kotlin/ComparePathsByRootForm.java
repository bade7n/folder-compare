import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ComparePathsByRootForm {
    private final ComparisonResultMap resultMap = new ComparisonResultMap();

    private List<Pair<Pattern, String>> result1ToRootForm = List.of(
            Pair.of(Pattern.compile("^(TeamCityRaw\\.zip/webapps/ROOT/WEB-INF/lib/)(([\\w\\d\\-\\.]+).jar)/(.+)$"), "$1/$4"),

            Pair.of(Pattern.compile("^webapps/ROOT/WEB-INF/plugins/([\\w\\-\\.]+)/agent/([\\w\\-\\.\\/]+)\\.jar/(.+)$"),
                    "webapps/ROOT/WEB-INF/plugins/$1/agent/$3"),

            Pair.of(Pattern.compile("^webapps/ROOT/WEB-INF/plugins/([\\w\\-\\.]+)/server/([\\w\\-\\.\\/]+)\\.jar/(.+)$"),
                            "webapps/ROOT/WEB-INF/plugins/$1/server/$3"
                    ),

            Pair.of(Pattern.compile("^webapps/ROOT/WEB-INF/lib/([\\w\\-\\.]+)\\.jar/(.+)$"),
                    "webapps/ROOT/WEB-INF/lib/$2"),

            Pair.of(Pattern.compile("^webapps/ROOT/js/ring/([\\w\\-\\/]+)\\.([a-z0-9]+)(\\.js((\\.map)?))$"),
                            "webapps/ROOT/js/ring/$1\\.$3")

    );




    public ComparePathsByRootForm(final List<String> result1, List<String> result2) {
        Thread t1 = new Thread(() -> {
            preprocessResult1(result1);
        });
        Thread t2 = new Thread(() -> {
            preprocessResult2(result2);
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public ComparisonResultMap getResultMap() {
        return resultMap;
    }

    public void preprocessResult1(List<String> result1) {
        for(String r1 : result1) {
            String root = findRoot(r1);
            resultMap.addResult1(root, r1);
        }
    }

    public void preprocessResult2(List<String> result2) {
        for(String r1 : result2) {
            String root = findRoot(r1);
            resultMap.addResult2(root, r1);
        }
    }

    private String findRoot(String r1) {
        for (Pair<Pattern, String> pair: result1ToRootForm) {
            Matcher matcher = pair.getKey().matcher(r1);
            if (matcher.find()) {
                return applyParams(pair.getValue(), getParams(matcher));
            }
        }
        return r1;
    }


//    @Nullable
//    public String compare(@NotNull String r) {
//
//        Boolean stop = r.contains("jetbrains/buildServer/nodejs/context/BuildContext.class");
//
//        String p = r.replace(elasticVersion, masterVersion);
//        if (result2.contains(p))
//            return p;
//
//
//        for (Pair<Pattern, String> e: result1ToRootForm) {
//            Matcher matcher = e.getKey().matcher(r);
//            if (matcher.find()) {
//                String params[] = getParams(matcher);
//                StringChecker pat = applyParams(e.getValue(), params);
//                String element = pat.match(result2);
//                if (element != null)
//                    return element;
//            }
//        }
//        return null;
//    }

    private String applyParams(String pattern, String[] params) {
        for(int i = 0; i< params.length; i++) {
            String param = params[i] != null ? params[i] : "";
            String group = Matcher.quoteReplacement(param);
            pattern = pattern.replace("$" + i, group);
        }
        return pattern;
    }

    private static String[] getParams(Matcher matcher) {
        String[] params = new String[matcher.groupCount()+1];
        for (int i = 0; i<= matcher.groupCount(); i++) {
            String s = matcher.group(i);
            params[i] = s;
        }
        return params;
    }
}
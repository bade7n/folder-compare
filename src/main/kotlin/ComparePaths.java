import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComparePaths {
    private final String elasticVersion;
    private final String masterVersion;
    private final String version;


    private final Set<String> result1;
    private final Set<String> result2;
    private List<Pair<Pattern, String[]>> rulesToMatchPrefixes;

    public ComparePaths(String elasticVersion, String masterVersion, Set<String> result1, Set<String> result2) {
        this.elasticVersion = elasticVersion;
        this.masterVersion = masterVersion;
        this.version = "2023.03-" + elasticVersion;
        this.result1 = result1;
        this.result2 = result2;
        rulesToMatchPrefixes = List.of(
//            Pair.of(Pattern.compile("^(.*)" + elasticVersion + "(.*)$"), new String[] {"$1" + masterVersion + "$2"}),

                Pair.of(Pattern.compile("^webapps/ROOT/WEB-INF/plugins/([\\w\\-]+)/agent/([\\w\\-\\.]+).(zip|jar)/(.+)$"),
                        new String[] {
                                "webapps/ROOT/WEB-INF/plugins/$1/agent/$2.jar/$2/$4",
                                "webapps/ROOT/WEB-INF/plugins/$1/agent/$2.zip/$2/$4"
                        }),


                Pair.of(Pattern.compile("^webapps/ROOT/WEB-INF/plugins/([\\w\\-]+)/agent/([\\w\\-\\.]+).(zip|jar)/([\\.\\-\\w\\/]+)\\.jar/(.+)$"),
                        new String[] {
                                "^webapps/ROOT/WEB-INF/plugins/$1/agent/$2.(zip|jar)/([\\.\\-\\w\\/]+)\\.jar/$5$"
                        }),

                Pair.of(Pattern.compile("^webapps/ROOT/WEB-INF/lib/([\\w\\-\\.]+)\\.jar/(.+)$"),
                        new String[] {
                                "^webapps/ROOT/WEB-INF/lib/(\\w\\-\\.)\\.jar/$2$"
                        }),

                Pair.of(Pattern.compile("^webapps/ROOT/WEB-INF/plugins/([\\w\\-\\.]+)/server/([\\w\\-\\.]+)-"+version+"(\\-classes)?\\.jar/(.+)$"),
                        new String[] {
                                "webapps/ROOT/WEB-INF/plugins/$1/server/$2.jar/$4",
                                "webapps/ROOT/WEB-INF/plugins/$1/server/lib/$2.jar/$4"
                        }),


                Pair.of(Pattern.compile("^webapps/ROOT/WEB-INF/plugins/([\\w\\-\\.]+)/server/([\\.\\-\\w\\/]+)\\.jar/(.+)$"),
                        new String[] {
                                "^webapps/ROOT/WEB-INF/plugins/$1/server/([\\.\\-\\w\\/]+)\\.jar/$3$"
                        }),

                Pair.of(Pattern.compile("^webapps/ROOT/js/ring/([\\w\\-\\/]+)\\.([a-z0-9]+)\\.js((\\.map)?)$"),
                        new String[] {
                                "^webapps/ROOT/js/ring/$1\\.([a-z0-9]+)\\.js$3$"
                        })
        );
    }





    @Nullable
    public String compare(@NotNull String r) {
        Boolean stop = r.contains("jetbrains/buildServer/nodejs/context/BuildContext.class");

        String p = r.replace(elasticVersion, masterVersion);
        if (result2.contains(p))
            return p;


        for (Pair<Pattern, String[]> e: rulesToMatchPrefixes) {
            Matcher matcher = e.getKey().matcher(r);
            if (matcher.find()) {
                String params[] = getParams(matcher);
                for (String pattern: e.getValue()) {
                    StringChecker pat = applyParams(pattern, params);
                    String element = pat.match(result2);
                    if (element != null)
                        return element;
                }
            }
        }
        return null;
    }

    private StringChecker applyParams(String pattern, String[] params) {
        boolean isPattern = pattern.startsWith("^");
        for(int i = 0; i< params.length; i++) {
            String param = params[i] != null ? params[i] : "";
            String group = isPattern ? param.replace("$", "\\$") : param;
            pattern = pattern.replace("$" + i, group);
        }
        return new StringChecker(isPattern, pattern);
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

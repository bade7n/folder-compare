import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComparePaths {
    private static String elasticVersion = "128696";
    private static String masterVersion = "128634";
    private static String version = "2023.03-" + elasticVersion;

    private static Map<Pattern, String> rulesToMatchPrefixes = Map.of(
        Pattern.compile("^(.*)" + elasticVersion + "(.*)$"), "$1" + masterVersion + "$2",

        Pattern.compile("^webapps/ROOT/WEB-INF/plugins/([\\w\\-]+)/agent/([\\w\\-\\.]+).zip/([\\.\\-\\w\\/]+)\\.jar/(.+)$"),
        "^webapps/ROOT/WEB-INF/plugins/$1/agent/$2.zip/([\\.\\-\\w\\/]+)\\.jar/$4$",

        Pattern.compile("^webapps/ROOT/WEB-INF/plugins/([\\w\\-]+)/agent/([\\w\\-\\.]+).zip/(.+)$"),
        "webapps/ROOT/WEB-INF/plugins/$1/agent/$2.zip/$2/$3",

        Pattern.compile("^webapps/ROOT/WEB-INF/plugins/([\\w\\-]+)/server/([\\w\\-\\.]+)-"+version+"(\\-classes)?\\.jar/(.+)$"),
        "webapps/ROOT/WEB-INF/plugins/$1/server/$2.jar/$4",

        Pattern.compile("^webapps/ROOT/WEB-INF/plugins/([\\w\\-]+)/server/([\\w\\-\\.]+)-"+version+"(\\-classes)?\\.jar/(.+)$"),
        "webapps/ROOT/WEB-INF/plugins/$1/server/lib/$2.jar/$4",

        Pattern.compile("^webapps/ROOT/WEB-INF/plugins/([\\w\\-]+)/server/([\\.\\-\\w\\/]+)\\.jar/(.+)$"),
        "^webapps/ROOT/WEB-INF/plugins/$1/server/([\\.\\-\\w\\/]+)\\.jar/$3$"
    );


    private final Set<ComparsionResult> result1;
    private final Set<ComparsionResult> result2;

    public ComparePaths(Set<ComparsionResult> result1, Set<ComparsionResult> result2) {
        this.result1 = result1;
        this.result2 = result2;
    }


    @Nullable
    public ComparsionResult compare(@NotNull ComparsionResult r) {
        Boolean stop = r.getPath().contains("buildServerResources/environmentDetails.jspf");

        for (Map.Entry<Pattern, String> e: rulesToMatchPrefixes.entrySet()) {
            Matcher matcher = e.getKey().matcher(r.getPath());
            if (matcher.find()) {
                var pattern = e.getValue();
                boolean isPattern = pattern.startsWith("^");
                for (int i = 0; i<=matcher.groupCount();i++) {
                    String s = matcher.group(i);
                    if (s != null) {
                        String group = isPattern ? s.replace("$", "\\$") : s;
                        pattern = pattern.replace("$" + i, group);
                    }
                }
                if (isPattern) {
                    Pattern p1 = Pattern.compile(pattern);

                    for (ComparsionResult c1: result2) {
                        if (p1.matcher(c1.getPath()).find())
                            return c1;
                    }
                } else {
                    ComparsionResult element = new ComparsionResult(pattern);
                    if (result2.contains(element))
                        return element;
                }
            }
        }
        return null;
    }
}

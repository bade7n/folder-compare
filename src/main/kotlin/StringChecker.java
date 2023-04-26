import java.util.Set;
import java.util.regex.Pattern;

public class StringChecker {
    private final boolean isPattern;
    private final String pat;

    public StringChecker(boolean isPattern, String pat) {
        this.isPattern = isPattern;
        this.pat = pat;
    }

    public String match(Set<String> result2) {
        if (isPattern) {
            Pattern p1 = Pattern.compile(pat);

            for (String c1 : result2) {
                if (p1.matcher(c1).find())
                    return c1;
            }
        } else {
            if (result2.contains(pat))
                return pat;
        }
        return null;
    }
}

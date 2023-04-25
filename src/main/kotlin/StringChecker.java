import java.util.Set;
import java.util.regex.Pattern;

public class StringChecker {
    private final boolean isPattern;
    private final String pat;

    public StringChecker(boolean isPattern, String pat) {
        this.isPattern = isPattern;
        this.pat = pat;
    }

    public ComparsionResult match(Set<ComparsionResult> result2) {
        if (isPattern) {
            Pattern p1 = Pattern.compile(pat);

            for (ComparsionResult c1 : result2) {
                if (p1.matcher(c1.getPath()).find())
                    return c1;
            }
        } else {
            ComparsionResult element = new ComparsionResult(pat);
            if (result2.contains(element))
                return element;
        }
        return null;
    }
}

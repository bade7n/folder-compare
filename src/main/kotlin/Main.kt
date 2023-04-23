import com.google.common.base.Stopwatch
import java.io.File
import java.nio.file.*
import java.util.Arrays
import java.util.regex.Pattern
import kotlin.io.path.name


fun main(args: Array<String>) {
    val sw = Stopwatch.createStarted()
    var result1 = mutableSetOf<ComparsionResult>();
    val t1 = Thread {
        result1 = fetchResults(Path.of(args.get(0)));
    }.also { it.start() }
    var result2 = mutableSetOf<ComparsionResult>();
    val t2 = Thread {
        result2 = fetchResults(Path.of(args.get(1)));
    }.also { it.start() }
    t2.join();
    t1.join()
    println("Takes ${sw.elapsed()}");
    val comparisonResult = compareResults(result1, result2, elasticVersion, masterVersion)
    println("Takes ${sw.elapsed()}");
    println("Program arguments: ${comparisonResult.first.size} ${comparisonResult.second.size}")
}

private fun fetchResults(path: Path) =
    readFile(path.name + ".cache") ?: ListFileSystem(path, mutableSetOf()).also {
        it.proceed()
        saveInto(it.result, path.name + ".cache")
    }.result

fun saveInto(result: MutableSet<ComparsionResult>, s: String) {
    File(s).writer().use { osw ->
        result.forEach({it -> osw.appendLine(it.path)} )
    }
}

fun readFile(file: String): MutableSet<ComparsionResult>? {
    return File(file).let { i ->
        if (i.exists())
            i.useLines { i1 -> i1.toMutableSet().map { it -> ComparsionResult(it) }.toMutableSet()}
        else
            null
    }
}

fun compareResults(result1: MutableSet<ComparsionResult>, result2: MutableSet<ComparsionResult>, s1: String, s2: String): Pair<MutableSet<ComparsionResult>, MutableSet<ComparsionResult>> {
    val li = result1.toList().listIterator()
    for (r in li) {
        val key = findMatchedInSet(result2, r)?.let {
            result2.remove(it)
            result1.remove(r)
        }
    }
    return Pair(result1, result2);
}

val elasticVersion = "128550"
val masterVersion = "128634"
val version = "2023.03-${elasticVersion}";

val rulesToMatchPrefixes = mapOf(
//    Pair("webapps/ROOT/WEB-INF/plugins/ant/agent/antPlugin.zip/antPlugin/ant-runtime.jar", "webapps/ROOT/WEB-INF/plugins/ant/agent/antPlugin.zip/lib/ant-runtime-${version}.jar"),
//    Pair("webapps/ROOT/WEB-INF/plugins/ant/agent/antPlugin.zip/antPlugin/ant-server-logging.jar", "webapps/ROOT/WEB-INF/plugins/ant/agent/antPlugin.zip/lib/ant-server-logging-${version}.jar"),
//    Pair("webapps/ROOT/WEB-INF/plugins/ant/agent/junitPlugin.zip/junitPlugin/junit-support.jar", "webapps/ROOT/WEB-INF/plugins/ant/agent/junitPlugin.zip/lib/junit-support-${version}.jar"),
//    Pair("webapps/ROOT/WEB-INF/plugins/ant/agent/junitPlugin.zip/junitPlugin/junit-runtime.jar", "webapps/ROOT/WEB-INF/plugins/ant/agent/junitPlugin.zip/lib/junit-runtime-${version}.jar"),
    Pair(Pattern.compile("^(.*)${elasticVersion}(.*)$"),
        "$1${masterVersion}$2"),

    Pair(
        Pattern.compile("^webapps/ROOT/WEB-INF/plugins/(\\w+)/agent/(\\w+).zip/([\\.\\-\\w\\/]+)\\.jar/(.+)$"),
            "^webapps/ROOT/WEB-INF/plugins/$1/agent/$2.zip/([\\.\\-\\w\\/]+)\\.jar/$4$"
    ),
    Pair(
        Pattern.compile("^webapps/ROOT/WEB-INF/plugins/(\\w+)/agent/(\\w+).zip/teamcity-plugin.xml$"),
        "webapps/ROOT/WEB-INF/plugins/$1/agent/$2.zip/$2/teamcity-plugin.xml$"
    ),

    Pair(
        Pattern.compile("^webapps/ROOT/WEB-INF/plugins/(\\w+)/server/([\\.\\-\\w\\/]+)\\.jar/(.+)$"),
        "^webapps/ROOT/WEB-INF/plugins/$1/server/([\\.\\-\\w\\/]+)\\.jar/$3$"
    ),
//    Pair(Regex("webapps/ROOT/WEB-INF/plugins/(\\w+)/agent/(\\w+).zip/(\\w+)/(\\w+).jar").toPattern(),
//        listOf("webapps/ROOT/WEB-INF/plugins/$1/agent/$2.zip/lib/$4-${version}.jar")),

//    Pair(Regex("webapps/ROOT/WEB-INF/plugins/(\\w+)/server/(\\w+)-${version}.jar/").toPattern(),
//        listOf("webapps/ROOT/WEB-INF/plugins/$1/server/$2.jar")),
)

fun findMatchedInSet(result2: MutableSet<ComparsionResult>, r: ComparsionResult): ComparsionResult? {
    var result = result2.contains(r)
    if (result)
        return r
    rulesToMatchPrefixes.forEach { (key, value) ->
        key.matcher(r.path).let {
            if (it.find()) {
                var pattern = value;
                for (i in 0..it.groupCount()) {
                    val group = it.group(i).replace("$", "\\$")
                    pattern = pattern.replace("$"+i, group);
                }
                if (pattern.startsWith("^")) {
                    val p1 = Pattern.compile(pattern)

                    for (r in result2) {
                        if (p1.matcher(r.path).find())
                            return r;
                    }
                } else {
                    val element = ComparsionResult(pattern)
                    if (result2.contains(element))
                        return element;

                }
            }
        }
    }
    return null;
}

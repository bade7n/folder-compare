import com.google.common.base.Stopwatch
import java.io.File
import java.nio.file.*
import java.util.regex.Pattern
import kotlin.io.path.extension
import kotlin.io.path.name


fun main(args: Array<String>) {
    val path1 = "Dist_Parts_BuildDist_Core_128945_elastic"
    val path2 = "Dist_Parts_BuildDist_Core_128938_master"
//    val path1 = "TeamCity-128944-elastic"
//    val path2 = "TeamCity-128862-master"
    val sw = Stopwatch.createStarted()
    var result1 = listOf<String>();
    var elastic = ""
    var master = ""
    val t1 = Thread {
        val p = fetchResults(Path.of(path1))
        result1 = p.first
        elastic = p.second
    }.also { it.start() }
    var result2 = listOf<String>();
    val t2 = Thread {
        val p = fetchResults(Path.of(path2))
        result2 = p.first
        master = p.second
    }.also { it.start() }
    t2.join();
    t1.join()
    println("Takes ${sw.elapsed()}");
    val comparisonResult = compareResultsByRoot(result1, result2, setOf(elastic, master))
    println("Takes ${sw.elapsed()}");
    saveInto(comparisonResult, "result_comparison.txt")
    val res2Missing = comparisonResult.findMissingInResult2();
    val res1Missing = comparisonResult.findMissingInResult1();
    val diff = comparisonResult.findByPrefix("webapps/ROOT/WEB-INF/plugins/java-dowser")
    println("Program arguments: ${comparisonResult}")
}

fun RootStringExtraInfo.format(root: String) =
    """
        Origin: $root
        In res1: ${this.origins1.joinToString()}
        In res2: ${this.origins2.joinToString()}
        
    """.trimIndent()

private fun fetchResults(path: Path): Pair<List<String>, String> {
    val cacheName = path.name + ".cache"
    val p = Pattern.compile("(\\d+)")
    val matcher = p.matcher(path.name);

    val version = if (matcher.find()) matcher.group(1) else ""

    return Pair(readFile(cacheName) ?: readReal(path, cacheName), version)
}

private fun readReal(path: Path, cacheName: String): List<String> {
    val results = if (path.toFile().isDirectory)
        ListFileSystem(path).proceed()
    else if (path.toFile().isFile && path.extension in setOf("jar", "zip")) {
        ListZipArchive(path, path, path.toFile().inputStream()).proceed()
    } else {
        mutableListOf()
    }
    saveInto(results, cacheName)
    return results
}

fun saveInto(result: MutableList<String>, s: String) {
    File(s).writer().use { osw ->
        result.forEach {
            osw.appendLine(it)
        }
    }
}

fun saveInto(result: ComparisonResultMap, s: String) {
    File(s).writer().use { osw ->
        osw.appendLine("+ file added in the new version, - file removed in the new version")
        result.nonMatchResults.forEach({
            osw.appendLine(it.key + '#' + it.value.occurence)
            it.value.origins1.forEach {
                osw.appendLine("+" + it)
            }
            it.value.origins2.forEach {
                osw.appendLine("-" + it)
            }
        } )
    }
}

fun readFile(file: String): List<String>? {
    return File(file).let { i ->
        if (i.exists())
            i.useLines { i1 -> i1.toList()}
        else
            null
    }
}

fun compareResults(elastic: String, master: String, result1: MutableSet<String>, result2: MutableSet<String>): Pair<MutableSet<String>, MutableSet<String>> {
    val li = result1.toList().listIterator()
    var comparison = ComparePaths(elastic, master, result1, result2)
    for (r in li) {
        val key = findMatchedInSet(result2, r, comparison)?.let {
            result2.remove(it)
            result1.remove(r)
        }
    }
    result1.sortedBy { it };
    result2.sortedBy { it };
    return Pair(result1, result2);
}

fun compareResultsByRoot(result1: List<String>, result2: List<String>, ignoredTokens: Set<String>) = ComparePathsByRootForm(result1, result2, ignoredTokens).resultMap

fun findMatchedInSet(result2: MutableSet<String>, r: String, comparison: ComparePaths): String? {
    var result = result2.contains(r)
    if (result)
        return r
    return comparison.compare(r)
}

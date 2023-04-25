import com.google.common.base.Stopwatch
import java.io.File
import java.nio.file.*
import kotlin.io.path.extension
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
    val comparisonResult = compareResults(result1, result2)
    println("Takes ${sw.elapsed()}");
    saveInto(comparisonResult.first, "result1.txt")
    saveInto(comparisonResult.second, "result2.txt")
    println("Program arguments: ${comparisonResult.first.size} ${comparisonResult.second.size}")
}

private fun fetchResults(path: Path): MutableSet<ComparsionResult> {
    val cacheName = path.name + ".cache"
    return readFile(cacheName) ?: readReal(path, cacheName)
}

private fun readReal(path: Path, cacheName: String): MutableSet<ComparsionResult> {
    val results = if (path.toFile().isDirectory)
        ListFileSystem(path, mutableSetOf()).proceed()
    else if (path.toFile().isFile && path.extension in setOf("jar", "zip")) {
        ListZipArchive(path, path, path.toFile().inputStream(), mutableSetOf()).proceed()
    } else {
        mutableSetOf()
    }
    saveInto(results, cacheName)
    return results
}

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

fun compareResults(result1: MutableSet<ComparsionResult>, result2: MutableSet<ComparsionResult>): Pair<MutableSet<ComparsionResult>, MutableSet<ComparsionResult>> {
    val li = result1.toList().listIterator()
    var comparison = ComparePaths(result1, result2)
    for (r in li) {
        val key = findMatchedInSet(result2, r, comparison)?.let {
            result2.remove(it)
            result1.remove(r)
        }
    }
    result1.sortedBy { it.path };
    result2.sortedBy { it.path };
    return Pair(result1, result2);
}

fun findMatchedInSet(result2: MutableSet<ComparsionResult>, r: ComparsionResult, comparison: ComparePaths): ComparsionResult? {
    var result = result2.contains(r)
    if (result)
        return r
    return comparison.compare(r)
}

import java.io.File
import java.nio.file.*


fun main(args: Array<String>) {
    val result1 = readFile("comparisonCache1.cache") ?:
     ListFileSystem(Path.of(args.get(0)), mutableListOf()).also {
         it.proceed()
         saveInto(it.result, "comparisonCache1.cache")
    }.result
    val result2 = readFile("comparisonCache2.cache") ?:
    ListFileSystem(Path.of(args.get(1)), mutableListOf()).also {
        it.proceed()
        saveInto(it.result, "comparisonCache2.cache")
    }.result
    result1.sortBy { it.path }
    result2.sortBy { it.path }
    val comparisonResult = compareResults(result1, result2)
    println("Program arguments: ${comparisonResult}")
}

fun saveInto(result: MutableList<ComparsionResult>, s: String) {
    File(s).writer().use { osw ->
        result.forEach({it -> osw.appendLine(it.path)} )
    }
}

fun readFile(file: String): MutableList<ComparsionResult>? {
    return File(file).let { i ->
        if (i.exists())
            i.useLines { i1 -> i1.toMutableList().map { it -> ComparsionResult(it) }.toMutableList()}
        else
            null
    }
}

fun compareResults(result1: MutableList<ComparsionResult>, result2: MutableList<ComparsionResult>): MutableList<ComparsionResult> {
    for (r in result1) {
        if (result2.contains(r)) {
            result2.remove(r)
        }
    }
    return result2;
}

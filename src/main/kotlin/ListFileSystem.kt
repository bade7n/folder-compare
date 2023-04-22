import org.slf4j.LoggerFactory
import java.io.IOException
import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes
import kotlin.io.path.extension

class ListFileSystem(val root: Path, val result: MutableList<ComparsionResult> = mutableListOf<ComparsionResult>()) {
    companion object {
        val log = LoggerFactory.getLogger(ListFileSystem.javaClass)
    }


    fun proceed() {
        Files.walkFileTree(root, object : SimpleFileVisitor<Path>() {
            @Throws(IOException::class)
            override fun visitFile(
                path1: Path,
                attrs: BasicFileAttributes): FileVisitResult {
                val file1 = path1.toFile();

                val relativize: Path = root.relativize(path1)

                if (file1.isDirectory)
                    return FileVisitResult.CONTINUE;
                else {
                    if (path1.extension in setOf("jar", "zip")) {
                        ListZipArchive(path1, path1.toFile().inputStream(), result).proceed()
                    } else {
                        result.add(ComparsionResult(relativize.toString()))
                    }
                }
                return FileVisitResult.CONTINUE
            }
        })
    }
}
import org.slf4j.LoggerFactory
import java.io.IOException
import java.nio.file.FileVisitResult
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.SimpleFileVisitor
import java.nio.file.attribute.BasicFileAttributes
import kotlin.io.path.extension

class ListFileSystem(val root: Path, val result: MutableList<String> = mutableListOf()) {
    companion object {
        val log = LoggerFactory.getLogger(ListFileSystem.javaClass)
    }

    fun proceed(): MutableList<String> {
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
                        ListZipArchive(root, path1, path1.toFile().inputStream(), result).proceed()
                    } else {
                        result.add(relativize.toString())
                    }
                }
                return FileVisitResult.CONTINUE
            }
        })
        return result;
    }
}
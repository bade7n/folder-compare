import java.io.FilterInputStream
import java.io.InputStream
import java.nio.file.Path
import java.util.zip.ZipInputStream
import kotlin.io.path.extension

class ListZipArchive(val root: Path, val path: Path, val inputStream: InputStream, val result: MutableSet<ComparsionResult> = mutableSetOf()) {
    fun proceed() {
        ZipInputStream(inputStream).use { zit ->

            generateSequence { zit.nextEntry }.forEach { zipEntry ->
                try {
                    val path1 = path.resolve(zipEntry.name)

                    if (path1.extension in setOf("jar", "zip")) {
                        val entryIs = object: FilterInputStream(zit) {
                            override fun close() {
                                zit.closeEntry();
                            }
                        }
                        ListZipArchive(root, path1, entryIs, result).proceed()
                    } else {
                        result.add(ComparsionResult(root.relativize(path1).toString()))
                    }
                } catch (e: Throwable) {
                }
            }
        }
    }
}
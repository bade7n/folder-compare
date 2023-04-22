import java.io.FilterInputStream
import java.io.IOException
import java.io.InputStream
import java.nio.file.Path
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import kotlin.io.path.extension

class ListZipArchive(val root: Path, val inputStream: InputStream, val result: MutableList<ComparsionResult> = mutableListOf<ComparsionResult>()) {
    fun proceed() {
        ZipInputStream(inputStream).use { zit ->

            generateSequence { zit.nextEntry }.forEach { zipEntry ->
                try {
                    val path1 = root.resolve(zipEntry.name)

                    if (path1.extension in setOf("jar", "zip")) {
                        val entryIs = object: FilterInputStream(zit) {
                            override fun close() {
                                zit.closeEntry();
                            }
                        }
                        ListZipArchive(path1, entryIs, result).proceed()
                    } else {
                        result.add(ComparsionResult(path1.toString()))
                    }
                } catch (e: Throwable) {
                }
            }
        }
    }
}
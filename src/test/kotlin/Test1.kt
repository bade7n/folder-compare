import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Test1 {
    fun test1() {
        var res1 = """
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/META-INF/MANIFEST.MF
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/ClassBlock.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/ClassInfo.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/ClassLine.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/ClassMethod.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/CoverageCodeRenderer.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/CoverageData.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/CoverageSourceData.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/CoverageStatistics.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/CoverageStatus.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/DiffEntry.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/Entry.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/html/HTMLReportBuilder.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/idea/IDEACoverageClassInfo.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/idea/IDEACoverageData.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/ClassDataBean${'$'}1.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/ClassDataBean${'$'}2.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/ClassDataBean${'$'}FileDataBean.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/ClassDataBean${'$'}LineDataBean.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/ClassDataBean.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/CoverageStatisticsBean${'$'}1.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/CoverageStatisticsBean${'$'}Counter.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/CoverageStatisticsBean.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/FooterInfos.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/html/BaseGenerator${'$'}Converter.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/html/BaseGenerator.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/html/ClassesIndexGenerator${'$'}1.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/html/ClassesIndexGenerator.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/html/ClassSourceReportGenerator.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/html/fs/FileSystem.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/html/fs/RealFSImpl.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/html/fs/ZipFileSystem${'$'}1.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/html/fs/ZipFileSystem${'$'}2.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/html/fs/ZipFileSystem.class
        """.trimIndent()
        val result1 = res1.split("\n")

        val res2 = """
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/META-INF/MANIFEST.MF
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/ClassBlock.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/ClassInfo.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/ClassLine.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/ClassMethod.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/CoverageCodeRenderer.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/CoverageData.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/CoverageSourceData.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/CoverageStatistics.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/CoverageStatus.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/DiffEntry.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/Entry.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/html/HTMLReportBuilder.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/idea/IDEACoverageClassInfo.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/idea/IDEACoverageData.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/ClassDataBean${'$'}1.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/ClassDataBean${'$'}2.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/ClassDataBean${'$'}FileDataBean.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/ClassDataBean${'$'}LineDataBean.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/ClassDataBean.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/CoverageStatisticsBean${'$'}1.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/CoverageStatisticsBean${'$'}Counter.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/CoverageStatisticsBean.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/FooterInfos.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/html/BaseGenerator${'$'}Converter.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/html/BaseGenerator.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/html/ClassesIndexGenerator${'$'}1.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/html/ClassesIndexGenerator.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/html/ClassSourceReportGenerator.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/html/fs/FileSystem.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/html/fs/RealFSImpl.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/html/fs/ZipFileSystem${'$'}1.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-1.0.jar/jetbrains/coverage/report/impl/html/fs/ZipFileSystem${'$'}2.class
            java-ide-plugins/eclipse-plugin-dependencies/coverage.zip/coverage/server/coverage-report-2.0.jar/jetbrains/coverage/report/impl/html/fs/ZipFileSystem.class
        """.trimIndent()
        val result2 = res2.split("\n")
        val res = ComparePathsByRootForm(result1, result2, setOf()).resultMap
        val nonMatchedResults = res.nonMatchResults;
        nonMatchedResults.forEach {
            println(it.value.format(it.key))
        }
        println("hi $res")
    }

    fun test2() {
        val res = ComparePathsByRootForm(listOf("webapps/ROOT/WEB-INF/plugins/Duplicator/agent/duplicatePlugin.zip/teamcity-plugin.xml"), listOf("webapps/ROOT/WEB-INF/plugins/Duplicator/agent/duplicatePlugin.zip/duplicatePlugin/teamcity-plugin.xml"), emptySet()).resultMap;
        val nmr = res.nonMatchResults
        println(nmr)
    }

    @Test
    fun testCheckRoot() {
        var cprf = ComparePathsByRootForm(listOf(), listOf(), setOf());
        assertThat(cprf.findRoot("webapps/ROOT/WEB-INF/plugins/Duplicator/agent/duplicatePlugin.zip/duplicatePlugin/teamcity-plugin.xml"))
            .isEqualTo("webapps/ROOT/WEB-INF/plugins/Duplicator/agent/duplicatePlugin/teamcity-plugin.xml");
        assertThat(cprf.findRoot("webapps/ROOT/WEB-INF/plugins/java-dowser/agent/java-dowser.zip/java-dowser/java-dowser.jar/versionInfo/VersionInfo.java"))
            .isEqualTo("webapps/ROOT/WEB-INF/plugins/java-dowser/agent/java-dowser/versionInfo/VersionInfo.java");
    }

    @Test
    fun test3() {
        val res = ComparePathsByRootForm(listOf("webapps/ROOT/WEB-INF/plugins/java-dowser/agent/java-dowser.zip/java-dowser/java-dowser.jar/versionInfo/VersionInfo.java"), listOf("webapps/ROOT/WEB-INF/plugins/Duplicator/agent/duplicatePlugin.zip/duplicatePlugin/teamcity-plugin.xml"), emptySet()).resultMap;
        val nmr = res.nonMatchResults
        println(nmr)
    }
}
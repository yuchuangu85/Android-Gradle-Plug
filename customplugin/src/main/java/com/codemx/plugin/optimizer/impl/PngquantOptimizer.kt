package com.codemx.plugin.optimizer.impl

import com.codemx.plugin.optimizer.Optimizer
import com.codemx.plugin.util.Logger
import com.codemx.plugin.util.PngquantUtil
import org.gradle.api.Project
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

/**
 * Created by yuchuan
 * DATE 2020/11/24
 * TIME 13:46
 */
class PngquantOptimizer : Optimizer {

    override fun optimize(project: Project, log: Logger, suffixP: String, files: List<File>) {
        PngquantUtil.copyPngquant2BuildFolder(project)
        var suffix = ""
        if (suffixP.trim().isEmpty()) {
            suffix = ".png"
        } else if (!suffixP.endsWith(".png")) {
            suffix += ".png"
        }
        var succeed: Int = 0
        var skipped: Int = 0
        var failed: Int = 0
        var totalSaved: Long = 0L
        val pngquant = PngquantUtil.getPngquantFilePath(project)
        files.forEach { file ->
            val originalSize: Long = file.length()
            val process: Process = ProcessBuilder(
                pngquant, "-v", "--force", "--skip-if-larger",
                "--speed=1", "--ext=${suffix}", file.absolutePath
            ).redirectErrorStream(true).start();
            val br: BufferedReader = BufferedReader(InputStreamReader(process.getInputStream()))
            val error: StringBuilder = StringBuilder()
            var line: String
            br.forEachLine {
                error.append(it)
            }
//            while (null != (line = br.readLine())) {
//                error.append(line)
//            }
            val exitCode: Int = process.waitFor()

            if (exitCode == 0) {
                succeed++
                val output = file.absolutePath.substring(0, file.absolutePath.lastIndexOf(".")) + suffix
                val optimizedSize = File(output).length()
                val rate = 1.0f * (originalSize - optimizedSize) / originalSize * 100
                totalSaved += (originalSize - optimizedSize)
                log.i("Succeed! ${originalSize}B-->${optimizedSize}B, ${rate}% saved! ${file.absolutePath}")
            } else if (exitCode == 98) {
                skipped++
                log.w("Skipped! ${file.absolutePath}")
            } else {
                failed++
                log.e("Failed! ${file.absolutePath}")
                log.e("Exit: ${exitCode}. " + error.toString())
            }
        }

        log.i(
            "Total: ${files.size}, Succeed: ${succeed}, " +
                    "Skipped: ${skipped}, Failed: ${failed}, Saved: ${totalSaved / 1024}KB"
        )
    }


}

package com.codemx.plugin.optimizer.impl

import com.codemx.plugin.optimizer.Optimizer
import com.codemx.plugin.util.Logger
import com.codemx.plugin.util.ZopflipngUtil
import org.gradle.api.Project
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

/**
 * Created by yuchuan
 * DATE 2020/11/24
 * TIME 15:03
 */
class ZopflipngOptimizer : Optimizer {
    override fun optimize(project: Project, log: Logger, suffixP: String, files: List<File>) {
        ZopflipngUtil.copyZopflipng2BuildFolder(project)
        var suffix = ""
        if (suffixP.trim().isEmpty()) {
            suffix = ".png"
        } else if (!suffixP.endsWith(".png")) {
            suffix += ".png"
        }

        var succeed = 0
        var skipped = 0
        var failed = 0
        var totalSaved = 0L
        var zopflipng = ZopflipngUtil.getZopflipngFilePath(project)
        files.forEach { file ->
            val originalSize = file.length()

            val output = file.absolutePath.substring(0, file.absolutePath.lastIndexOf(".")) + suffix
            val process = ProcessBuilder(zopflipng, "-y", "-m", file.absolutePath, output).redirectErrorStream(
                true
            ).start()
            val br = BufferedReader(InputStreamReader(process.getInputStream()))
            val error = StringBuilder()
//            String line
//                    while (null != (line = br.readLine())) {
//                        error.append(line)
//                    }
            br.forEachLine {
                error.append(it)
            }
            val exitCode = process.waitFor()

            if (exitCode == 0) {
                succeed++
                val optimizedSize = File(output).length()
                val rate = 1.0f * (originalSize - optimizedSize) / originalSize * 100
                totalSaved += (originalSize - optimizedSize)
                log.i("Succeed! ${originalSize}B-->${optimizedSize}B, ${rate}% saved! ${file.absolutePath}")
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
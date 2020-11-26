package com.codemx.plugin.util

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.Project
import java.io.File
import java.io.FileOutputStream

/**
 * 静态类
 * Created by yuchuan
 * DATE 2020/11/24
 * TIME 14:01
 */
object PngquantUtil {

    val name = "pngquant"

    fun copyPngquant2BuildFolder(project: Project) {
        val pngquantDir: File = getPngquantDirectory(project)

        if (!pngquantDir.exists()) {
            pngquantDir.mkdirs()
        }

        val pngFile: File = File(getPngquantFilePath(project))
        if (!pngFile.exists()) {
//            FileOutputStream(pngFile).withStream {
//                var inputStream = getResourceAsStream("/$name/${getFilename()}")
//                it.write(inputStream.getBytes())
//            }
        }
        pngFile.setExecutable(true, false)
    }

    fun getPngquantDirectoryPath(project: Project): String {
        return   project.buildDir.absolutePath + File.separator + "$name"
    }

    fun getPngquantDirectory(project: Project): File {
        return File (getPngquantDirectoryPath(project))
    }

    fun getPngquantFilePath(project: Project): String {
        return getPngquantDirectoryPath(project) + File.separator + getFilename()
    }

    fun getFilename(): String {
        if (Os.isFamily(Os.FAMILY_WINDOWS)) {
            return "${name}.exe"
        } else if (Os.isFamily(Os.FAMILY_MAC)) {
            return "${name}-mac"
        } else {
            return "$name"
        }
    }

}
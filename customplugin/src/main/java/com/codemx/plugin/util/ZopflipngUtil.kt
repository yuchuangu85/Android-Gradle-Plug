package com.codemx.plugin.util

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.Project
import java.io.File

/**
 * Created by yuchuan
 * DATE 2020/11/24
 * TIME 15:05
 */
object ZopflipngUtil {

    var name = "zopflipng"

    fun copyZopflipng2BuildFolder(project: Project) {
        var pngquantDir = getZopflipngDirectory(project)
        if (!pngquantDir.exists()) {
            pngquantDir.mkdirs()
        }
//        var pngFile = File(getZopflipngFilePath(project))
//        if (!pngFile.exists()) {
//            FileOutputStream(pngFile).withStream {
//                var inputStream = ZopflipngUtil.class. getResourceAsStream ("/$name/${getFilename()}")
//                it.write(inputStream.getBytes())
//            }
//        }
//        pngFile.setExecutable(true, false)
    }

    fun getZopflipngDirectoryPath(project: Project): String {
        return project.buildDir.absolutePath + File.separator + "$name"
    }

    fun getZopflipngDirectory(project: Project): File {
        return File(getZopflipngDirectoryPath(project))
    }

    fun getZopflipngFilePath(project: Project): String {
        return getZopflipngDirectoryPath(project) + File.separator + getFilename()
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
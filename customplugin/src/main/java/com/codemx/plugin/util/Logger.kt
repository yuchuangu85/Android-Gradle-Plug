package com.codemx.plugin.util

import org.gradle.api.Project
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by yuchuan
 * DATE 2020/11/24
 * TIME 14:54
 */
class Logger(project: Project) {

    val LOG_FILE_NAME = "img_optimizer.log"
    val INFO = "info:  "
    val WARN = "warn:  "
    val ERROR = "error: "

    var file: File
    lateinit var writer: Writer

    init {
        file = File(project.projectDir.absolutePath + File.separator + LOG_FILE_NAME)
        PrintWriter(file).close()
    }

    fun write(logLevel: String, msg: String) {
        writer = PrintWriter(
            BufferedWriter(
                OutputStreamWriter(
                    FileOutputStream(file, true), "UTF-8"
                )
            ), true
        )
        try {
            writer.write(getDateTime() + "  " + logLevel)
            writer.write(msg + "\r\n")
            writer.write("----------------------------------------\r\n")
        } catch (e: Exception) {
        } finally {
            writer.close()
        }
    }

    fun getDateTime(): String {
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return df.format(Date())
    }

    fun i(msg: String) {
        write(INFO, msg)
    }

    fun w(msg: String) {
        write(WARN, msg)
    }

    fun e(msg: String) {
        write(ERROR, msg)
    }

}
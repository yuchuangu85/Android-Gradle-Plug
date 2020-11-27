package com.codemx.plugin.task

import com.codemx.plugin.optimizer.Constants
import com.codemx.plugin.optimizer.Optimizer
import com.codemx.plugin.optimizer.OptimizerFactory
import com.codemx.plugin.util.Logger
import groovy.io.FileType
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.security.InvalidParameterException

/**
 * Created by yuchuan
 * DATE 2020/11/24
 * TIME 14:51
 */
class ImgOptimizerTask : DefaultTask() {

    @Input
    @Optional
    lateinit var suffix: String

    @Input
    @Optional
    var triggerSize: Int = 0

    @Input
    @Optional
    var type: String? = Constants.LOSSY

    /**
     * 图片文件夹(drawable-xxx, mipmap-xxx)
     */
    lateinit var imgDirs: List<File>
    private lateinit var log: Logger

    @TaskAction
    fun optimize() {
        log = Logger(project)
        log.i("Task $name begin:")
        checkTriggerSize()
        val optimizer: Optimizer = OptimizerFactory.getOptimizer(type)
        optimizer.optimize(project, log, suffix, checkFile())
        log.i("Task $name executed successfully.")
    }

    private fun checkTriggerSize() {
        if (triggerSize < 0) {
            throw  InvalidParameterException("img-optimizer: invalid triggerSize.")
        }
    }

    private fun checkFile(): List<File> {
        val files = ArrayList<File>()
        imgDirs.forEach { dir ->
            if (dir.isDirectory) {
                dir.listFiles()?.forEach {
                    if (it.totalSpace >= (1024 * triggerSize) && !it.name.endsWith(".9.png") &&
                        (it.name.endsWith(".png"))
                    ) {
                        files.add(it)
                    }
                }
            }
        }
        log.i("${files.size} images need to be optimized.")
        return files
    }
}
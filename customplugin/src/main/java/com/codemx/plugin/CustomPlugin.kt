package com.codemx.plugin

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.api.BaseVariant
import com.codemx.plugin.extentions.ImgOptimizerExtension
import com.codemx.plugin.optimizer.Constants
import com.codemx.plugin.task.ImgOptimizerTask
import org.gradle.api.DomainObjectCollection
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

/**
 * 地址：
 * https://sourcegraph.com/github.com/chenenyu/img-optimizer-gradle-plugin/-/tree/img-optimizer
 */
class CustomPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        print(project.toString())
        if (project.plugins.hasPlugin(AppPlugin::class.java)) {
//            applyAndroid(project, project.android.applicationVariants as DomainObjectCollection<BaseVariant>)
        } else if (project.plugins.hasPlugin(LibraryPlugin::class.java)) {
//            applyAndroid(project, project.android.libraryVariants as DomainObjectCollection<BaseVariant>)
        } else {
//            throw IllegalArgumentException('img-optimizer gradle plugin only works in with Android module.')
        }
    }

    fun applyAndroid(project: Project, variants: DomainObjectCollection<BaseVariant>) {
        val ext = project.extensions.create(Constants.EXT_NAME, ImgOptimizerExtension::class.java)
        variants.all { variant: BaseVariant ->
            // println("-------- variant: $variant.name --------")
            val imgDirectories: ArrayList<File> = arrayListOf()
            variant.sourceSets.forEach { sourceSet ->
                // println("sourceSets.${sourceSet.name} -->")
                sourceSet.resDirectories.forEach { res ->
                    if (res.exists()) {
                        println("${res.name}.directories:")
                        res.listFiles()?.forEach {
                            if (it.isDirectory && (it.name.startsWith("drawable")
                                        || it.name.startsWith("mipmap"))
                            ) {
                                // println("$it.absolutePath")
                                imgDirectories.add(it)
                            }
                        }
                    }
                }
            }

            if (imgDirectories.isNotEmpty()) {
                project.tasks.create(
                    "${Constants.TASK_NAME}${variant.name.capitalize()}",
                    ImgOptimizerTask::class.java
                ) {
                    it.group = "optimize"
                    it.description = "Optimize ${variant.name} images"
                    it.imgDirs = imgDirectories
                    it.triggerSize = ext.triggerSize
                    it.suffix = ext.suffix
                    it.type = ext.type
                }
            }
        }
    }
}


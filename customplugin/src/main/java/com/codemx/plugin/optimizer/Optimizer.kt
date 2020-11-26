package com.codemx.plugin.optimizer

import com.codemx.plugin.util.Logger
import org.gradle.api.Project
import java.io.File

/**
 * Created by yuchuan
 * DATE 2020/11/24
 * TIME 13:47
 */
interface Optimizer {

    /**
     * @param project Project
     * @param log Logger
     * @param suffix String
     * @param files List<File>
     */
    fun optimize(project: Project, log: Logger, suffix: String, files: List<File>)

}
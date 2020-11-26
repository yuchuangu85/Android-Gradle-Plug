package com.codemx.plugin.optimizer

import com.codemx.plugin.optimizer.impl.PngquantOptimizer
import com.codemx.plugin.optimizer.impl.ZopflipngOptimizer

/**
 * Created by yuchuan
 * DATE 2020/11/24
 * TIME 15:02
 */
object OptimizerFactory {

    fun getOptimizer(type: String?): Optimizer {
        return when (type) {
            Constants.LOSSY -> {
                PngquantOptimizer()
            }
            Constants.LOSSLESS -> {
                ZopflipngOptimizer()
            }
            else -> {
                throw  IllegalArgumentException ("Unacceptable optimizer type. Please use lossy or lossless.")
            }
        }
    }

}
package com.codemx.plugin.extentions

import com.codemx.plugin.optimizer.Constants

/**
 * Created by yuchuan
 * DATE 2020/11/24
 * TIME 10:46
 */
class ImgOptimizerExtension {
    /**
     * 优化后生成的图片名后缀
     */
    var suffix = ""

    /**
     * 触发优化的起始大小(kb)
     */
    var triggerSize = 0

    /**
     * 压缩模式(默认有损压缩)
     */
    var type: String? = Constants.LOSSY
}
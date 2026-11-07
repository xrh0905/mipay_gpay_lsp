package com.mipay.gpay.lsp

import android.util.Log
import io.github.libxposed.api.XposedModule

/**
 * Thin wrapper over [XposedModule.log] so hook code does not need to carry the module instance.
 */
object DevLog {
    private const val TAG = "MiPayGPayLSP"

    @Volatile
    private var logger: XposedModule? = null

    fun attach(module: XposedModule) {
        logger = module
    }

    fun i(message: String) {
        val module = logger
        if (module != null) module.log(Log.INFO, TAG, message) else Log.i(TAG, message)
    }

    fun e(message: String, throwable: Throwable? = null) {
        val module = logger
        if (module != null) module.log(Log.ERROR, TAG, message, throwable) else Log.e(TAG, message, throwable)
    }
}

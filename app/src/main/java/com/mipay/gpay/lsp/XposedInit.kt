package com.mipay.gpay.lsp

import io.github.libxposed.api.XposedModule
import io.github.libxposed.api.XposedModuleInterface.ModuleLoadedParam
import io.github.libxposed.api.XposedModuleInterface.PackageReadyParam

/**
 * libxposed API 102 entry point, declared in META-INF/xposed/java_init.list.
 */
class XposedInit : XposedModule() {

    override fun onModuleLoaded(param: ModuleLoadedParam) {
        DevLog.attach(this)
        DevLog.i(
            "onModuleLoaded: process=${param.processName} " +
                "framework=$frameworkName($frameworkVersionCode) api=$apiVersion"
        )
    }

    override fun onPackageReady(param: PackageReadyParam) {
        if (param.packageName != MainHook.MIPAY_PKG) {
            DevLog.i("onPackageReady ignored package=${param.packageName}")
            return
        }

        DevLog.i("onPackageReady: ${param.packageName}")
        try {
            MainHook(this).install(param.classLoader)
        } catch (t: Throwable) {
            DevLog.e("MainHook install failed", t)
        }
    }
}

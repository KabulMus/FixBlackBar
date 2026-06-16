package com.ethan.fixblackbar

import android.view.View
import android.view.WindowManager
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class MainHook : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {

        // 排除系统框架自身的进程，防止系统启动时产生冗余日志
        if (lpparam.packageName == "android") {
            return
        }

        // ==========================================
        // 功能一：通用全屏黑条 / 挖孔状态修正（保留）
        // ==========================================
        try {
            XposedHelpers.findAndHookMethod(
                WindowManager.LayoutParams::class.java,
                "copyFrom",
                WindowManager.LayoutParams::class.java,
                object : XC_MethodHook() {
                    @Throws(Throwable::class)
                    override fun beforeHookedMethod(param: MethodHookParam) {
                        val src = param.args[0] as WindowManager.LayoutParams
                        val oldMode = src.layoutInDisplayCutoutMode
                        val targetMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES

                        if (oldMode != targetMode) {
                            src.layoutInDisplayCutoutMode = targetMode
                            XposedBridge.log("Ethan_Fix: [挖孔修正] 成功修正窗口模式 | 当前进程: ${lpparam.packageName}")
                        }
                    }
                }
            )
        } catch (e: Throwable) {
            XposedBridge.log("Ethan_Fix: 窗口 Hook 失败: " + e.message)
        }

        // ==========================================
        // 功能二：霸道降维打击：从底层干掉导航栏小横条黑块
        // ==========================================
        try {
            // Hook View 树附加到窗口（AttachedToWindow）的时机
            // 此时可以抓取到最纯粹的根视图 DecorView
            XposedHelpers.findAndHookMethod(
                View::class.java,
                "onAttachedToWindow",
                object : XC_MethodHook() {
                    @Throws(Throwable::class)
                    override fun beforeHookedMethod(param: MethodHookParam) {
                        val view = param.thisObject as View

                        // 寻找每个应用的根布局类名 (DecorView)
                        if (view.javaClass.name.endsWith("DecorView")) {
                            // 拿到当前布局的布局参数
                            val lp = view.layoutParams
                            if (lp is WindowManager.LayoutParams) {
                                // 核心魔法：直接在最底层赋予窗口“全屏无阻碍”和“强行沉浸”的标记
                                // FLAG_LAYOUT_NO_LIMITS (0x00000200): 强行允许窗口把内容延伸到屏幕外/包括导航栏
                                // FLAG_TRANSLUCENT_NAVIGATION (0x08000000): 系统强制接管导航栏黑条，使其隐形
                                val targetFlags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION

                                if ((lp.flags and targetFlags) != targetFlags) {
                                    lp.flags = lp.flags or targetFlags
                                    // XposedBridge.log("Ethan_Fix: 成功强制剥离导航栏黑色切边 | 进程: ${lpparam.packageName}")
                                }
                            }
                        }
                    }
                }
            )
        } catch (e: Throwable) {
            XposedBridge.log("Ethan_Fix: 强制沉浸 Hook 失败: " + e.message)
        }
    }
}
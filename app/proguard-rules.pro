# 🎯 严禁混淆 Xposed 入口类，保证 LSPosed 能在反射时顺利找到它
-keep class com.ethan.fixblackbar.MainHook { *; }

# 顺便保护 Xposed 自带的 API 接口不被裁剪
-keep class de.robv.android.xposed.** { *; }

# FixBlackBar

一个基于 [LSPosed](https://github.com/lsposed/lsposed) 的轻量级 Android 修复模块，用于解决部分类原生系统下，特定应用在状态栏或导航栏出现非预期黑色占位条的问题。

## ⚠️ 设备兼容性说明（注意）

* **测试环境：** 本模块目前仅在 Redmi Note 9 5G（运行基于 Android 13 的 PixelExperience 固件）上进行了实际测试并确认有效。
* **未知风险：** 由于不同厂商的 ROM（如 MIUI/OriginOS/ColorOS 等）或不同的 Android 版本对窗口管理器的底层修改各不相同，无法保证本模块在其他机型或系统上能同样生效。请自行测试。

## 📦 使用方法

1. 在 Android Studio 中编译并安装本项目，或在 [Releases](../../releases) 页面下载打包好的 APK。
2. 打开 **LSPosed 管理器**，激活本模块。
3. 在模块的作用域列表中，**可以仅勾选需要修复的目标应用**。
4. 强制停止并重新打开该应用即可生效。

## 📄 开源协议

本项目基于 [MIT License](https://opensource.org/license/mit) 协议开源。
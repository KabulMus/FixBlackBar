# 📱 EthanFixAll / FixBlackBar

一个基于 Xposed/LSPosed 框架的**极致轻量级（几百KB）**全屏沉浸与边界修正模块。专治各种全面屏应用底部的死黑条、死白条，以及全屏状态下挖孔区域无法延伸的顽疾。

[![Platform](https://img.shields.io/badge/Platform-Android%2010+-green.svg)](https://developer.android.com)
[![Framework](https://img.shields.io/badge/Framework-Xposed%20%2F%20LSPosed-blueviolet.svg)](https://github.com/LSPosed/LSPosed)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

---

## ✨ 核心特性

* **⚡ 极致轻量**：严谨派终极优化。彻底移除了 `appcompat`、`material` 等所有臃肿的 UI 依赖库，开启 R8 混淆与资源缩减，APK 体积控制在极低水准，纯粹为 Hook 而生。
* **🕳️ 挖孔修正 (Universal)**：强制将窗口裁剪模式修改为 `SHORT_EDGES`，允许应用内容优雅地延伸进全屏挖孔/刘海区，消除顶部突兀的断层黑条。
* **🛡️ 精准破壁 (Advanced)**：针对顽固应用采用现代 `WindowInsets` 边缘清洗机制（或不设限大招），强制系统导航栏底色纯透明，让应用原生的 Tab 栏色彩自然向下渗透。
* **🔒 防御性白名单**：内置代码级控制网。大招默认关闭，仅对白名单内的特定应用（如网盘、特定视频软件）生效，100% 杜绝其他无辜应用产生排版错乱、组件掉出屏幕外的副作用。

---

## 🛠️ 项目架构 (No-Activity 纯净版)

整个项目采用 **Kotlin** 编写，为了追求零资源残留，移除了所有默认的 `values`、`themes` 等布局资源：

```text
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── assets/
│   │       │   └── xposed_init        # Xposed 灵魂指引入口
│   │       ├── java/com/ethan/...
│   │       │   └── MainHook.kt        # 核心 Hook 逻辑 (包含双功能与白名单)
│   │       └── AndroidManifest.xml    # 纯净清单 (强开 hasCode)
│   └── build.gradle.kts               # 极致缩减版 Gradle 配置

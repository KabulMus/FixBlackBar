plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.ethan.fixblackbar"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.ethan.fixblackbar"
        minSdk = 30
        targetSdk = 36
        versionCode = 200
        versionName = "2.0"

        // 删除了 testInstrumentationRunner，因为极简模块不需要跑安卓原生的 UI 测试
    }

    buildTypes {
        release {
            // 严谨派的终极优化：开启 R8 代码混淆和无用资源缩减
            // 它可以帮你把没有用到的系统类和方法进一步剔除，进一步压榨 APK 体积
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // 👈 强行把原本的 implementation(libs.androidx.core.ktx) 替换为指定具体的低版本
    // 1.12.0 ~ 1.13.0 版本非常稳定，完美兼容你目前的编译配置，且同样极小
    implementation("androidx.core:core-ktx:1.13.1")

    // 核心 Xposed 依赖，保持不变
    compileOnly("de.robv.android.xposed:api:82")
}
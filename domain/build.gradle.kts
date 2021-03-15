plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion (30)
    buildToolsVersion ("29.0.3")

    defaultConfig {
        minSdkVersion (24)
        targetSdkVersion (30)
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation (fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation( Dependencies.kotlin)
    testImplementation (Dependencies.junit)

    implementation(Dependencies.coroutine)
}
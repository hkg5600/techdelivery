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
    implementation(project(":domain"))
    implementation (fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation( Dependencies.kotlin)
    implementation (Dependencies.core_ktx)
    implementation (Dependencies.appcompat)
    implementation (Dependencies.constraintlayout)
    testImplementation (Dependencies.junit)
    androidTestImplementation (Dependencies.android_junit)
    androidTestImplementation (Dependencies.test_espresso)

}
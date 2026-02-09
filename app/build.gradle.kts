plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = rootProject.extra["compileSdk"] as Int
    namespace = "com.sarang.torang"
    defaultConfig {
        minSdk = rootProject.extra["minSdk"] as Int
        targetSdk = rootProject.extra["targetSdk"] as Int
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "com.sarang.torang.CustomTestRunner"

        versionCode = 1
        versionName = "0.1.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtension.get()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    namespace = "com.sryang.feedscreentestapp"
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    /** HILT */
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.nav.compose) // hiltViewModel

    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.activity:activity-compose:1.5.1")

    /** Compose */
    val composeBom = platform("androidx.compose:compose-bom:2024.04.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.material3:material3:1.2.0-alpha10")
    implementation("androidx.compose.material3:material3-window-size-class")

    /** Navigation start */
    // Kotlin
    implementation(libs.nav.fragment.ktx)
    implementation(libs.nav.ui.ktx)

    // Feature module Support
    implementation(libs.nav.dynamic.features.fragment)

    // Testing Navigation
    androidTestImplementation(libs.nav.testing)

    // Jetpack Compose Integration
    implementation(libs.nav.compose)
    /** Navigation end */

    implementation(project(":library"))


    implementation(libs.theme)
    implementation(libs.mediaContentResolver)
    implementation(libs.composePermissionTest)
    implementation(libs.accompanist.permissions)
    implementation(libs.coil)
    implementation(libs.commonImageLoader)
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)
    implementation(libs.shortForm)
}

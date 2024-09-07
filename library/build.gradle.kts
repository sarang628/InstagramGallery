plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-android")
    id("kotlin-kapt")
    alias(libs.plugins.hilt)
    id("maven-publish")
    id("kotlinx-serialization")
}

android {
    compileSdk = rootProject.extra["compileSdk"] as Int
    namespace = "com.sarang.instagralleryModule"
    defaultConfig {
        minSdk = rootProject.extra["minSdk"] as Int
        targetSdk = rootProject.extra["targetSdk"] as Int
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "com.sarang.torang.CustomTestRunner"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_17
        sourceCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
    }
}

dependencies {
    // HILT
    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.nav.compose) // hiltViewModel

    // Testing Start
    testImplementation(libs.junit)
    androidTestImplementation(libs.x.junit.ext)
    androidTestImplementation(libs.x.espresso.core)
    testImplementation(libs.kotlinx.coroutines.test) // coroutines unit test
    androidTestImplementation(libs.x.ui.test.junit4) // Test rules and transitive dependencies
    debugImplementation(libs.x.ui.test.manifest) // Needed for createAndroidComposeRule, but not createComposeRule
    testImplementation(libs.mockito.core) // Mockito
    testImplementation(libs.mockito.inline)
    testImplementation(libs.core.testing) // AndroidX Core Testing
    // Testing End

    // Compose
    androidTestImplementation(platform(libs.x.compose.bom))
    implementation(libs.x.ui) //없으면 @Composable import 안됨
    implementation(libs.x.ui.graphics)
    implementation(libs.x.ui.tooling.preview) // Android Studio Preview support
    debugImplementation(libs.x.ui.tooling)
    implementation(libs.material3) //JetNews Main 따라하기
    implementation(libs.material3.windows.size)
    implementation(libs.lifecycle.runtime.compose)

    // Navigation start
    // Kotlin
    implementation(libs.nav.fragment.ktx)
    implementation(libs.nav.ui.ktx)

    // Feature module Support
    implementation(libs.nav.dynamic.features.fragment)

    // Testing Navigation
    androidTestImplementation(libs.nav.testing)

    // Jetpack Compose Integration
    implementation(libs.nav.compose)
    implementation(libs.kotlinx.serialization.json)
    // Navigation end

    implementation(libs.constraintlayout.compose)

    implementation(libs.mediaContentResolver)
    implementation("io.coil-kt:coil-compose:2.6.0")
    implementation("id.zelory:compressor:3.0.1")
    implementation("com.google.accompanist:accompanist-permissions:0.29.1-alpha")
    implementation("com.github.sarang628:ComposePermissionTest:0f86ef39ec")
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.github.jitpack"
                artifactId = "android-example"
                version = "1.0"
            }
        }
    }
}
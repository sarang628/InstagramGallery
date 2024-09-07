// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.serialization) apply false
}

extra.apply {
    // Sdk and tools
    set("compileSdk", 34)
    set("minSdk", 26)
    set("targetSdk", 34)
    set("buildToolsVersion", "30.0.3")
    set("compose_version", "1.6.0")
}
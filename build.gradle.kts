// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    // Android Application plugin version
    id("com.android.application") version "8.2.2" apply false
    
    // Kotlin Android plugin version
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
}

// Yeh task project clean karne ke liye hota hai
tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}

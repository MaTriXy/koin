import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
}

val androidCompileSDK : String by project
val androidMinSDK : String by project

android {
    namespace = "org.koin.android"
    compileSdk = androidCompileSDK.toInt()
    defaultConfig {
        minSdk = androidMinSDK.toInt()
    }
    buildFeatures {
        buildConfig = false
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

tasks.withType<KotlinCompile>().all {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_1_8)
    }
}

dependencies {
    api(project(":core:koin-core"))
    api(project(":core:koin-core-viewmodel"))
    api(libs.android.appcompat)
    api(libs.android.activity)
    api(libs.android.fragment)
    api(libs.androidx.viewmodel)
    api(libs.androidx.commonJava8)

    // tests
    testImplementation(project(":core:koin-test"))
    testImplementation(project(":core:koin-test-junit4"))
    testImplementation(libs.kotlin.test)
    testImplementation(libs.test.junit)
    testImplementation(libs.test.mockito)
    testImplementation(libs.test.mockk)
}

// android sources
val sourcesJar: TaskProvider<Jar> by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(android.sourceSets.map { it.java.srcDirs })
}

apply(from = file("../../gradle/publish-android.gradle.kts"))

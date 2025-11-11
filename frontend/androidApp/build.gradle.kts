plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.compose")
}

android {
    namespace = "com.tcc.frontend.android"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.tcc.frontend.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = org.jetbrains.compose.ComposeCompilerKotlinVersion.version
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(compose.ui)
    implementation(compose.material3)
    implementation(compose.preview)
    implementation("androidx.activity:activity-compose:1.8.2")
}

@file:Suppress("UNUSED_EXPRESSION")

import com.android.build.api.dsl.Packaging

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.assignment"
    compileSdk = 35 // Ensure compatibility with dependencies

    defaultConfig {
        applicationId = "com.example.assignment"
        minSdk = 21
        targetSdk = 35 // ✅ Updated to match latest dependencies
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_17 // ✅ Updated for better performance
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17" // ✅ Ensure compatibility with Compose
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10" // ✅ Latest stable version
    }

    fun Packaging.() {
        resources.excludes.add("META-INF/*")
    }
}

dependencies {

    // Core Android dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    //noinspection UseTomlInstead
    implementation("androidx.datastore:datastore-preferences:1.1.2")

    // Jetpack Compose BOM (Ensures versions work together)
    implementation(platform(libs.androidx.compose.bom))

    // Jetpack Compose core dependencies
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation("androidx.datastore:datastore-preferences:1.1.2")
    //noinspection UseTomlInstead
    implementation("io.coil-kt:coil-compose:2.2.2")

    // ViewModel for Compose
    implementation(libs.lifecycle.viewmodel.compose)

    implementation("com.squareup.retrofit2:retrofit:2.9.0") // Retrofit for API Calls
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // Convert JSON Response
    //noinspection UseTomlInstead
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7") // ViewModel & LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7") // Jetpack Compose ViewMode
    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation ("androidx.compose.ui:ui:1.0.5" ) // Check for the latest version


    // Jetpack Compose Navigation
    //noinspection UseTomlInstead
    implementation("androidx.navigation:navigation-compose:2.8.7")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.31.1-alpha") // Use the latest version


    implementation ("org.simpleframework:simple-xml:2.7.1")  // XML Parsing
    implementation ("com.squareup.retrofit2:converter-simplexml:2.9.0") // Retrofit XML Converter
    implementation( "androidx.navigation:navigation-compose:2.4.0-beta01") // Check for the latest version
    implementation ("androidx.compose.material3:material3:1.0.0-alpha01") // Check for the latest version

    // Material Icons (Optional but recommended)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.play.services.maps)

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Debugging tools
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

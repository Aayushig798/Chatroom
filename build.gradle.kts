plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)            // ✅ Add this line
    kotlin("kapt")                  // ✅ Needed for annotation processing (Hilt compiler)

//    id ("kotlin-kapt")
//    id ("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.chatroom"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.chatroom"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

//    implementation ("com.google.dagger:hilt-android:2.51")
//
//    kapt ("com.google.dagger:hilt-compiler:2.51")
//    // If using Jetpack ViewModel with Hilt:
//    implementation ("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
//    kapt ("androidx.hilt:hilt-compiler:1.0.0")
    implementation(libs.hilt.android)
    implementation(libs.androidx.foundation.android)   // ✅ Hilt core library
    kapt(libs.hilt.compiler)            // ✅ Hilt annotation processor

    implementation ("androidx.compose.material3:material3:<version>")
    implementation ("androidx.compose.material:material-icons-extended:<version>")

    implementation("androidx.navigation:navigation-compose:2.7.5")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation("androidx.compose.runtime:runtime-livedata:1.5.2")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation(platform("com.google.firebase:firebase-bom:33.16.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation ("com.google.firebase:firebase-database-ktx:20.2.2")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.storage)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
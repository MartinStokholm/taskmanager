plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.gms.google-services")
}

android {
    namespace = "groupassigment.taskmanager.application"
    compileSdk = 34

    defaultConfig {
        applicationId = "groupassigment.taskmanager.application"
        minSdk = 30
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    val nav_version = "2.4.0-alpha10"
    val compose_ui_version = "1.0.4"
    implementation ("androidx.navigation:navigation-compose:$nav_version")
    implementation(platform("com.google.firebase:firebase-bom:32.6.0"))
    implementation ("com.google.firebase:firebase-firestore")
    implementation ("com.google.firebase:firebase-auth-ktx")
    implementation ("androidx.core:core-ktx:1.9.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation ("androidx.activity:activity-compose:1.3.1")
    implementation ("androidx.compose.ui:ui:$compose_ui_version")
    implementation ("androidx.compose.ui:ui-tooling-preview:$compose_ui_version")
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation ("androidx.compose.material:material:$compose_ui_version")
    implementation ("androidx.compose.material:material-icons-extended:$compose_ui_version")
    implementation ("androidx.compose.runtime:runtime:$compose_ui_version")
    implementation ("androidx.compose.runtime:runtime-livedata:$compose_ui_version")
    implementation ("androidx.compose.foundation:foundation:$compose_ui_version")
    implementation ("androidx.compose.foundation:foundation-layout:$compose_ui_version")
    implementation ("androidx.compose.animation:animation:$compose_ui_version")
    implementation ("androidx.compose.animation:animation-core:$compose_ui_version")

    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:$compose_ui_version")
    debugImplementation ("androidx.compose.ui:ui-tooling:$compose_ui_version")
    debugImplementation ("androidx.compose.ui:ui-test-manifest:$compose_ui_version")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0") // Gson converter (for JSON parsing)

}
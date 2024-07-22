plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.b07group2.taamapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.b07group2.taamapp"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.database)
    testImplementation(libs.junit)
    testImplementation("org.mockito:mockito-all:1.10.19")
    testImplementation("org.robolectric:robolectric:4.7.3")
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
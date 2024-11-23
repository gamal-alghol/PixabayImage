plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id ("dagger.hilt.android.plugin")
    kotlin("kapt")
    id("kotlin-kapt")

}

android {
    namespace = "com.gamal.Pixabay"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.gamal.Pixabay"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.swiperefreshlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation (libs.gson)
    implementation (libs.oopsnointernet)

    //Data store
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore)
    //Loading view
    implementation (libs.avloadingindicatorview)
    //retrofit
    implementation (libs.converter.scalars)
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.converter.moshi)
    implementation (libs.logging.interceptor)
    //chucker team
    implementation (libs.library)

    //hilt
    implementation (libs.hilt.android)
    kapt (libs.hilt.android.compiler)
    kapt(libs.androidx.hilt.compiler)
    //View Model
    implementation (libs.androidx.lifecycle.viewmodel.ktx)
    //live Data
    implementation (libs.androidx.lifecycle.livedata.ktx)

    //glide
    implementation (libs.glide)

    //paging
implementation(libs.androidx.paging.runtime.ktx)


}
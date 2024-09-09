plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.bb4first.mycashtask"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.bb4first.mycashtask"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

//noinspection UseTomlInstead
dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")


    // Navigation Component
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.0")

    //Circle Image View
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Coroutine Lifecycle Scopes
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.5")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.5")

    // Activity KTX for viewModels()
    implementation("androidx.activity:activity-ktx:1.9.2")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.6.0")
    implementation("com.squareup.retrofit2:converter-gson:2.6.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.5.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")

    //Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-android-compiler:2.50")
    kapt("androidx.hilt:hilt-compiler:1.2.0")

    //Shared preferences
    implementation("androidx.preference:preference-ktx:1.2.1")

    //Glide
    implementation("com.github.bumptech.glide:glide:4.15.1")

    //ViewPager2
    implementation("androidx.viewpager2:viewpager2:1.1.0")

    // Circular Progress Drawable
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    //Circular Image View
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // Built-in Splash screen
    implementation("androidx.core:core-splashscreen:1.0.1")

    // dp & SSP - a scalable size units
    implementation("com.intuit.ssp:ssp-android:1.1.0")

    // view pager2
    implementation("androidx.viewpager2:viewpager2:1.1.0")

    // Gravity Snap Helper for Horizental RecyclerView
    implementation("com.github.rubensousa:gravitysnaphelper:2.2.2")

    // Paging 3
    implementation("androidx.paging:paging-runtime-ktx:3.3.2")

    // Build-in splashscreen
    implementation("androidx.core:core-splashscreen:1.0.0")
}

kapt {
    correctErrorTypes = true
}
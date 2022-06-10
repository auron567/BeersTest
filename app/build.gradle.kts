plugins {
    id(BuildPlugins.ANDROID_APPLICATION)
    id(BuildPlugins.KOTLIN_ANDROID)
    id(BuildPlugins.KOTLIN_KAPT)
    id(BuildPlugins.KOTLIN_PARCELIZE)
    id(BuildPlugins.SAFE_ARGS)
    id(BuildPlugins.HILT)
}

android {
    compileSdk = Sdk.COMPILE_VERSION

    defaultConfig {
        minSdk = Sdk.MIN_VERSION
        targetSdk = Sdk.TARGET_VERSION

        applicationId = App.ID
        versionCode = App.VERSION_CODE
        versionName = App.VERSION_NAME
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "API_BASE_URL", "\"https://api.punkapi.com/\"")
        buildConfigField("String", "API_VERSION", "\"v2/\"")
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            signingConfig = signingConfigs.getByName("debug")
        }
    }
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi")
    }
}

dependencies {
    // Main
    implementation(Libs.APP_COMPAT)
    implementation(Libs.CORE_KTX)
    implementation(Libs.CORE_SPLASH_SCREEN)

    // UI
    implementation(Libs.CONSTRAINT_LAYOUT)
    implementation(Libs.MATERIAL)
    implementation(Libs.COIL)

    // Coroutines
    implementation(Libs.COROUTINES_CORE)
    implementation(Libs.COROUTINES_ANDROID)

    // Navigation
    implementation(Libs.NAVIGATION_KTX)
    implementation(Libs.NAVIGATION_UI_KTX)

    // Hilt
    implementation(Libs.HILT_ANDROID)
    kapt(Libs.HILT_COMPILER)

    // Retrofit
    implementation(Libs.RETROFIT)
    implementation(Libs.RETROFIT_MOSHI)
    implementation(Libs.OK_HTTP_LOGGING)

    // Moshi
    implementation(Libs.MOSHI)
    kapt(Libs.MOSHI_CODEGEN)

    // Timber
    implementation(Libs.TIMBER)

    // Test
    testImplementation(TestLibs.JUNIT)
    testImplementation(TestLibs.MOCKK)
    testImplementation(TestLibs.TURBINE)
    testImplementation(TestLibs.KOTEST_ASSERTIONS)
    testImplementation(TestLibs.COROUTINES_TEST)
}

object Versions {
    const val COROUTINES = "1.6.2"
    const val APP_COMPAT = "1.4.1"
    const val CORE_KTX = "1.7.0"
    const val CORE_SPLASH_SCREEN = "1.0.0-rc01"
    const val CONSTRAINT_LAYOUT = "2.1.4"
    const val NAVIGATION = "2.4.2"
    const val MATERIAL = "1.6.0"
    const val HILT = "2.42"
    const val RETROFIT = "2.9.0"
    const val OK_HTTP = "4.9.3"
    const val COIL = "2.1.0"
    const val TIMBER = "5.0.1"
    const val JUNIT = "4.13.2"
    const val MOCKK = "1.12.4"
    const val TURBINE = "0.8.0"
    const val KOTEST = "5.3.0"
    const val KTLINT = "0.45.2"
}

object BuildVersions {
    const val AGP = "7.2.1"
    const val KOTLIN = "1.7.0"
    const val KTLINT_GRADLE = "10.3.0"
}

object BuildPlugins {
    const val ANDROID_APPLICATION = "com.android.application"
    const val KOTLIN_ANDROID = "org.jetbrains.kotlin.android"
    const val KOTLIN_KAPT = "org.jetbrains.kotlin.kapt"
    const val KOTLIN_PARCELIZE = "org.jetbrains.kotlin.plugin.parcelize"
    const val SAFE_ARGS = "androidx.navigation.safeargs.kotlin"
    const val HILT = "dagger.hilt.android.plugin"
    const val KTLINT_GRADLE = "org.jlleitschuh.gradle.ktlint"
}

object Libs {
    const val COROUTINES_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINES}"
    const val COROUTINES_ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINES}"
    const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.APP_COMPAT}"
    const val CORE_KTX = "androidx.core:core-ktx:${Versions.CORE_KTX}"
    const val CORE_SPLASH_SCREEN = "androidx.core:core-splashscreen:${Versions.CORE_SPLASH_SCREEN}"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${Versions.CONSTRAINT_LAYOUT}"
    const val NAVIGATION_KTX = "androidx.navigation:navigation-fragment-ktx:${Versions.NAVIGATION}"
    const val NAVIGATION_UI_KTX = "androidx.navigation:navigation-ui-ktx:${Versions.NAVIGATION}"
    const val MATERIAL = "com.google.android.material:material:${Versions.MATERIAL}"
    const val HILT_ANDROID = "com.google.dagger:hilt-android:${Versions.HILT}"
    const val HILT_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val RETROFIT_GSON = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
    const val OK_HTTP_LOGGING = "com.squareup.okhttp3:logging-interceptor:${Versions.OK_HTTP}"
    const val COIL = "io.coil-kt:coil:${Versions.COIL}"
    const val TIMBER = "com.jakewharton.timber:timber:${Versions.TIMBER}"
}

object TestLibs {
    const val JUNIT = "junit:junit:${Versions.JUNIT}"
    const val MOCKK = "io.mockk:mockk:${Versions.MOCKK}"
    const val TURBINE = "app.cash.turbine:turbine:${Versions.TURBINE}"
    const val KOTEST_ASSERTIONS = "io.kotest:kotest-assertions-core-jvm:${Versions.KOTEST}"
    const val COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.COROUTINES}"
}

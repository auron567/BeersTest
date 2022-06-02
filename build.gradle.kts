plugins {
    id(BuildPlugins.ANDROID_APPLICATION) version BuildVersions.AGP apply false
    id(BuildPlugins.KOTLIN_ANDROID) version BuildVersions.KOTLIN apply false
    id(BuildPlugins.SAFE_ARGS) version Versions.NAVIGATION apply false
    id(BuildPlugins.HILT) version Versions.HILT apply false
    id(BuildPlugins.KTLINT_GRADLE) version BuildVersions.KTLINT_GRADLE
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

subprojects {
    apply {
        plugin(BuildPlugins.KTLINT_GRADLE)
    }
    ktlint {
        version.set(Versions.KTLINT)
        verbose.set(true)
        android.set(true)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

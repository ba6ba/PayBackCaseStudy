// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath(ProjectPlugins.hilt)
    }
}

plugins {
    id(Plugins.androidApplication) version (Versions.gradle) apply (false)
    id(Plugins.androidLibrary) version (Versions.gradle) apply (false)
    id(Plugins.kotlinAndroid) version (Versions.kotlin) apply (false)
    id(Plugins.kotlinJvm) version (Versions.kotlin) apply (false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
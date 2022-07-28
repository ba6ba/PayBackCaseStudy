import extensions.kapt

plugins {
    id(Plugins.androidLibrary)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
    id(Plugins.hilt)
}

android {
    compileSdk = AndroidConfig.compileSdk

    defaultConfig.apply {
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk
    }
    buildTypes {
        release {
            buildConfigField("String", "BASE_URL", "\"https://pixabay.com/api/\"")
            buildConfigField("String", "API_KEY", "\"20003399-d7fe2406ddc264a7ee70c701b\"")
        }
        debug {
            buildConfigField("String", "BASE_URL", "\"https://pixabay.com/api/\"")
            buildConfigField("String", "API_KEY", "\"20003399-d7fe2406ddc264a7ee70c701b\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {
    implementation(Dependencies.dagger_hilt)
    implementation(Dependencies.retrofitGson)
    implementation(Dependencies.retrofit)
    implementation(Dependencies.okhttp3)
    implementation(Dependencies.okhttp3Interceptor)
    implementation(Dependencies.coroutines)
    implementation(Dependencies.coroutinesAndroid)
    implementation(Dependencies.timber)
    kapt(Dependencies.hiltCompiler)
}
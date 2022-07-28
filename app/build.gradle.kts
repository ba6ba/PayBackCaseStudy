import extensions.kapt

plugins {
    id(Plugins.androidApplication)
    id(Plugins.kotlinAndroid)
    id(Plugins.kotlinKapt)
    id(Plugins.hilt)
    id(Plugins.parcelize)
}

android {
    compileSdk = AndroidConfig.compileSdk

    defaultConfig {
        applicationId = AndroidConfig.applicationId
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk
        versionCode = AndroidConfig.versionCode
        versionName = AndroidConfig.versionName
    }

    buildTypes {
        release {
            isMinifyEnabled = BuildTypeProd.isMinifyEnabled
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
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(Dependencies.kotlinStdlibJdk)
    implementation(Dependencies.kotlinCoreKtx)
    implementation(Dependencies.androidAppcompat)
    implementation(Dependencies.androidConstraintLayout)
    implementation(Dependencies.androidRecyclerView)
    implementation(Dependencies.materialDesignNew)
    implementation(Dependencies.dagger_hilt)
    implementation(Dependencies.retrofitGson)
    implementation(Dependencies.retrofit)
    implementation(Dependencies.okhttp3)
    implementation(Dependencies.okhttp3Interceptor)
    implementation(Dependencies.coil)
    implementation(Dependencies.activityKtx)
    implementation(Dependencies.fragmentKtx)
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.paging)
    implementation(Dependencies.navigationFragment)
    implementation(Dependencies.navigationUi)
    implementation(Dependencies.hiltNavigationFragment)
    implementation(Dependencies.dimensions)
    implementation(Dependencies.dimensionsSsp)

    implementation(Dependencies.lifeCycleRuntimeExtensions)
    implementation(Dependencies.lifeCycleViewModel)
    implementation(Dependencies.lifeCycleLiveData)
    implementation(Dependencies.lifeCycleLiveDataCore)
    implementation(Dependencies.lifeCycleJava8)
    implementation(Dependencies.lifeCycleSavedStateViewModel)

    implementation(Dependencies.timber)
    implementation(Dependencies.coroutines)
    implementation(Dependencies.coroutinesAndroid)
    implementation(Dependencies.lottie)
    implementation(Dependencies.facebookShimmer)
    implementation(Dependencies.swipeRefreshLayout)

    kapt(Dependencies.lifeCycleCompiler)
    kapt(Dependencies.hiltCompiler)

    testImplementation(Dependencies.androidXTesting)
    androidTestImplementation(Dependencies.androidXTesting)
    testImplementation(Dependencies.mockitoKotlin)
    testImplementation(Dependencies.coroutinesTest)
    testImplementation(Dependencies.coroutinesCoreTest)
    testImplementation(Dependencies.mockitoCore)
    testImplementation(Dependencies.mockitoInline)
    testImplementation(Dependencies.jUnit)
    debugImplementation(Dependencies.fragmentTesting)
    debugImplementation(Dependencies.testCore)
    androidTestImplementation(Dependencies.jUnitExtension)
    androidTestImplementation(Dependencies.espressoCore)
    androidTestImplementation(Dependencies.espressoContrib)
    androidTestImplementation(Dependencies.daggerHiltTesting)
    androidTestImplementation(Dependencies.testRules)
    androidTestImplementation(Dependencies.testRunner)
    kaptAndroidTest(Dependencies.hiltCompiler)
}
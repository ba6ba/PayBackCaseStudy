@Suppress("unused", "MayBeConstant")
object Dependencies {
    val kotlinCoreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    val kotlinStdlibJdk = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"

    val dagger_hilt = "com.google.dagger:hilt-android:${Versions.dagger_hilt}"
    val hilt = "androidx.hilt:hilt:${Versions.hilt}"
    val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.dagger_hilt}"
    val hiltNavigationFragment = "androidx.hilt:hilt-navigation-fragment:${Versions.hiltNavigationFragment}"

    val androidAppcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    val androidConstraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
    val androidRecyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    val materialDesignNew = "com.google.android.material:material:${Versions.materialDesignNew}"

    val retrofitGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val okhttp3Interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3}"
    val okhttp3 = "com.squareup.okhttp3:okhttp:${Versions.okhttp3}"
    val coil = "io.coil-kt:coil:${Versions.coil}"

    val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    val dimensions = "com.intuit.sdp:sdp-android:${Versions.dimensions}"
    val dimensionsSsp = "com.intuit.ssp:ssp-android:${Versions.dimensionsSsp}"

    val activityKtx = "androidx.activity:activity-ktx:${Versions.activityKTXVersion}"
    val coreKtx = "androidx.core:core-ktx:${Versions.coreKTXVersion}"
    val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKTXVersion}"

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    const val lifeCycleRuntimeExtensions =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifeCycle}"
    const val lifeCycleViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifeCycle}"
    const val lifeCycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifeCycle}"
    const val lifeCycleLiveDataCore =
        "androidx.lifecycle:lifecycle-livedata-core-ktx:${Versions.lifeCycle}"
    const val lifeCycleJava8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifeCycle}"
    const val lifeCycleSavedStateViewModel =
        "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifeCycle}"
    const val lifeCycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifeCycle}"

    const val paging = "androidx.paging:paging-runtime-ktx:${Versions.paging}"
    const val lottie = "com.airbnb.android:lottie:${Versions.lottie}"
    const val facebookShimmer  = "com.facebook.shimmer:shimmer:${Versions.facebookShimmer}"
    const val swipeRefreshLayout  = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshLayout}"

    const val androidXTesting = "androidx.arch.core:core-testing:${Versions.androidXTesting}"
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}"
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTest}"
    const val coroutinesCoreTest = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCoreTest}"
    const val mockitoCore = "org.mockito:mockito-core:${Versions.mockitoCore}"
    const val mockitoInline = "org.mockito:mockito-inline:${Versions.mockitoInline}"
    const val jUnit = "junit:junit:${Versions.jUnit}"
    const val jUnitExtension = "androidx.test.ext:junit:${Versions.jUnitExtension}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val espressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
    const val fragmentTesting = "androidx.fragment:fragment-testing:${Versions.fragmentTesting}"
    const val testCore = "androidx.test:core:${Versions.testCore}"
    const val daggerHiltTesting = "com.google.dagger:hilt-android-testing:${Versions.daggerHiltTesting}"
    const val testRunner = "com.android.support.test:runner:${Versions.testRunner}"
    const val testRules = "com.android.support.test:rules:${Versions.testRules}"
}

plugins {
    id("com.android.library")
    alias(libs.plugins.kotlin.android)
    `maven-publish`
}


android {
    namespace = "cu.uci.apklisupdate"
    compileSdk = 34

    defaultConfig {
        minSdk = 14
        multiDexEnabled = true

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    flavorDimensions += "version"
    productFlavors {
        create("full") {}
        create("non-view") {}
    }
    publishing {
        singleVariant("non-viewRelease")
        multipleVariants("full") {
            includeBuildTypeValues("debug", "release")
            includeFlavorDimensionAndValues("version", "full")
        }
    }
}


dependencies {

    implementation(libs.appcompat.v110)
    testImplementation(libs.junit)
    androidTestImplementation(libs.runner)
    androidTestImplementation(libs.espresso.core)
    testImplementation(libs.mockito.core)

    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kluent)
    testImplementation(libs.kotlin.stdlib.jdk7)

    implementation(libs.kotlin.stdlib.jdk7)

    /**Retrofit**/
    implementation(libs.okhttp)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    /** rx java **/
    implementation(libs.rxjava)
    implementation(libs.rxkotlin)
    implementation(libs.rxandroid)
    implementation(libs.adapter.rxjava2)

    implementation(libs.material.v110)
    implementation(libs.palette)

    //html
    implementation(libs.html.textview)
    implementation(libs.picasso)
}

publishing {
    publications {
        register<MavenPublication>("full") {
            groupId = "com.github.suitetecsa"
            artifactId = "apklisupdate"
            version = "1.4"

            afterEvaluate {
                from(components["full"])
            }
        }
        register<MavenPublication>("non-viewRelease") {
            groupId = "com.github.suitetecsa"
            artifactId = "apklisupdate-non-view"
            version = "1.4"

            afterEvaluate {
                from(components["non-viewRelease"])
            }
        }
    }
    repositories {
        maven {
            name = "jitpack"
            url = uri("https://jitpack.io")
        }
    }
}

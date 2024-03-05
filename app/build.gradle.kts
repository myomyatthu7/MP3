plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "app.mmt.test"
    compileSdk = 34

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
    }

    defaultConfig {
        applicationId = "app.mmt.test"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.material:material:1.11.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-database:20.3.0")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("com.airbnb.android:lottie:6.3.0")
    // https://mvnrepository.com/artifact/com.google.http-client/google-http-client-gson
    implementation("com.google.http-client:google-http-client-gson:1.44.1")
    // https://mvnrepository.com/artifact/com.google.api-client/google-api-client
    implementation("com.google.api-client:google-api-client-android:1.31.0")
    implementation("com.google.api-client:google-api-client:1.31.0")
    // https://mvnrepository.com/artifact/com.google.apis/google-api-services-docs
    implementation("com.google.apis:google-api-services-docs:v1-rev61-1.25.0")
    // https://mvnrepository.com/artifact/com.google.apis/google-api-services-drive
    implementation("com.google.apis:google-api-services-drive:v3-rev197-1.25.0")

}
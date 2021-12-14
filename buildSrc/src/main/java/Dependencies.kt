import org.gradle.api.JavaVersion

object Config {
    const val applicationId = "com.github.evgeniychufarnov.dictionary"
    const val compileSdk = 31
    const val minSdk = 23
    const val targetSdk = 31
    const val versionCode = 1
    const val versionName = "1.0"
}

object Modules {
    const val app = ":app"
    const val model = ":model"
    const val repository = ":repository"
}

object Versions {
    const val core = "1.7.0"
    const val appcompat = "1.4.0"
    const val material = "1.4.0"
    const val constraintLayout = "2.1.2"
    const val fragmentKtx = "1.4.0"
    const val retrofit = "2.9.0"
    const val koin = "3.1.4"
    const val glide = "4.12.0"
    const val room = "2.3.0"
}

object AndroidX {
    const val core = "androidx.core:core-ktx:${Versions.core}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
}

object Koin {
    const val koin = "io.insert-koin:koin-core:${Versions.koin}"
    const val koinAndroid = "io.insert-koin:koin-android:${Versions.koin}"
}

object Glide {
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
}

object Room {
    const val runtime = "androidx.room:room-runtime:${Versions.room}"
    const val compiler = "androidx.room:room-compiler:${Versions.room}"
}
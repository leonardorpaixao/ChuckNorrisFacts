object Versions {

    const val gsonVersion = "2.8.5"
    const val kotlin = "1.3.31"
    const val appCompatXVersion = "1.0.2"
    const val gradleVersion = "3.4.1"
    const val constraint = "1.1.3"
    const val androidX = "1.0.0"
    const val lifeCycle = "2.2.0"
    const val rxJava = "1.2.5"
    const val rxAndroid = "1.2.1"
    const val retroFit = "2.5.0"
    const val okHttp = "3.14.0"
    const val gms = "4.2.0"
    const val fabric = "1.26.1"

}

object Dependencies {
    const val fileTree = "fileTree(dir: 'libs', include: ['*.jar'])"
    const val kotlinLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val appCompatX = "androidx.appcompat:appcompat:${Versions.appCompatXVersion}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraint}"

    const val gradle = "com.android.tools.build:gradle:${Versions.gradleVersion}"
    const val fabric = "io.fabric.tools:gradle:${Versions.fabric}"
    const val gms = "com.google.gms:google-services:${Versions.gms}"

    const val gson = "com.google.code.gson:gson:${Versions.gsonVersion}"
    const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.androidX}"
    const val cardView = "androidx.cardview:cardview:${Versions.androidX}"


    const val lifeCycle = "androidx.lifecycle:lifecycle-extensions:${Versions.lifeCycle}"
    const val rxJava = "io.reactivex:rxjava:${Versions.rxJava}"
    const val rxAndroid = "io.reactivex:rxandroid:${Versions.rxAndroid}"
    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"


    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retroFit}"
    const val retrofitAdapter = "com.squareup.retrofit2:adapter-rxjava:${Versions.retroFit}"
    const val retrofitConverter = "com.squareup.retrofit2:converter-gson:${Versions.retroFit}"
    const val okHttpInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"


}

object VersionTest {
    const val runner = "1.2.0"
    const val espresso = "3.0.2"
    const val jUnit = "4.12"
}

object DependenciesTest {
    const val runner = "androidx.test:runner:${VersionTest.runner}"
    const val espressoCore = "com.android.support.test.espresso:espresso-core:${VersionTest.espresso}"
    const val jUnit = "junit:junit:${VersionTest.jUnit}"
}
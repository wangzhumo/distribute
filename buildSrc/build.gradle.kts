import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `kotlin-dsl`
}
repositories{
    google()
    mavenCentral()
    maven("https://developer.huawei.com/repo/")
}

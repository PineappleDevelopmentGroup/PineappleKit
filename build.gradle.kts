plugins {
    `kotlin-dsl`
    `maven-publish`
    `java-gradle-plugin`
}

group = "sh.miles.pineapplekit"
version = "1.0.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(libs.kotlin.jvm)
    implementation(libs.shadow)
}

gradlePlugin {
    val pineappleKit by plugins.creating {
        id = "sh.miles.pineapplekit"
        implementationClass = "sh.miles.pineapplekit.PineappleKitPlugin"
        displayName = "PineappleKit"
        description = "A gradle plugin for easily setting up pineapple"
    }
}

publishing {
    repositories {
        maven("https://maven.miles.sh/pineapple") {
            credentials {
                this.username = System.getenv("PINEAPPLE_REPOSILITE_USERNAME")
                this.password = System.getenv("PINEAPPLE_REPOSILITE_PASSWORD")
            }
        }
    }
}

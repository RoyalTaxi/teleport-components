import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    id("io.gitlab.arturbosch.detekt") version "1.23.8"
    id("org.jlleitschuh.gradle.ktlint") version "14.0.1"
    alias(libs.plugins.android.library)
    `maven-publish`
}

group = "uz.teleport"
version = "1.0.0"

kotlin {
    androidTarget {
        publishLibraryVariants("release")
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "TeleportComponents"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.teleport.design)
            api(libs.teleport.resources)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.landscapist.coil3)
        }
        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
        }
    }
}

android {
    namespace = "uz.teleport.components"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

detekt {
    toolVersion = "1.23.8"
    config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
    buildUponDefaultConfig = true

    source.setFrom(
        files(
            "src/commonMain/kotlin",
            "src/androidMain/kotlin",
        ),
    )

    reports {
        html.required.set(true)
        xml.required.set(true)
    }
}

allprojects {
    plugins.withId("org.jlleitschuh.gradle.ktlint") {
        ktlint {
            filter {
                exclude("**/build/**")
                exclude("**/generated/**")
            }
        }
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/RoyalTaxi/teleport-components")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GITHUB_ACTOR")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

afterEvaluate {
    publishing {
        publications.withType<MavenPublication>().configureEach {
            val baseName = rootProject.name
            if (artifactId.startsWith(baseName)) {
                artifactId = artifactId.replace(baseName, "components")
            }
        }
    }
}

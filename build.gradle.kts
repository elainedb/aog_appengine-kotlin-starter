import com.google.cloud.tools.gradle.appengine.appyaml.AppEngineAppYamlExtension
import org.akhikhl.gretty.GrettyExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("com.google.cloud.tools:appengine-gradle-plugin:2.0.0-rc6")
        classpath("org.akhikhl.gretty:gretty:+")
    }
}

plugins {
    val kotlinVersion = "1.3.30"

    kotlin("jvm") version kotlinVersion
    kotlin("kapt") version kotlinVersion
    java
    war
}

apply {
    plugin("com.google.cloud.tools.appengine-appyaml")
    plugin("org.akhikhl.gretty")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("javax.servlet:javax.servlet-api:3.1.0")
    implementation("com.google.appengine:appengine:+")
    implementation(kotlin("stdlib-jdk8"))

    implementation("com.google.actions:actions-on-google:1.0.0")
    implementation("com.googlecode.json-simple:json-simple:1.1.1")
    testImplementation("junit:junit:4.12")
}

the<GrettyExtension>().let {
    it.httpPort = 8080
    it.contextPath = "/"
    it.servletContainer = "jetty9" // What App Engine Flexible uses
}

the<AppEngineAppYamlExtension>().deploy {
    projectId = "project-id"
    version = "1-0-0"
    stopPreviousVersion = true
    promote = true
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
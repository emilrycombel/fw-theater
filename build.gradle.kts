// dependency version
val mapstructVersion: String by project
val testContainerVersion: String by project
val ulidCreatorVersion: String by project
val lombokVersion: String by project

plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    kotlin("plugin.jpa") version "1.9.25"
    id("org.springframework.boot") version "3.4.0"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "pl.er.code"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    val compileOnly by getting {
        extendsFrom(getByName("annotationProcessor"))
    }
}

repositories {
    mavenCentral()
}

// Define the source set for unit tests (usually already defined as 'test')
sourceSets["test"].apply {
    java.srcDir("src/test/java")
    resources.srcDir("src/test/resources")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath
}

// Define the source set for integration tests
val integrationTest by sourceSets.creating {
    java.srcDir("src/integrationTest/kotlin")
    compileClasspath += sourceSets["main"].output + configurations["testRuntimeClasspath"]
    runtimeClasspath += output + compileClasspath

    resources.srcDir("src/integrationTest/resources")
}


//regirstering integration tests to run after the unit tests
tasks.register<Test>("integrationTest") {
    group = "verification"
    useJUnitPlatform()
    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath
    shouldRunAfter(tasks["test"])

}

tasks.named<Copy>("processIntegrationTestResources") {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

tasks.named<Copy>("processTestResources") {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

tasks["check"].dependsOn(tasks["integrationTest"])

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-logging")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")


    annotationProcessor("org.mapstruct:mapstruct-processor:${mapstructVersion}")
    annotationProcessor("org.hibernate:hibernate-jpamodelgen:6.4.1.Final")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework:spring-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.alibaba.fastjson2:fastjson2:2.0.47")
    implementation("org.mapstruct:mapstruct:${mapstructVersion}")
    implementation("de.siegmar:fastcsv:3.1.0")

    //persistence
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.hibernate:hibernate-envers:6.4.1.Final")
    implementation("org.postgresql:postgresql")
    implementation("com.zaxxer:HikariCP:4.0.3") // Optional as spring-boot-starter-data-jpa already provides it, but you can add if you want a specific version or just for clarity.
    implementation("org.liquibase:liquibase-core:4.24.0")

    implementation("com.github.f4b6a3:ulid-creator:${ulidCreatorVersion}")
    implementation("org.mapstruct:mapstruct:${mapstructVersion}")
    implementation("org.apache.httpcomponents.client5:httpclient5:5.4")
    implementation("commons-net:commons-net:3.8.0")
    implementation("io.micrometer:micrometer-registry-prometheus")

    implementation("net.datafaker:datafaker:2.4.2")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-aop") // For aspects
    implementation("io.github.resilience4j:resilience4j-spring-boot2:2.1.0")

    testAnnotationProcessor("org.mapstruct:mapstruct-processor:${mapstructVersion}")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    "integrationTestImplementation"("org.testcontainers:testcontainers:${testContainerVersion}")
    "integrationTestImplementation"("org.testcontainers:junit-jupiter:${testContainerVersion}")
    "integrationTestImplementation"("org.testcontainers:postgresql:${testContainerVersion}")
    "integrationTestImplementation"("org.testcontainers:rabbitmq:1.20.0")
    "integrationTestImplementation"("io.specto:hoverfly-java:0.19.1")
    "integrationTestImplementation"("io.specto:hoverfly-java-junit5:0.19.1")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}


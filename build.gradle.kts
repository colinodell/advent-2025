plugins {
    kotlin("jvm") version "2.1.0"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.2"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.junit.jupiter:junit-jupiter-engine:5.11.3")
    implementation("org.junit.jupiter:junit-jupiter-params:5.11.3")
    implementation("org.assertj:assertj-core:3.26.3")
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

tasks {
    wrapper {
        gradleVersion = "8.11.1"
    }
    test {
        useJUnitPlatform()
    }
}

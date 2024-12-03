plugins {
    kotlin("jvm") version "2.1.0"
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-test:2.1.0")
    implementation("org.jetbrains.kotlin:kotlin-test-junit5:2.1.0")

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
}

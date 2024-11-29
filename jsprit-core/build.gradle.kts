group = "tech.amelio"
version = "0.0.1-SNAPSHOT"

plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.11.3")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.8.2")

    implementation("org.apache.commons:commons-math3:3.4")
    implementation("org.slf4j:slf4j-api:2.0.16")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.hamcrest:hamcrest-core:1.3")
    testImplementation("org.mockito:mockito-all:1.9.5")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    jvmArgs = listOf(
        "--add-opens=java.base/java.lang=ALL-UNNAMED",
        "--add-opens=java.base/java.util=ALL-UNNAMED",
    )
}


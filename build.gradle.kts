plugins {
    kotlin("jvm") version "1.8.20" // 코틀린 버전 설정
    id("java")
}

group = "com.maijsoft.NOAFK"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    // Paper API 추가
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.named<Jar>("jar") {
    manifest {
        attributes(
            "Main-Class" to "com.maijsoft.NOAFK.Main" // 메인 클래스 지정
        )
    }
}
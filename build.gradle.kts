// 공개 API 모듈.
//
// 이 모듈은 인터페이스와 이벤트만 담는다. 구현체(플러그인 본체)에는 절대 의존하지 않는다.
// 그래야 다른 개발자가 이 jar 하나만 받아서 컴파일할 수 있고, 본체 소스를 공개하지 않아도 된다.
//
// 언어를 Java 로 둔 것도 같은 이유다. Kotlin 으로 만들면 이 API 를 쓰는 쪽이
// kotlin-stdlib 를 함께 넣어야 하고, 널 가능성 메타데이터까지 신경 써야 한다.
plugins {
    `java-library`
    `maven-publish`
}

group = "io.github.extra04"

// 고정값으로 둔다. 이 폴더는 플러그인 본체의 하위 모듈로도, 단독 저장소로도 빌드되는데
// rootProject.version 을 참조하면 단독 빌드(예: JitPack)에서 "unspecified" 가 된다.
//
// 본체 버전과 따로 움직인다. API 는 계약이라 본체가 바뀌어도 계약이 그대로면 올리지 않는다.
version = "1.0.0"

java {
    toolchain { languageVersion.set(JavaLanguageVersion.of(21)) }
    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    // Bukkit/Paper 외에는 아무것도 쓰지 않는다. 의존성이 늘면 API 로서의 가치가 떨어진다.
    compileOnly("io.papermc.paper:paper-api:1.21.8-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains:annotations:26.0.2")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc> {
    (options as StandardJavadocDocletOptions).encoding = "UTF-8"
    (options as StandardJavadocDocletOptions).charSet = "UTF-8"
    // Javadoc 의 접근성 경고까지 오류로 올리지 않는다. 문서를 못 만들 이유가 되면 안 된다.
    (options as StandardJavadocDocletOptions).addStringOption("Xdoclint:none", "-quiet")
}

tasks.withType<Jar> {
    // 모듈 이름이 "api" 라 그대로 두면 api-1.0.0.jar 가 된다. 내려받은 사람이
    // 무슨 파일인지 알아볼 수 있게 이름을 붙인다.
    archiveBaseName.set("trail-api")

    // 라이선스를 jar 안에 함께 넣는다. jar 만 받아 쓰는 사람도 조건을 알 수 있어야 한다.
    from(projectDir) {
        include("LICENSE")
        into("META-INF")
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            artifactId = "trail-api"
        }
    }
}

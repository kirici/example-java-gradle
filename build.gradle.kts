plugins {
    id("checkstyle")
    id("com.diffplug.spotless") version "6.20.0"
    id("com.github.spotbugs") version "5.0.14"
    id("java")
}
repositories {
    mavenCentral()
}
checkstyle {
    toolVersion = "10.12.2"
}
spotbugs {
    ignoreFailures.set(true)
    reportsDir.set(file("$buildDir/reports"))
}
spotless {
    isEnforceCheck = false
    java {
        googleJavaFormat()
    }
}
tasks.check {
    dependsOn(tasks.checkstyleMain)
}
tasks.spotbugsMain {
    reports {
        register("sarif") {
            required.set(true)
        }
    }
}
tasks.spotbugsTest {
    enabled = false
}
tasks.withType<Checkstyle>().configureEach {
    isIgnoreFailures = true
    reports {
        sarif.required.set(true)
        xml.required.set(false)
        html.required.set(false)
    }
    val archive = configurations.checkstyle.get().resolve().filter {
        it.name.startsWith("checkstyle")
    }
    config = resources.text.fromArchiveEntry(archive, "google_checks.xml")
}

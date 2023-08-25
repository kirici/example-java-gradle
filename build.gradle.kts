plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.22"
    id("java")
    id("com.diffplug.spotless") version "6.20.0"
    id("checkstyle")

    application
}
repositories {
    mavenCentral()
}
tasks.check {
    dependsOn(tasks.checkstyleMain)
}
checkstyle {
    toolVersion = "10.12.2"
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
spotless {
    isEnforceCheck = false
    java {
        googleJavaFormat()
    }
}
application {
    mainClass.set("demo.MainJava") 
}
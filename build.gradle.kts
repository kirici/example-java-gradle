plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.22"
    application
    checkstyle
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
}
application {
    mainClass.set("demo.MainJava") 
}

plugins {
    application
}


group = "${rootProject.group}.app"


application {
    mainClass.set("$group.Main")
}


dependencies {
    val junitVersion: String by project

    implementation(project(":business"))

    testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")
}


tasks.test {
    useJUnitPlatform()
}


tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

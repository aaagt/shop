plugins {
    application
}


group = "${rootProject.group}.app"


application {
    mainClass.set("$group.Main")
}


dependencies {
    val junitVersion: String by project

    implementation(":business")

    testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")
}


tasks.test {
    useJUnitPlatform()
}

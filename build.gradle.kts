plugins {
    idea
    java
    id("gg.essential.loom") version "1.10.+"
}

val modID: String by project
val modName: String by project
val modGroup: String by project
val modVersion: String by project
val mcVersion = "1.8.9"

group = modGroup
version = modVersion

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(8))
}

loom {
    runConfigs {
        remove(getByName("server"))
    }

    forge {
        pack200Provider.set(dev.architectury.pack200.java.Pack200Adapter())
        accessTransformer(rootProject.file("src/main/resources/${modID}_at.cfg"))
    }

    // For some reason loom defaults to tab indentation
    decompilers {
        named("vineflower") {
            options.put("indent-string", "    ")
        }
    }
}

repositories {
    mavenCentral()
    maven("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1")
}

dependencies {
    minecraft("com.mojang:minecraft:1.8.9")
    mappings("de.oceanlabs.mcp:mcp_stable:22-1.8.9")
    forge("net.minecraftforge:forge:1.8.9-11.15.1.2318-1.8.9")

    modRuntimeOnly("me.djtheredstoner:DevAuth-forge-legacy:1.2.1")
}

sourceSets.main {
    output.setResourcesDir(sourceSets.main.flatMap { it.java.classesDirectory })
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }

    processResources {
        inputs.property("modID", modID)
        inputs.property("modName", modName)
        inputs.property("version", version)
        inputs.property("mcVersion", mcVersion)

        filesMatching(listOf("mcmod.info")) {
            expand(inputs.properties) {
                escapeBackslash = true
            }
        }

        rename("(.+_at.cfg)", "META-INF/$1")
    }

    jar {
        manifest.attributes(mapOf(
            "FMLAT" to "${modID}_at.cfg"
        ))
    }
}

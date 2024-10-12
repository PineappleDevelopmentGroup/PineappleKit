package sh.miles.pineapplekit

import org.gradle.api.model.ObjectFactory
import sh.miles.pineapplekit.data.PineappleModule

abstract class PineappleKitExtension(factory: ObjectFactory) {
    val spigotVersion = factory.property(String::class.java)
    val mainPackage = factory.property(String::class.java)
    val modules = factory.listProperty(PineappleModule::class.java)

    fun module(name: String, version: String): PineappleModule {
        return PineappleModule(name, version, listOf(), false)
    }
}

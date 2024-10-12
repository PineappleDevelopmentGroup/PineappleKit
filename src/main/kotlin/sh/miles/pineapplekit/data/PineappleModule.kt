package sh.miles.pineapplekit.data

data class PineappleModule(
    val name: String,
    val version: String,
    val exclusionPattern: List<String>,
    val debugMode: Boolean
) {
    companion object {
        val BUNDLE = PineappleModule("pineapple-bundle", "1.0.0-SNAPSHOT", listOf(), false)
        val COMMON = PineappleModule("pineapple-common", "1.0.0-SNAPSHOT", listOf(), false)
        val INFSTACK = PineappleModule("pineapple-infstack", "1.0.0-SNAPSHOT", listOf(), false)
        val TILES = PineappleModule("pineapple-tiles", "1.0.0-SNAPSHOT", listOf(), false)
    }

    fun withVersion(version: String): PineappleModule {
        return PineappleModule(this.name, version, this.exclusionPattern, this.debugMode)
    }

    fun withName(name: String): PineappleModule {
        return PineappleModule(name, this.version, this.exclusionPattern, this.debugMode)
    }

    fun withExclusionPattern(exclusionPattern: List<String>): PineappleModule {
        return PineappleModule(this.name, this.version, exclusionPattern, this.debugMode)
    }

    fun withDebugMode(): PineappleModule {
        return PineappleModule(this.name, this.version, this.exclusionPattern, true)
    }
}

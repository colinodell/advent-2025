rootProject.name = "advent-2025"

plugins {
    id("org.danilopianini.gradle-pre-commit-git-hooks") version "2.0.15"
}

gitHooks {
    preCommit {
        from {
            """
            ./gradlew check
            """.trimIndent()
        }
    }
    createHooks(true)
}

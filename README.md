# core-storage

Android-library module providing local persistence for Sentinel Bank's mock banking data:
a Room database (accounts, cards, transactions), encrypted key-value storage for
session/token data, and a plain DataStore for non-sensitive app preferences. There is no
networking in this module — that's `core-network`'s job.

Part of the [Sentinel Bank](https://github.com/manojmourya2505) portfolio.

## Install

```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/manojmourya2505/sentinel-core-storage")
            credentials {
                username = providers.gradleProperty("gpr.user").getOrElse(System.getenv("GITHUB_ACTOR") ?: "")
                password = providers.gradleProperty("gpr.token").getOrElse(System.getenv("GITHUB_TOKEN") ?: "")
            }
        }
    }
}
```

```kotlin
// build.gradle.kts
dependencies {
    implementation("com.sentinelbank:core-storage:1.0.0")
}
```

## What's inside

### Room database (`db/`)

- `AccountEntity`, `CardEntity`, `TransactionEntity` — mock banking data. Money is always a
  `Long` in minor units (cents), never a floating point type.
- Sensitive columns (`accountNumber`, `maskedPan`) are wrapped in `EncryptedField` and routed
  through a `TypeConverter` at persistence time.
- `FieldCipher` — a functional interface the hosting app implements (typically backed by
  `core-security`'s Android Keystore) and installs via `FieldCipherProvider.cipher = ...` at
  startup. `core-storage` has no compile-time dependency on `core-security`; it defaults to a
  no-op passthrough so the module works standalone and in tests.
- `AccountDao`, `CardDao`, `TransactionDao` — suspend CRUD plus `Flow`-returning queries for
  reactive UI.
- `SentinelDatabase` — Room database tying it together, with `SentinelDatabase.getInstance(context)`.

### Encrypted preferences (`prefs/`)

- `SecurePrefsManager` — `EncryptedSharedPreferences` (AES256-GCM `MasterKey`) wrapper for
  session/token storage: `putString/getString/putBoolean/getBoolean/putLong/getLong/remove/clear`
  plus `saveSessionToken`/`getSessionToken`/`clearSession`.
- `AppPreferencesDataStore` — plain Jetpack DataStore Preferences wrapper for non-sensitive
  settings: `isOnboardingComplete`, `themeMode`, `sessionTimeoutMinutes` (each a `Flow` + setter).

### Helpers (`util/`)

- `MoneyFormatter` — minor-units-to-display-string / currency-string conversion.
- `CategoryInference` — keyword-based `TransactionCategory` inference for mock data.

## Build locally

```bash
./gradlew build
./gradlew publishToMavenLocal   # consume via mavenLocal() while iterating
```

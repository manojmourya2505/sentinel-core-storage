package com.sentinelbank.core.storage.db

/**
 * Interface for encrypting/decrypting individual Room column values at rest
 * (e.g. account numbers, masked PANs).
 *
 * `core-storage` deliberately has no compile-time dependency on `core-security` — per the
 * project's module rule that `core-*` modules only depend on `core-common`. The hosting app
 * wires a real implementation (backed by `core-security`'s Android Keystore) into
 * [FieldCipherProvider] at startup. Until then, [FieldCipherProvider.cipher] defaults to a
 * no-op passthrough so this module works standalone and in tests.
 */
interface FieldCipher {
    fun encrypt(plain: String): String
    fun decrypt(cipher: String): String
}

/**
 * Process-wide holder for the active [FieldCipher]. Set once at app startup (typically from
 * the Application class) after `core-security`'s crypto manager is initialized.
 */
object FieldCipherProvider {
    @Volatile
    var cipher: FieldCipher = object : FieldCipher {
        override fun encrypt(plain: String) = plain
        override fun decrypt(cipher: String) = cipher
    }
}

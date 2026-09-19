package com.sentinelbank.core.storage.db.converters

import androidx.room.TypeConverter
import com.sentinelbank.core.storage.db.FieldCipherProvider

/**
 * Plain-text wrapper for a column value that must be encrypted at rest via the injected
 * [com.sentinelbank.core.storage.db.FieldCipher] (see [FieldCipherProvider]). Application code
 * always sees the decrypted [value]; Room persists the ciphertext produced by
 * [EncryptedFieldConverters].
 */
data class EncryptedField(val value: String)

/**
 * Room [TypeConverter]s that route [EncryptedField] columns through the process-wide
 * [FieldCipherProvider]. Registered on [com.sentinelbank.core.storage.db.SentinelDatabase].
 */
class EncryptedFieldConverters {

    @TypeConverter
    fun toColumn(field: EncryptedField): String =
        FieldCipherProvider.cipher.encrypt(field.value)

    @TypeConverter
    fun fromColumn(raw: String): EncryptedField =
        EncryptedField(FieldCipherProvider.cipher.decrypt(raw))
}

package com.sentinelbank.core.storage.db

import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals

class FieldCipherProviderTest {

    @AfterTest
    fun resetToDefault() {
        FieldCipherProvider.cipher = object : FieldCipher {
            override fun encrypt(plain: String) = plain
            override fun decrypt(cipher: String) = cipher
        }
    }

    @Test
    fun `default cipher is a passthrough`() {
        assertEquals("plain-text", FieldCipherProvider.cipher.encrypt("plain-text"))
        assertEquals("plain-text", FieldCipherProvider.cipher.decrypt("plain-text"))
    }

    @Test
    fun `injected cipher is used once set`() {
        FieldCipherProvider.cipher = object : FieldCipher {
            override fun encrypt(plain: String) = "enc($plain)"
            override fun decrypt(cipher: String) = cipher.removeSurrounding("enc(", ")")
        }

        assertEquals("enc(secret)", FieldCipherProvider.cipher.encrypt("secret"))
        assertEquals("secret", FieldCipherProvider.cipher.decrypt("enc(secret)"))
    }
}

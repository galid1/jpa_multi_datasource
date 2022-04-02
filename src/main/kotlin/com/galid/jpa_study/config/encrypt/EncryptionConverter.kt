package com.galid.jpa_study.config.encrypt

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.Base64Utils
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Component
class EncryptionConverter(
    private val encryptionHelper: EncryptionHelper
) : AttributeConverter<String?, String?> {
    override fun convertToDatabaseColumn(attribute: String?): String? {
        if (attribute.isNullOrEmpty()) {
            return null
        }

        return encryptionHelper.encrypt(attribute)
    }

    override fun convertToEntityAttribute(dbData: String?): String? {
        if (dbData.isNullOrEmpty()) {
            return null
        }

        return encryptionHelper.decrypt(dbData)
    }
}

@Component
class EncryptionHelper(
    @Value("\${business-center.data-encryption-key}")
    val dataEncryptionKey: String
) {
    private val ivLength = 128
    private val algorithm = "AES/GCM/NoPadding"

    fun generateIv(): String {
        val iv = ByteArray(ivLength / 8)
        SecureRandom().nextBytes(iv)
        return iv.toString()
    }

    fun encrypt(plaintext: String): String {
        val keySpec = SecretKeySpec(dataEncryptionKey.toByteArray(), "AES")

        val iv = generateIv()
        val gcmParameterSpec = GCMParameterSpec(ivLength, iv.toByteArray())

        val encrypter = Cipher.getInstance(algorithm)
        encrypter.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec)

        val ciphertext = encrypter.doFinal(plaintext.toByteArray())
        val encodedCiphertext = Base64Utils.encodeToString(ciphertext)
        val encodedIv = Base64Utils.encodeToString(iv.toByteArray())

        return "$encodedIv:$encodedCiphertext"
    }

    fun decrypt(str: String): String {
        val keySpec = SecretKeySpec(dataEncryptionKey.toByteArray(), "AES")

        val encodedIv = str.split(":")[0]
        val encodedCiphertext = str.split(":")[1]
        val decodedIv = Base64Utils.decodeFromString(encodedIv)
        val decodedCiphertext = Base64Utils.decodeFromString(encodedCiphertext)

        val gcmParameterSpec = GCMParameterSpec(ivLength, decodedIv)
        val decrypter = Cipher.getInstance(algorithm)
        decrypter.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec)

        return decrypter.doFinal(decodedCiphertext).toString(Charsets.UTF_8)
    }
}
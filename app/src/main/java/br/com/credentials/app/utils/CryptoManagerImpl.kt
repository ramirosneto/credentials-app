package br.com.credentials.app.utils

import br.com.credentials.app.BuildConfig
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class CryptoManagerImpl : CryptoManager {

    private val key = SecretKeySpec(BuildConfig.SECRET_KEY.toByteArray(), ALGORITHM_TYPE)
    private val cipher = Cipher.getInstance(ALGORITHM_TYPE)

    override fun encrypt(data: String): String {
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val encryptedBytes = cipher.doFinal(data.toByteArray())
        return Base64.getEncoder().encodeToString(encryptedBytes)
    }

    override fun decrypt(encryptedData: String): String {
        cipher.init(Cipher.DECRYPT_MODE, key)
        val decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData))
        return String(decryptedBytes)
    }

    companion object {
        private const val ALGORITHM_TYPE = "AES"
    }
}
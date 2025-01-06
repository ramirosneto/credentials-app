package br.com.credentials.app.utils

interface CryptoManager {

    fun encrypt(data: String): String
    fun decrypt(encryptedData: String): String
}
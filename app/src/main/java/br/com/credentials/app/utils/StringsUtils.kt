package br.com.credentials.app.utils

val VALIDATION_STATUS_EXTRA = "status"
val ENCRYPTED_USER = "encryptedUser"
val ENCRYPTED_PASS = "encryptedstatus"

fun String.usernameIsCorrect() =
    "Teste01" == this

fun String.passwordIsCorrect() =
    "Mercantil" == this

fun String.joinToString(value: String) =
    this.plus(",\n$value")
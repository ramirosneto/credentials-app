package br.com.credentials.app.model.data

import br.com.credentials.app.R

enum class ValidationResult(val msg: String, val icon: Int) {
    SUCCESS(
        msg = "Autenticado com sucesso!",
        icon = R.drawable.ic_success
    ),
    ERROR(
        msg = "Usuário ou senha inválidos!",
        icon = R.drawable.ic_error
    ),
    EMPTY(
        msg = "",
        icon = 0
    )
}
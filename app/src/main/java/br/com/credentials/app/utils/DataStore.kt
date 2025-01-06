package br.com.credentials.app.utils

interface DataStore {

    fun writeAccessLog(data: String)
    fun readAccessLog(): String?
}
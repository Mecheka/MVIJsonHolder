package com.example.rules

interface InvalidImportRule {

    fun isAllowImport(
        visitingPackage: String,
        visitingClassName: String,
        importStatement: String
    ): Boolean

    fun getMessage(): String
}

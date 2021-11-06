package com.example.rules.invalidrule

import com.example.rules.InvalidImportRule
import com.example.rules.util.isDataPackage
import com.example.rules.util.isPresentationPackage

class InvalidPresentationToDataDependencyRule : InvalidImportRule {
    override fun isAllowImport(
        visitingPackage: String,
        visitingClassName: String,
        importStatement: String
    ): Boolean = !(visitingPackage.isPresentationPackage() && importStatement.isDataPackage())

    override fun getMessage(): String = "Data classes should not import from presentation package"
}

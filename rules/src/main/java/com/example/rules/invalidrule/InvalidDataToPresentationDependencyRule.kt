package com.example.rules.invalidrule

import com.example.rules.InvalidImportRule
import com.example.rules.util.isDataPackage
import com.example.rules.util.isPresentationPackage

class InvalidDataToPresentationDependencyRule : InvalidImportRule {
    override fun isAllowImport(
        visitingPackage: String,
        visitingClassName: String,
        importStatement: String
    ): Boolean = !(visitingPackage.isDataPackage() && importStatement.isPresentationPackage())

    override fun getMessage(): String = "Presentation classes should not import from data package"
}

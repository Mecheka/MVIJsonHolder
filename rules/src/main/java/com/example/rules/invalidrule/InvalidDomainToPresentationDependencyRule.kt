package com.example.rules.invalidrule

import com.example.rules.InvalidImportRule
import com.example.rules.util.isDomainPackage
import com.example.rules.util.isPresentationPackage

class InvalidDomainToPresentationDependencyRule : InvalidImportRule {
    override fun isAllowImport(
        visitingPackage: String,
        visitingClassName: String,
        importStatement: String
    ): Boolean = !(visitingPackage.isDomainPackage() && importStatement.isPresentationPackage())

    override fun getMessage(): String = "Presentation classes should not import from domain package"
}

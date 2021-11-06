package com.example.rules.invalidrule

import com.example.rules.InvalidImportRule
import com.example.rules.util.isDataPackage
import com.example.rules.util.isDomainPackage

class InvalidDataToDomainDependencyRule : InvalidImportRule {
    override fun isAllowImport(
        visitingPackage: String,
        visitingClassName: String,
        importStatement: String
    ): Boolean = !(visitingPackage.isDataPackage() && importStatement.isDomainPackage())

    override fun getMessage(): String = "Domain classes should not import from data package"
}

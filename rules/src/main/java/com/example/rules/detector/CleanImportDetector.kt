package com.example.rules.detector

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.example.rules.invalidrule.InvalidDataToDomainDependencyRule
import com.example.rules.invalidrule.InvalidDataToPresentationDependencyRule
import com.example.rules.invalidrule.InvalidDomainToPresentationDependencyRule
import com.example.rules.invalidrule.InvalidPresentationToDataDependencyRule
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UImportStatement
import org.jetbrains.uast.getContainingUFile
import java.util.EnumSet

@Suppress("UnstableApiUsage")
class CleanImportDetector : Detector(), Detector.UastScanner {

    override fun getApplicableUastTypes(): List<Class<out UElement>> =
        listOf(UImportStatement::class.java)

    private val rules = listOf(
        InvalidDataToDomainDependencyRule(),
        InvalidDataToPresentationDependencyRule(),
        InvalidDomainToPresentationDependencyRule(),
        InvalidPresentationToDataDependencyRule()
    )

    override fun createUastHandler(context: JavaContext): UElementHandler {
        return object : UElementHandler() {
            override fun visitImportStatement(node: UImportStatement) {
                node.importReference?.let { import ->
                    rules.forEach { rule ->
                        val visitingPackageName = import.getContainingUFile()?.packageName
                        val visitingClassName = context.file.nameWithoutExtension
                        val importedClass = import.asRenderString()
                        visitingPackageName?.let {
                            if (!rule.isAllowImport(
                                    visitingPackageName,
                                    visitingClassName,
                                    importedClass
                                )
                            ) {
                                context.report(
                                    ISSUE_IMPORT_DETECTOR,
                                    context.getLocation(import),
                                    rule.getMessage()
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val MESSAGE = "Lint detector for detecting invalid imports"
        val ISSUE_IMPORT_DETECTOR = Issue.create(
            id = "IncorrectImportDetector",
            briefDescription = MESSAGE,
            explanation = MESSAGE,
            category = Category.CORRECTNESS,
            priority = 9,
            severity = Severity.ERROR,
            implementation = Implementation(
                CleanImportDetector::class.java,
                EnumSet.of(Scope.ALL_JAVA_FILES)
            )
        )
    }
}

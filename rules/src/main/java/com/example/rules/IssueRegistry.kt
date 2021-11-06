package com.example.rules

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import com.example.rules.detector.CleanImportDetector

@Suppress("UnstableApiUsage")
class IssueRegistry : IssueRegistry() {

    override val api: Int = CURRENT_API
    override val issues: List<Issue>
        get() = listOf(CleanImportDetector.ISSUE_IMPORT_DETECTOR)
}

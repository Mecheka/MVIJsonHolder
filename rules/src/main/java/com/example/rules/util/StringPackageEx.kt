package com.example.rules.util

import com.example.rules.DATA_PACKAGE
import com.example.rules.DOMAIN_PACKAGE
import com.example.rules.PRESENTATION_PACKAGE

fun String.isDataPackage(): Boolean = (!this.isDataBindingPackage() && this.contains(".$DATA_PACKAGE")) || this.endsWith(
    DATA_PACKAGE
)

fun String.isDataBindingPackage(): Boolean = this.contains(".databinding")

fun String.isDomainPackage(): Boolean = this.contains(".$DOMAIN_PACKAGE") || this.endsWith(
    DOMAIN_PACKAGE
)

fun String.isPresentationPackage(): Boolean =
    this.contains(".$PRESENTATION_PACKAGE") || this.endsWith(
        PRESENTATION_PACKAGE
    )

package br.com.github.ui.common.extensions

internal inline fun <reified AnyT> Any.cast() = this as AnyT

internal inline fun <reified AnyT> Any.safeCast() = this as? AnyT

internal fun <AnyT> AnyT?.getOrDefault(default: AnyT): AnyT = this ?: default

internal fun <AnyT> AnyT?.isNotNull() = this != null

internal fun <AnyT> AnyT.singletonList() = listOf(this)

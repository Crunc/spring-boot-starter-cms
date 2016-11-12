package com.github.crunc.springboot.cms.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

/**
 * Creates a Logger for the given class
 */
internal fun <T : Any> loggerFor(loggingClass: KClass<T>): Logger = LoggerFactory.getLogger(loggingClass.java)

package com.github.crunc.springboot.cms.filesystem

import com.github.crunc.springboot.cms.logging.loggerFor
import org.eclipse.jgit.util.FileUtils
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import kotlin.concurrent.thread

class TempDirectory(prefix: String, destroyOnShutdown: Boolean = true) {

    private val log = loggerFor(TempDirectory::class)

    val path: Path

    val file: File
        get() = path.toFile()

    init {
        try {
            path = Files.createTempDirectory(prefix).toAbsolutePath()

            if (destroyOnShutdown) {
                Runtime.getRuntime().addShutdownHook(thread(start = false) {
                    try {
                        FileUtils.delete(file, FileUtils.RECURSIVE)
                    } catch (e: IOException) {
                        log.warn("failed to delete temporary directory at $path")
                    }
                })
            }
        } catch (e: IOException) {
            throw IllegalStateException("failed to create temporary directory with prefix $prefix")
        }
    }

    override fun toString(): String {
        return path.toString()
    }
}
package com.github.crunc.springboot.cms.content

import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.nio.charset.Charset
import java.nio.file.Path
import java.nio.file.Paths

/**
 * Provides CMS content (files)
 */
interface CmsContentRepository {

    fun open(path: Path): InputStream

    fun open(path: String): InputStream = open(Paths.get(path))

    fun read(path: Path, charset: Charset): Reader = InputStreamReader(open(path), charset)

    fun read(path: Path): Reader = read(path, Charsets.UTF_8)

    fun read(path: String, charset: Charset): Reader = read(Paths.get(path), charset)

    fun read(path: String): Reader = read(Paths.get(path))
}


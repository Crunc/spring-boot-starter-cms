package com.github.crunc.springboot.cms.content.git

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.lib.RepositoryBuilder
import org.eclipse.jgit.lib.RepositoryCache
import org.eclipse.jgit.util.FS
import java.io.Closeable
import java.io.File
import java.nio.file.Path


class ClosableGit(repository: Repository) : Git(repository), Closeable, AutoCloseable {

    companion object {

        @JvmStatic
        fun wrap(repository: Repository): ClosableGit = ClosableGit(repository)

        @JvmStatic
        fun open(dir: File, fs: FS): ClosableGit {
            val key: RepositoryCache.FileKey = RepositoryCache.FileKey.lenient(dir, fs)

            return wrap(RepositoryBuilder()
                    .setFS(fs)
                    .setGitDir(key.file)
                    .setMustExist(true)
                    .build())
        }

        @JvmStatic
        fun open(dir: File): ClosableGit = open(dir, FS.DETECTED)

        @JvmStatic
        fun open(dir: Path): ClosableGit = open(dir.toFile(), FS.DETECTED)
    }
}
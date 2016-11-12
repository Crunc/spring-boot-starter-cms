package com.github.crunc.springboot.cms.content.git

import com.github.crunc.springboot.cms.CmsProperties
import com.github.crunc.springboot.cms.content.CmsContentRepository
import com.github.crunc.springboot.cms.filesystem.TempDirectory
import com.github.crunc.springboot.cms.logging.loggerFor
import org.eclipse.jgit.api.Git
import org.springframework.beans.factory.InitializingBean
import org.springframework.util.Assert
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path

/**
 * JGit based [CmsContentRepository]
 */
class JGitCmsContentRepository(val props: CmsProperties) : CmsContentRepository, InitializingBean {

    private val log = loggerFor(JGitCmsContentRepository::class)
    private val baseDir: TempDirectory

    init {
        baseDir = TempDirectory(prefix = "cms-content-", destroyOnShutdown = true)
//        SshSessionFactory.setInstance(object : JschConfigSessionFactory() {
//            override fun configure(hc: OpenSshConfig.Host, session: Session) {
//                session.setConfig("StrictHostKeyChecking", "no")
//            }
//        })
    }

    override fun open(path: Path): InputStream {

        Assert.isTrue(!path.isAbsolute, "can not open absolute path $path")

        openRepository().use { git ->

            val ref = git.checkoutLabel(props.git.label)

            log.info("checked out ${ref.objectId.name}")

            val filePath = baseDir.path.resolve(path)
            return Files.newInputStream(filePath)
        }
    }

    override fun afterPropertiesSet() {
        log.info("initializing from Git repository ${props.git.uri}")

        if (props.git.cloneOnStartup) {
            synchronized(baseDir) {
                cloneRemoteRepository()
            }
        }
    }

    private fun openRepository(): ClosableGit {
        synchronized(baseDir) {
            if (Files.exists(baseDir.path.resolve(".git"))) {
                return ClosableGit.open(baseDir.path)
            } else {
                cloneRemoteRepository()
                return ClosableGit.open(baseDir.path)
            }
        }
    }

    private fun cloneRemoteRepository() {

        log.info("cloning Git repository into $baseDir")

        Git.cloneRepository()
                .setURI(props.git.uri)
                .setDirectory(baseDir.file)
                .setTimeout(props.git.timeout)
                .call()
                .close()
    }

    private fun git(): ClosableGit = ClosableGit.open(baseDir.file)
}

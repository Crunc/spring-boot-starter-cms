package com.github.crunc.springboot.cms

import com.github.crunc.springboot.cms.content.CmsContentRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import java.io.Reader

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(App::class))
class CmsAutoConfigurationTest {

    @Autowired
    lateinit var repository: CmsContentRepository

    @Test
    fun run(): Unit {

        val content = repository.read("content/sample.html").use(Reader::readText)

        println(content)
    }
}
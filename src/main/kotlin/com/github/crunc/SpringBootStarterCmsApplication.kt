package com.github.crunc

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class SpringBootStarterCmsApplication

fun main(args: Array<String>) {
    SpringApplication.run(SpringBootStarterCmsApplication::class.java, *args)
}

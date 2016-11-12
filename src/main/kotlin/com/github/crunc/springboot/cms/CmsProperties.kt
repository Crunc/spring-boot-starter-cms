package com.github.crunc.springboot.cms

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("spring.cms")
open class CmsProperties {

    var git = Git()

    class Git {

        var uri: String = "https://github.com/Crunc/spring-boot-cms-sample-content.git"
        var cloneOnStartup = true
        var timeout: Int = 5
        var label: String = "master"
    }
}
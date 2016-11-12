package com.github.crunc.springboot.cms

import com.github.crunc.springboot.cms.content.CmsContentRepository
import com.github.crunc.springboot.cms.content.git.JGitCmsContentRepository
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(CmsProperties::class)
open class CmsAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(CmsContentRepository::class)
    open fun cmsContentRepository(props:CmsProperties): CmsContentRepository
            = JGitCmsContentRepository(props)
}


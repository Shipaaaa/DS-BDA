package ru.shipa.elasticsearch.sample.config

import org.apache.http.client.HttpClient
import org.apache.http.client.config.CookieSpecs
import org.apache.http.client.config.RequestConfig
import org.apache.http.impl.client.HttpClients
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.social.twitter.api.Twitter
import org.springframework.social.twitter.api.impl.TwitterTemplate


/**
 * Twitter config for connecting to Twitter stream api
 *
 * @author v.shipugin
 */
@Configuration
class TwitterConfig {

    private val logger: Logger = LogManager.getLogger("TwitterConfig")

    @Value("\${spring.social.twitter.appId}")
    private lateinit var consumerKey: String

    @Value("\${spring.social.twitter.appSecret}")
    private lateinit var consumerSecret: String

    @Value("\${twitter.access.token}")
    private lateinit var accessToken: String

    @Value("\${twitter.access.token.secret}")
    private lateinit var accessTokenSecret: String

    /**
     * Create bean of TwitterTemplate for connecting to stream
     * @return TwitterTemplate
     */
    @Bean
    fun getTwitter(): Twitter {
        return TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret).apply {
            setRequestFactory(getClientHttpRequestFactory())
            logger.info("isAuthorized $isAuthorized")
        }
    }

    private fun getClientHttpRequestFactory(): HttpComponentsClientHttpRequestFactory {
        return HttpComponentsClientHttpRequestFactory().apply {
            httpClient = this@TwitterConfig.getHttpClient()
        }
    }

    private fun getHttpClient(): HttpClient {
        return HttpClients
            .custom()
            .setDefaultRequestConfig(
                RequestConfig
                    .custom()
                    .setCookieSpec(CookieSpecs.STANDARD)
                    .build()
            )
            .build()
    }
}

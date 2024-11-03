package com.plugin.httprundecknotificationplugin


import spock.lang.Specification

/**
 * Unit tests for HttpRundecknotificationplugin, covering all supported HTTP methods
 * and testing for both successful and unsuccessful cases.
 */
class HttpRundecknotificationpluginSpec extends Specification {
    //Some Possible trigger names
    public static final String TRIGGER_START = "start"
    public static final String TRIGGER_SUCCESS = "success"
    public static final String TRIGGER_FAILURE = "failure"
    public static final String BASE_URL = "https://httpbin.org/"


    /**
     * Sample execution data for testing.
     * @return a map with execution data properties
     */
    Map<String, Object> sampleExecutionData() {
        [
                id                     : 1,
                href                   : 'http://example.com/dummy/execution/1',
                status                 : 'succeeded',
                user                   : 'rduser',
                dateStarted            : new Date(0),
                'dateStartedUnixtime'  : 0,
                'dateStartedW3c'       : '1970-01-01T00:00:00Z',
                dateEnded              : new Date(10000),
                'dateEndedUnixtime'    : 10000,
                'dateEndedW3c'         : '1970-01-01T00:00:10Z',
                description            : 'a job',
                argstring              : '-opt1 value',
                project                : 'rdproject1',
                succeededNodeListString: 'nodea,nodeb',
                succeededNodeList      : ['nodea', 'nodeb'],
                loglevel               : 'INFO'
        ]
    }

    /**
     * Test for HTTP POST request success.
     */
    def "POST request with valid URL"() {
        given:
        HttpRundecknotificationplugin plugin = new HttpRundecknotificationplugin()
        plugin.remoteUrl = BASE_URL + "/post"
        plugin.method = "POST"
        plugin.contentType = "application/json"
        plugin.body = "{\"name\": \"Test\", \"role\": \"Developer\"}"

        when:
        def result = plugin.postNotification(TRIGGER_SUCCESS, sampleExecutionData(), [:])

        then:
        result
    }

    /**
     * Test for HTTP GET request success.
     */
    def "GET request with valid URL"() {
        given:
        HttpRundecknotificationplugin plugin = new HttpRundecknotificationplugin()
        plugin.remoteUrl = BASE_URL + "/get"
        plugin.method = "GET"
        plugin.contentType = "application/json"
        plugin.body = ""

        when:
        def result = plugin.postNotification(TRIGGER_SUCCESS, sampleExecutionData(), [:])

        then:
        result
    }

    /**
     * Test for HTTP PUT request success.
     */
    def "PUT request with valid URL"() {
        given:
        HttpRundecknotificationplugin plugin = new HttpRundecknotificationplugin()
        plugin.remoteUrl = BASE_URL + "/put"
        plugin.method = "PUT"
        plugin.contentType = "application/json"
        plugin.body = "{\"update\": \"true\"}"

        when:
        def result = plugin.postNotification(TRIGGER_SUCCESS, sampleExecutionData(), [:])

        then:
        result
    }

    /**
     * Test for HTTP DELETE request success.
     */
    def "DELETE request with valid URL"() {
        given:
        HttpRundecknotificationplugin plugin = new HttpRundecknotificationplugin()
        plugin.remoteUrl = BASE_URL + "/delete"
        plugin.method = "DELETE"
        plugin.contentType = "application/json"
        plugin.body = ""

        when:
        def result = plugin.postNotification(TRIGGER_SUCCESS, sampleExecutionData(), [:])

        then:
        result
    }

    /**
     * Test for HTTP PATCH request success.
     */
    def "PATCH request with valid URL"() {
        given:
        HttpRundecknotificationplugin plugin = new HttpRundecknotificationplugin()
        plugin.remoteUrl = BASE_URL + "/patch"
        plugin.method = "PATCH"
        plugin.contentType = "application/json"
        plugin.body = "{\"name\": \"Patched\"}"

        when:
        def result = plugin.postNotification(TRIGGER_SUCCESS, sampleExecutionData(), [:])

        then:
        result
    }

    /**
     * Test for HTTP HEAD request success.
     */
    def "HEAD request with valid URL"() {
        given:
        HttpRundecknotificationplugin plugin = new HttpRundecknotificationplugin()
        plugin.remoteUrl = BASE_URL + "/get"
        plugin.method = "HEAD"
        plugin.contentType = "application/json"
        plugin.body = ""

        when:
        def result = plugin.postNotification(TRIGGER_SUCCESS, sampleExecutionData(), [:])

        then:
        result
    }

    /**
     * Test for HTTP CONNECT request failure with exception, since it's not supported by the plugin.
     */
    def "CONNECT request with valid URL"() {
        given:
        HttpRundecknotificationplugin plugin = new HttpRundecknotificationplugin()
        plugin.remoteUrl = BASE_URL + "/connect"
        plugin.method = "CONNECT"
        plugin.contentType = "application/json"
        plugin.body = ""

        when:
        plugin.postNotification(TRIGGER_SUCCESS, sampleExecutionData(), [:])

        then:
        thrown(IllegalArgumentException)
    }

    /**
     * Test for HTTP OPTIONS request failure with exception, since it's not supported by the plugin.
     */
    def "OPTIONS request with valid URL"() {
        given:
        HttpRundecknotificationplugin plugin = new HttpRundecknotificationplugin()
        plugin.remoteUrl = BASE_URL + "/options"
        plugin.method = "OPTIONS"
        plugin.contentType = "application/json"
        plugin.body = ""

        when:
        plugin.postNotification(TRIGGER_SUCCESS, sampleExecutionData(), [:])

        then:
        thrown(IllegalArgumentException)
    }

    /**
     * Test for HTTP TRACE request failure with exception, since it's not supported by the plugin.
     */
    def "TRACE request with valid URL"() {
        given:
        HttpRundecknotificationplugin plugin = new HttpRundecknotificationplugin()
        plugin.remoteUrl = BASE_URL + "/trace"
        plugin.method = "TRACE"
        plugin.contentType = "application/json"
        plugin.body = ""

        when:
        plugin.postNotification(TRIGGER_SUCCESS, sampleExecutionData(), [:])

        then:
        thrown(IllegalArgumentException)
    }

    /**
     * Test with an invalid URL to verify error handling.
     */
    def "Invalid URL"() {
        given:
        HttpRundecknotificationplugin plugin = new HttpRundecknotificationplugin()
        plugin.remoteUrl = "https://invalidurl"
        plugin.method = "POST"
        plugin.contentType = "application/json"
        plugin.body = "{\"name\": \"Test\"}"

        when:
        def result = plugin.postNotification(TRIGGER_SUCCESS, sampleExecutionData(), [:])

        then:
        !result
    }

}
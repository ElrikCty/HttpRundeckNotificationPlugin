package com.plugin.httprundecknotificationplugin

import com.dtolabs.rundeck.core.plugins.Plugin
import com.dtolabs.rundeck.plugins.descriptions.PluginDescription
import com.dtolabs.rundeck.plugins.descriptions.PluginProperty
import com.dtolabs.rundeck.plugins.descriptions.SelectValues
import com.dtolabs.rundeck.plugins.notification.NotificationPlugin
import okhttp3.Request
import okhttp3.Response
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * A Rundeck Notification Plugin that supports various HTTP methods
 * to send notifications to a specified endpoint.
 */
@Plugin(service="Notification", name="httprundecknotificationplugin")
@PluginDescription(title="HttpRundeckNotificationPlugin", description="This is a notification plugin that supports different HTTP methods.")
public class HttpRundecknotificationplugin implements NotificationPlugin{

    private static final Logger logger = LoggerFactory.getLogger(HttpRundecknotificationplugin.class)
    private HttpRequestBuilder httpRequestBuilder = new HttpRequestBuilder()


    @PluginProperty(name = "remoteUrl" ,
            title = "Remote URL",
            description = "HTTP URL to be used to make the request.",
            required = true)
    String remoteUrl

    @PluginProperty(name = "method" ,
            title = "HTTP method",
            description = "HTTP method to be used in the request.",
            required = true)
    @SelectValues(values = ["POST", "GET", "PUT", "DELETE", "PATCH", "HEAD"])
    String method

    @PluginProperty(name = "contentType" ,
            title = "Content Type",
            description = "HTTP Content Type to be used in the request.",
            required = false,
            defaultValue = "application/json")
    @SelectValues(values = ["application/json", "application/xml", "text/plain", "application/x-www-form-urlencoded", "multipart/form-data"], freeSelect = true)
    String contentType

    @PluginProperty(name = "body" ,
            title = "Body Content",
            description = "HTTP Body Content to be used in the request.",
            required = false)
    String body

    /**
     * Sends an HTTP request as a notification based on the configured properties.
     *
     * @param trigger the trigger type for the notification (e.g., "onstart", "onsuccess", "onfailure", etc)
     * @param executionData the data associated with the execution, such as job ID, status, and other metadata
     * @param config any additional configuration for the plugin
     * @return true if the request was successful; false otherwise
     */
    @Override
    public boolean postNotification(String trigger, Map executionData, Map config) {

        if (!remoteUrl) {
            logger.error("Remote URL is required for the notification.")
            return false
        }

        if (!method) {
            logger.error("HTTP method is required for the notification.")
            return false
        }


        try {
            Request request = httpRequestBuilder.buildRequest(remoteUrl, method, contentType, body)
            logger.info("Sending HTTP {} request to {}", method, remoteUrl)

            try (Response response = httpRequestBuilder.executeRequest(request)) {
                boolean success = response.isSuccessful()
                logger.info("Received HTTP response: {} - {}", response.code(), response.message())
                return success
            }
        } catch (IOException e) {
            logger.error("Error executing HTTP request: {}", e.message)
            return false
        }

    }
}
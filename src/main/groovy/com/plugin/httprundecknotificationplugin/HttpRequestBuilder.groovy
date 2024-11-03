package com.plugin.httprundecknotificationplugin

import okhttp3.*

/**
 * A utility class to build and execute HTTP requests.
 * Supports multiple HTTP methods and content types.
 */
class HttpRequestBuilder {
    private static final OkHttpClient httpClient = new OkHttpClient()

    /**
     * Builds an HTTP request with the specified method, URL, content type, and body content.
     * @param url the target URL for the request
     * @param method the HTTP method to use (e.g., GET, POST)
     * @param contentType the MIME type of the request content (if applicable)
     * @param bodyContent the request body content
     * @return the built Request object
     * @throws IllegalArgumentException if the method is unsupported or if required parameters are missing
     */
    Request buildRequest(String url, String method, String contentType, String bodyContent) {
        if (!url) throw new IllegalArgumentException("URL cannot be null or empty.")
        if (!method) throw new IllegalArgumentException("HTTP method cannot be null or empty.")

        def builder = new Request.Builder().url(url)
        RequestBody body = null


        if (!method.equalsIgnoreCase("GET") && bodyContent) {
            if (!contentType) throw new IllegalArgumentException("Content type required for methods with a body.")
            body = RequestBody.create(MediaType.parse(contentType), bodyContent)
        }

        //CONNECT, TRACE and OPTIONS are not fully supported.
        switch (method.toUpperCase()) {
            case "POST":
                builder.post(body)
                break
            case "PUT":
                builder.put(body)
                break
            case "DELETE":
                builder.delete(body)
                break
            case "PATCH":
                builder.patch(body)
                break
            case "HEAD":
                builder.head()
                break
            case "GET":
                builder.get()
                break
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: $method")
        }

        return builder.build()
    }

    /**
     * Executes the given HTTP request and returns the response.
     * This method should be called within a try-with-resources block to ensure proper resource management.
     * @param request the HTTP request to execute
     * @return the HTTP response
     * @throws IOException if an error occurs during request execution
     */
    static Response executeRequest(Request request) {
        httpClient.newCall(request).execute()
    }
}

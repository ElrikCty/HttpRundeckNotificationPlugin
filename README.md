# HTTP Rundeck Notification Plugin

A Rundeck Notification Plugin that sends HTTP requests (e.g., `POST`, `GET`, `PUT`, `DELETE`) to a specified URL as a notification. This plugin supports multiple HTTP methods and configurable request bodies, allowing seamless integration with various HTTP-based services.

## Features

- Supports HTTP methods: `GET`, `POST`, `PUT`, `DELETE`, `PATCH`, `HEAD`
- Configurable content type and request body
- Detailed logging for debugging
- Handling of unsupported methods

---

## Table of Contents

- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [Examples](#examples)
- [Testing](#testing)


---

## Installation

1. **Build the Plugin**:
   - Clone the repository and navigate to the plugin directory.
   - Use Gradle to build the JAR file:
     ```bash
     ./gradlew build
     ```
   - The JAR file will be generated in the `build/libs` directory.

2. **Install the Plugin**:
   - Copy the generated JAR file to Rundeck’s `libext` directory (typically located at `/var/lib/rundeck/libext` on a Linux server).
   - Restart Rundeck to load the new plugin:
     ```bash
     sudo service rundeckd restart
     ```

## Configuration

Once installed, the plugin can be configured in the Rundeck UI when creating or editing a job. The following properties are available:

- **Remote URL**: The URL where the HTTP request will be sent. This field is required.
- **HTTP Method**: The HTTP method to use for the request. Options include:
  - `POST`, `GET`, `PUT`, `DELETE`, `PATCH`, `HEAD`
- **Content Type**: The content type of the request body. Default is `application/json` if a body is provided.
  - Options include `application/json`, `application/xml`, `text/plain`, `application/x-www-form-urlencoded`, `multipart/form-data`.
- **Body Content**: The content of the HTTP request body (e.g., JSON, XML, plain text). Required for methods that send data (like `POST` or `PUT`).

## Usage

The plugin can be used as a notification in a Rundeck job. It sends HTTP requests based on the configured properties, allowing you to trigger HTTP-based workflows upon job completion, start, or failure.

1. **Create/Edit a Job** in Rundeck.
2. **Add a Notification** step.
3. Select the **HTTP Rundeck Notification Plugin** and configure the properties as described above.
4. Save and execute the job.

## Examples

### Example 1: POST Request with JSON Body

- **Remote URL**: `https://example.com/api/notify`
- **Method**: `POST`
- **Content Type**: `application/json`
- **Body Content**: `{"jobName": "TestJob", "status": "success"}`

### Example 2: GET Request 

- **Remote URL**: `https://example.com/api/check`
- **Method**: `GET`

### Example 3: PUT Request with XML Content

- **Remote URL**: `https://example.com/api/update`
- **Method**: `PUT`
- **Content Type**: `application/xml`
- **Body Content**: `<update><id>123</id><status>completed</status></update>`

## Testing

Run unit tests with Gradle:

```bash
./gradlew test

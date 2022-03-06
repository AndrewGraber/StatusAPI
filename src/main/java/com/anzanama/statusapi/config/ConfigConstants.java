package com.anzanama.statusapi.config;

public class ConfigConstants {
    public static final String CATEGORY_GENERAL = "General Settings";
    public static final String CATEGORY_HTTP_SERVER = "HTTP Server";
    public static final String CATEGORY_HTTP_CLIENT = "HTTP Client";
    public static final String CATEGORY_DATABASE = "Database";

    public static final String NAME_HTTP_SERVER = "use_http_server";
    public static final String DEFAULT_HTTP_SERVER = "true";
    public static final String COMMENT_HTTP_SERVER = "Whether the internal http_server should be activated (Default: true)";

    public static final String NAME_HTTP_CLIENT = "use_http_client";
    public static final String DEFAULT_HTTP_CLIENT = "false";
    public static final String COMMENT_HTTP_CLIENT = "Whether the event driven http_client should be activated (Default: false)";

    public static final String NAME_DATABASE = "use_database";
    public static final String DEFAULT_DATABASE = "false";
    public static final String COMMENT_DATABASE = "Whether the mod should log data to a mysql database (Default: false)";

    public static final String NAME_SERVER_PORT = "server_port";
    public static final String DEFAULT_SERVER_PORT = "9321";
    public static final String COMMENT_SERVER_PORT = "The port to bind to for listening for requests (only used when communication_method is 'http_server')";

    public static final String NAME_CLIENT_ADDRESS = "client_address";
    public static final String DEFAULT_CLIENT_ADDRESS = "localhost";
    public static final String COMMENT_CLIENT_ADDRESS = "The address to send requests to (only used when communication_method is 'http_clent')";

    public static final String NAME_CLIENT_PORT = "client_port";
    public static final String DEFAULT_CLIENT_PORT = "9322";
    public static final String COMMENT_CLIENT_PORT = "The port to send requests to (only used when communication_method is 'http_client')";
}

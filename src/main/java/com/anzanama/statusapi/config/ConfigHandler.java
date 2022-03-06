package com.anzanama.statusapi.config;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {
    public static void syncConfig(Configuration config) {
        try {
            config.load();

            ConfigVals.use_http_server = config.get(ConfigConstants.CATEGORY_HTTP_SERVER,
                    ConfigConstants.NAME_HTTP_SERVER, ConfigConstants.DEFAULT_HTTP_SERVER,
                    ConfigConstants.COMMENT_HTTP_SERVER).getBoolean();

            ConfigVals.use_http_client = config.get(ConfigConstants.CATEGORY_HTTP_CLIENT,
                    ConfigConstants.NAME_HTTP_CLIENT, ConfigConstants.DEFAULT_HTTP_CLIENT,
                    ConfigConstants.COMMENT_HTTP_CLIENT).getBoolean();

            ConfigVals.use_database = config.get(ConfigConstants.CATEGORY_DATABASE,
                    ConfigConstants.NAME_DATABASE, ConfigConstants.DEFAULT_DATABASE,
                    ConfigConstants.COMMENT_DATABASE).getBoolean();

            ConfigVals.server_port = config.get(ConfigConstants.CATEGORY_HTTP_SERVER,
                    ConfigConstants.NAME_SERVER_PORT, ConfigConstants.DEFAULT_SERVER_PORT,
                    ConfigConstants.COMMENT_SERVER_PORT).getInt();

            ConfigVals.client_address = config.get(ConfigConstants.CATEGORY_HTTP_CLIENT,
                    ConfigConstants.NAME_CLIENT_ADDRESS, ConfigConstants.DEFAULT_CLIENT_ADDRESS,
                    ConfigConstants.COMMENT_CLIENT_ADDRESS).getString();

            ConfigVals.client_port = config.get(ConfigConstants.CATEGORY_HTTP_CLIENT,
                    ConfigConstants.NAME_CLIENT_PORT, ConfigConstants.DEFAULT_CLIENT_PORT,
                    ConfigConstants.COMMENT_CLIENT_PORT).getInt();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(config.hasChanged()) config.save();
        }
    }
}

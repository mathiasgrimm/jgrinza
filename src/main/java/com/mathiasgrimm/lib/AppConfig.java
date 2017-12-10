package com.mathiasgrimm.lib;

import org.json.JSONObject;

/**
 * this class is just a wrapper for the JSONObject so that it can be automatically injected
 * when defining a constructor parameter as AppConfig
 */

public class AppConfig {

    private JSONObject config;

    public AppConfig(JSONObject config)
    {
        this.config = config;
    }

    public JSONObject get() {
        return this.config;
    }
}

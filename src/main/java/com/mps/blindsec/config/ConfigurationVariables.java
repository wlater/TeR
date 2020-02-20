package com.mps.blindsec.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.Data;
import lombok.var;

@ConstructorBinding
@ConfigurationProperties(prefix = "environments.dev")
@Data
public class ConfigurationVariables{
    public static var PUBLIC_KEY_NAME;
    public static var ACTUAL_STORAGE_PATH;
    public static var SALT;

}
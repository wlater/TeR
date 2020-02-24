package com.mps.blindsec.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.var;

@ConfigurationProperties
@Data
@NoArgsConstructor
public class ConfigurationVariables{

    private var PUBLIC_KEY_NAME;

    private var ACTUAL_STORAGE_PATH;
    
    private var SALT;

}
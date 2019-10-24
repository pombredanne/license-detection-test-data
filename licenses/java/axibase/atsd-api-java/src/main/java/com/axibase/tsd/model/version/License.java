package com.axibase.tsd.model.version;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class License {
    private boolean forecastEnabled;
    private int hbaseServers;
    private boolean remoteHbase;
    private String productVersion;
    private boolean dataVersioningEnabled;
    private Long forecastExpirationTime;
    private Long dataVersioningExpirationTime;
}

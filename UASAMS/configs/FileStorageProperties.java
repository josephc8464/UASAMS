package org.team2.UASAMS.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.upload")
public class FileStorageProperties {

    /**
     * Directory path where uploaded application documents are stored.
     */
    private String dir = "uploads/applications";

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }
}
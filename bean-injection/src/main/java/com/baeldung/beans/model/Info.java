package com.baeldung.beans.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Info {
    private String name;
    private String build;
    private String support;
    private String version;
    private String description;
    private String authorization_endpoint;
    private String token_endpoint;
    private String min_cli_version;
    private String min_recommended_cli_version;
    private String api_version;
    private String app_ssh_endpoint;
    private String app_ssh_host_key_fingerprint;
    private String app_ssh_oauth_client;
    private String doppler_logging_endpoint;
    private String routing_endpoint;

    public Info(String name, String build, String support, String version, String description, String authorization_endpoint, String token_endpoint, String min_cli_version, String min_recommended_cli_version, String api_version, String app_ssh_endpoint, String app_ssh_host_key_fingerprint, String app_ssh_oauth_client, String doppler_logging_endpoint, String routing_endpoint) {
        this.name = name;
        this.build = build;
        this.support = support;
        this.version = version;
        this.description = description;
        this.authorization_endpoint = authorization_endpoint;
        this.token_endpoint = token_endpoint;
        this.min_cli_version = min_cli_version;
        this.min_recommended_cli_version = min_recommended_cli_version;
        this.api_version = api_version;
        this.app_ssh_endpoint = app_ssh_endpoint;
        this.app_ssh_host_key_fingerprint = app_ssh_host_key_fingerprint;
        this.app_ssh_oauth_client = app_ssh_oauth_client;
        this.doppler_logging_endpoint = doppler_logging_endpoint;
        this.routing_endpoint = routing_endpoint;
    }

    public Info() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthorization_endpoint() {
        return authorization_endpoint;
    }

    public void setAuthorization_endpoint(String authorization_endpoint) {
        this.authorization_endpoint = authorization_endpoint;
    }

    public String getToken_endpoint() {
        return token_endpoint;
    }

    public void setToken_endpoint(String token_endpoint) {
        this.token_endpoint = token_endpoint;
    }

    public String getMin_cli_version() {
        return min_cli_version;
    }

    public void setMin_cli_version(String min_cli_version) {
        this.min_cli_version = min_cli_version;
    }

    public String getMin_recommended_cli_version() {
        return min_recommended_cli_version;
    }

    public void setMin_recommended_cli_version(String min_recommended_cli_version) {
        this.min_recommended_cli_version = min_recommended_cli_version;
    }

    public String getApi_version() {
        return api_version;
    }

    public void setApi_version(String api_version) {
        this.api_version = api_version;
    }

    public String getApp_ssh_endpoint() {
        return app_ssh_endpoint;
    }

    public void setApp_ssh_endpoint(String app_ssh_endpoint) {
        this.app_ssh_endpoint = app_ssh_endpoint;
    }

    public String getApp_ssh_host_key_fingerprint() {
        return app_ssh_host_key_fingerprint;
    }

    public void setApp_ssh_host_key_fingerprint(String app_ssh_host_key_fingerprint) {
        this.app_ssh_host_key_fingerprint = app_ssh_host_key_fingerprint;
    }

    public String getApp_ssh_oauth_client() {
        return app_ssh_oauth_client;
    }

    public void setApp_ssh_oauth_client(String app_ssh_oauth_client) {
        this.app_ssh_oauth_client = app_ssh_oauth_client;
    }

    public String getDoppler_logging_endpoint() {
        return doppler_logging_endpoint;
    }

    public void setDoppler_logging_endpoint(String doppler_logging_endpoint) {
        this.doppler_logging_endpoint = doppler_logging_endpoint;
    }

    public String getRouting_endpoint() {
        return routing_endpoint;
    }

    public void setRouting_endpoint(String routing_endpoint) {
        this.routing_endpoint = routing_endpoint;
    }

    @Override
    public String toString() {
        return "Info{" +
                "name='" + name + '\'' +
                ", build='" + build + '\'' +
                ", support='" + support + '\'' +
                ", version='" + version + '\'' +
                ", description='" + description + '\'' +
                ", authorization_endpoint='" + authorization_endpoint + '\'' +
                ", token_endpoint='" + token_endpoint + '\'' +
                ", min_cli_version='" + min_cli_version + '\'' +
                ", min_recommended_cli_version='" + min_recommended_cli_version + '\'' +
                ", api_version='" + api_version + '\'' +
                ", app_ssh_endpoint='" + app_ssh_endpoint + '\'' +
                ", app_ssh_host_key_fingerprint='" + app_ssh_host_key_fingerprint + '\'' +
                ", app_ssh_oauth_client='" + app_ssh_oauth_client + '\'' +
                ", doppler_logging_endpoint='" + doppler_logging_endpoint + '\'' +
                ", routing_endpoint='" + routing_endpoint + '\'' +
                '}';
    }
}
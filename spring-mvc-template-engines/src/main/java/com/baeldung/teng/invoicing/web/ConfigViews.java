package com.baeldung.teng.invoicing.web;

import org.springframework.context.annotation.Configuration;

/**
 * Extend the appropriate (view) configuration class to decide, at build-time, which actual view technology to use.
 *
 * @version 1.0
 */
@Configuration
public class ConfigViews extends ConfigJspViews {}

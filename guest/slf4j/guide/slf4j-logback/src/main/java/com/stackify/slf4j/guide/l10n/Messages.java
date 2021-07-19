package com.stackify.slf4j.guide.l10n;

import ch.qos.cal10n.BaseName;
import ch.qos.cal10n.Locale;
import ch.qos.cal10n.LocaleData;

@BaseName("messages")
@LocaleData({ @Locale("en_US"), @Locale("es_ES") })
public enum Messages {
    CLIENT_REQUEST, REQUEST_STARTED, REQUEST_FINISHED
}

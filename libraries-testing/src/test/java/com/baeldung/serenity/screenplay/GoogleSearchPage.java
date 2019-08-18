package com.baeldung.serenity.screenplay;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;

/**
 * @author baoqiang
 */
@DefaultUrl("https://www.google.com/ncr")
class GoogleSearchPage extends PageObject {

    static final Target SEARCH_RESULT_TITLES = Target.the("search results").locatedBy("._ksh");

    static final Target SEARCH_INPUT_BOX = Target.the("search input box").locatedBy("#lst-ib");

}

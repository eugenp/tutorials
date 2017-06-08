package com.baeldung.serenity.screenplay;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.annotations.DefaultUrl;

/**
 * @author baoqiang
 */
@DefaultUrl("https://www.google.com/ncr")
public class GoogleSearchPage extends PageObject {

    public static final Target SEARCH_RESULT_TITLES = Target
      .the("search results")
      .locatedBy("._ksh");

    public static final Target SEARCH_INPUT_BOX = Target
      .the("search input box")
      .locatedBy("#lst-ib");

}

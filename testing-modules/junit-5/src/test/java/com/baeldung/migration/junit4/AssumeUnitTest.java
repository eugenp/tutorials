/************************************************************************************
 * Copyright © BMC Software Inc.                                                    *
 *                                                                                  *
 *                                                                                  *
 *  `.-.`                                                                           *
 *  +oooo/:.                                                                        *
 *  `ooo-/+oo+:.`                                                                   *
 *  `ooo  `.:+ooo/-`           bmmm`                                                *
 *  `ooo.     `-/+oo+:.`       bmmm`                                                *
 *   -+oo/-`     `.:+oo+.      bmmm`                                                *
 *    `-/+oo+:.`  `-/ooo-      bmmm:+cccb/.   +mmm:mmmm+mm/mmmmm+-    `:+cccc+:`    *
 *       `.:+oo+/+oo+/-`       bmmmmbcbmmmb/  cmmmmbbmmmmmmbbmmmm/  :bmmbccbmmb-    *
 *         `-+oooooo/.`        bmmm:` `.cdmm: cmmm:``-mmmm-``-mmmb -mmdo`  .+oo/    *
 *     `.:+ooo/:.-/+oo+:`      bmmm`    .mmmo cmmm`   bmmm`   mmmb ommm.            *
 *   `:+oo+/-`     .:ooo:      bmmm`    /dmm+ cmmm`   bmmm`   mmmb /mmd/    ---.    *
 *   /oo/.`     .:/+oo/:`      bmmmb+//cmmmb` cmmm`   bmmm`   mmmb `cmmmo//ommm/    *
 *  `ooo    `-/+oo+/-`         cmmmcbmmmmc/`  cmmm`   cmmb`   bmmc  `:cbmmmmbc:     *
 *  `ooo .-/ooo+:.`            ```` `...``    ````    ````    ````     `....`       *
 *  `ooo+ooo/-`                                                                     *
 *  -+++:.`                                                                         *
 *                                                                                  *
 *                                                                                  *
 * This source code is property of BMC Software Inc.,                               *
 * covered by Copyright. All rights reserved.                                       *
 *                                                                                  *
 ************************************************************************************/
package com.baeldung.migration.junit4;


import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;

import org.junit.Test;

public class AssumeUnitTest {

    @Test
    void trueAssumption() {
        assumeTrue("5 is greater the 1", 5 > 1);
        assertEquals(5 + 2, 7);
    }

    @Test
    void falseAssumption() {
        assumeFalse("5 is less then 1", 5 < 1);
        assertEquals(5 + 2, 7);
    }
}

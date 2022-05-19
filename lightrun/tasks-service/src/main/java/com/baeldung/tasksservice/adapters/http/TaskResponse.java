/****************************************************************************************************************
 *
 *  Copyright (c) 2022 OCLC, Inc. All Rights Reserved.
 *
 *  OCLC proprietary information: the enclosed materials contain
 *  proprietary information of OCLC, Inc. and shall not be disclosed in whole or in
 *  any part to any third party or used by any person for any purpose, without written
 *  consent of OCLC, Inc.  Duplication of any portion of these materials shall include this notice.
 *
 ******************************************************************************************************************/

package com.baeldung.tasksservice.adapters.http;

import java.time.Instant;

public record TaskResponse(String id,
                           String title,
                           Instant created,
                           String createdBy,
                           String assignedTo,
                           String status) {
}

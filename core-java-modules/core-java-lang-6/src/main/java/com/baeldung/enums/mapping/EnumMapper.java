package com.baeldung.enums.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;

import com.baeldung.enums.mapping.order.CmsOrderStatus;
import com.baeldung.enums.mapping.order.OrderStatus;
import com.baeldung.enums.mapping.user.ExternalUserStatus;
import com.baeldung.enums.mapping.user.UserStatus;

@Mapper
public interface EnumMapper {

    CmsOrderStatus map(OrderStatus orderStatus);

    @ValueMapping(source = "PENDING", target = "INACTIVE")
    @ValueMapping(source = "BLOCKED", target = "INACTIVE")
    @ValueMapping(source = "INACTIVATED_BY_SYSTEM", target = "INACTIVE")
    @ValueMapping(source = "DELETED", target = "INACTIVE")
    ExternalUserStatus map(UserStatus userStatus);

    @ValueMapping(source = MappingConstants.ANY_REMAINING, target = "INACTIVE")
    ExternalUserStatus mapDefault(UserStatus userStatus);

}

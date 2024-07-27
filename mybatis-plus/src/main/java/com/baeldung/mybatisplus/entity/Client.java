package com.baeldung.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@TableName("client")
public class Client {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime creationDate;

    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime lastModifiedDate;

}
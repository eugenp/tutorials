package com.suri.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Member {
    private String Name;
    @JsonFormat(pattern = "dd-mm-yyyy")
    private Date dataOfBirth;

    @JsonFormat(pattern = "dd-mm-yyyy")
    private Date memberSince;

    private String address;

}

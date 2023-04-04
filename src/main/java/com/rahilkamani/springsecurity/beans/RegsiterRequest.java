package com.rahilkamani.springsecurity.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegsiterRequest {

    private String firstName;
    private String lastname;

    private String userName;
    private String password;

    private String userRole;
}

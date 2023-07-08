package com.uade.ad.controller.dto;

import com.uade.ad.model.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OAuthUserDto {
    private User user;
    private String jwt;
    private Boolean isNewUser;
}
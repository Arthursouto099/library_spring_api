package com.simplifica.library.config.authentication;

import lombok.Builder;

@Builder
public record JwtUserData(Long userId, String email) {}

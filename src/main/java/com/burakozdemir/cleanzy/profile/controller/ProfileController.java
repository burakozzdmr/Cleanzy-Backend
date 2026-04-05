package com.burakozdemir.cleanzy.profile.controller;

import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.profile.dto.ProfileDTO;
import org.springframework.http.ResponseEntity;

public interface ProfileController {
    ResponseEntity<ApiSuccessResponse<ProfileDTO>> getProfile(Long userId);
}

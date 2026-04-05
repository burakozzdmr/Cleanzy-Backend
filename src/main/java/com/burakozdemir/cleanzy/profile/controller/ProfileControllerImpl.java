package com.burakozdemir.cleanzy.profile.controller;

import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.profile.dto.ProfileDTO;
import com.burakozdemir.cleanzy.profile.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/v1/profile")
public class ProfileControllerImpl implements ProfileController {

    private final ProfileService profileService;

    ProfileControllerImpl(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/me/{userId}")
    @Override
    public ResponseEntity<ApiSuccessResponse<ProfileDTO>> getProfile(
            @PathVariable Long userId
    ) {
        return ResponseEntity
                .ok(profileService.fetchProfile(userId));
    }
}

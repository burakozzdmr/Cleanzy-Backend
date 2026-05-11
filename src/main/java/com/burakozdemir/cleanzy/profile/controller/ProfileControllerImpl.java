package com.burakozdemir.cleanzy.profile.controller;

import com.burakozdemir.cleanzy.auth.entity.User;
import com.burakozdemir.cleanzy.common.exception.BusinessException;
import com.burakozdemir.cleanzy.common.exception.ErrorType;
import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.profile.dto.ProfileDTO;
import com.burakozdemir.cleanzy.profile.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/api/v1/profile")
@RequiredArgsConstructor
@Tag(name = "Profile", description = "User profile endpoints")
@SecurityRequirement(name = "bearerAuth")
public class ProfileControllerImpl implements ProfileController {

    private final ProfileService profileService;

    @Override
    @GetMapping("/me/{userId}")
    @Operation(summary = "Get profile of the authenticated user")
    public ResponseEntity<ApiSuccessResponse<ProfileDTO>> getProfile(@PathVariable Long userId) {
        User authenticatedUser = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        if (!authenticatedUser.getId().equals(userId)) {
            throw new BusinessException(ErrorType.UNAUTHORIZED_ACCESS);
        }

        return ResponseEntity.ok(profileService.fetchProfile(userId));
    }
}

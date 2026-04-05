package com.burakozdemir.cleanzy.profile.service;

import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.profile.dto.ProfileDTO;

public interface ProfileService {
    ApiSuccessResponse<ProfileDTO> fetchProfile(Long userId);
}

package com.burakozdemir.cleanzy.cleaner.service;

import com.burakozdemir.cleanzy.cleaner.dto.CleanerResponseDTO;
import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;

import java.util.List;

public interface CleanerService {
    public ApiSuccessResponse<List<CleanerResponseDTO>> fetchCleanerList();
    public ApiSuccessResponse<CleanerResponseDTO> fetchCleanerDetails(Long cleanerID);
}

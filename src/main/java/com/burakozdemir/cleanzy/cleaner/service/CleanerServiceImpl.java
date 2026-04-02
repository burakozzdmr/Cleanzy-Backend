package com.burakozdemir.cleanzy.cleaner.service;

import com.burakozdemir.cleanzy.cleaner.dto.CleanerResponseDTO;
import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CleanerServiceImpl implements CleanerService {
    private final CleanerService cleanerService;

    CleanerServiceImpl(CleanerService cleanerService) {
        this.cleanerService = cleanerService;
    }

    @Override
    public ApiSuccessResponse<List<CleanerResponseDTO>> fetchCleanerList() {
        return null;
    }

    @Override
    public ApiSuccessResponse<CleanerResponseDTO> fetchCleanerDetails(Long cleanerID) {
        return null;
    }
}

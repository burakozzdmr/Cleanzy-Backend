package com.burakozdemir.cleanzy.cleaner.service;

import com.burakozdemir.cleanzy.cleaner.dto.CleanerResponseDTO;
import com.burakozdemir.cleanzy.cleaner.repository.CleanerRepository;
import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CleanerServiceImpl implements CleanerService {
    private final CleanerRepository cleanerRepository;

    CleanerServiceImpl(CleanerRepository cleanerRepository) {
        this.cleanerRepository = cleanerRepository;
    }

    @Override
    public ApiSuccessResponse<List<CleanerResponseDTO>> fetchCleanerList() {
        return null;
    }

    @Override
    public ApiSuccessResponse<CleanerResponseDTO> fetchCleanerDetailsByID(Long cleanerID) {
        return null;
    }
}

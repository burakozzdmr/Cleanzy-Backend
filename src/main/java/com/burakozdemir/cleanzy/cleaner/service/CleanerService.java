package com.burakozdemir.cleanzy.cleaner.service;

import com.burakozdemir.cleanzy.cleaner.dto.CleanerResponseDTO;
import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.common.util.ServiceType;

import java.util.List;

public interface CleanerService {
    ApiSuccessResponse<List<CleanerResponseDTO>> fetchCleanerList(ServiceType service);
    ApiSuccessResponse<CleanerResponseDTO> fetchCleanerDetailsByID(Long cleanerID);
}

package com.burakozdemir.cleanzy.cleaner.service;

import com.burakozdemir.cleanzy.auth.dto.UserSummaryDTO;
import com.burakozdemir.cleanzy.cleaner.dto.CleanerResponseDTO;
import com.burakozdemir.cleanzy.cleaner.entity.Cleaner;
import com.burakozdemir.cleanzy.cleaner.repository.CleanerRepository;
import com.burakozdemir.cleanzy.common.exception.BusinessException;
import com.burakozdemir.cleanzy.common.exception.ErrorType;
import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.common.util.ServiceType;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CleanerServiceImpl implements CleanerService {

    private final CleanerRepository cleanerRepository;

    CleanerServiceImpl(CleanerRepository cleanerRepository) {
        this.cleanerRepository = cleanerRepository;
    }

    @Override
    public ApiSuccessResponse<List<CleanerResponseDTO>> fetchCleanerList(ServiceType service) {
        List<Cleaner> cleaners = (service != null)
                ? cleanerRepository.findByServicesContaining(service)
                : cleanerRepository.findAll();

        List<CleanerResponseDTO> dtoList = cleaners.stream()
                .map(this::toCleanerResponseDTO)
                .toList();

        return ApiSuccessResponse.of(dtoList, dtoList.size());
    }

    @Override
    public ApiSuccessResponse<CleanerResponseDTO> fetchCleanerDetailsByID(Long cleanerID) {
        Cleaner dbCleaner = cleanerRepository.findById(cleanerID)
                .orElseThrow(() -> new BusinessException(ErrorType.CLEANER_NOT_FOUND));

        return ApiSuccessResponse.of(toCleanerResponseDTO(dbCleaner));
    }

    private CleanerResponseDTO toCleanerResponseDTO(Cleaner cleaner) {
        CleanerResponseDTO dto = new CleanerResponseDTO();
        BeanUtils.copyProperties(cleaner, dto);

        if (cleaner.getUser() != null) {
            UserSummaryDTO userSummary = new UserSummaryDTO();
            BeanUtils.copyProperties(cleaner.getUser(), userSummary);
            userSummary.setUserId(cleaner.getUser().getId());
            dto.setUser(userSummary);
        }

        return dto;
    }
}

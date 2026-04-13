package com.burakozdemir.cleanzy.favorite.service;

import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.favorite.dto.FavoriteRequestDTO;
import com.burakozdemir.cleanzy.favorite.dto.FavoriteResponseDTO;

import java.util.List;

public interface FavoriteService {
    ApiSuccessResponse<List<FavoriteResponseDTO>> fetchFavoritesByUserId(Long userId);
    ApiSuccessResponse<FavoriteResponseDTO> addFavorite(FavoriteRequestDTO request);
    ApiSuccessResponse<Boolean> removeFavorite(FavoriteRequestDTO request);
}

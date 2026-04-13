package com.burakozdemir.cleanzy.favorite.controller;

import com.burakozdemir.cleanzy.common.response.ApiSuccessResponse;
import com.burakozdemir.cleanzy.favorite.dto.FavoriteRequestDTO;
import com.burakozdemir.cleanzy.favorite.dto.FavoriteResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FavoriteController {
    ResponseEntity<ApiSuccessResponse<List<FavoriteResponseDTO>>> getFavoritesByUserId(Long userId);
    ResponseEntity<ApiSuccessResponse<FavoriteResponseDTO>> addFavorite(FavoriteRequestDTO request);
    ResponseEntity<ApiSuccessResponse<Boolean>> removeFavorite(FavoriteRequestDTO request);
}

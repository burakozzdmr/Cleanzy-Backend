package com.burakozdemir.cleanzy.favorite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteRequestDTO {
    private Long userId;
    private Long favoritedUserId;
}

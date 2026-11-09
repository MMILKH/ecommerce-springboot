package com.ecommerce.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarrelloDto {

    private Integer id;
    private String stato;
    private LocalDateTime dataCreazione;
    private List<RigaCarrelloDto> righe;

}

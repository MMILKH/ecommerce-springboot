package com.ecommerce.ecommerce.service;

import com.ecommerce.ecommerce.dto.OrdineDto;
import com.ecommerce.ecommerce.dto.RigaOrdineDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface OrdineService {
    OrdineDto createOrder(OrdineDto ordineDto,int userId);

}

package com.ecommerce.ecommerce.config;

import com.ecommerce.ecommerce.dto.ProdottoDto;
import com.ecommerce.ecommerce.entity.Prodotto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setMatchingStrategy(MatchingStrategies.STRICT);
//        modelMapper.addMappings(prodottoPropertyMap);
        return modelMapper;
    }

//    PropertyMap<Prodotto, ProdottoDto> prodottoPropertyMap = new PropertyMap<Prodotto, ProdottoDto>() {
//        @Override
//        protected void configure() {}
//    };
}

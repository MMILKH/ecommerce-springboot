package com.ecommerce.ecommerce.service.impl;

import com.ecommerce.ecommerce.api.model.CarrelloDto;
import com.ecommerce.ecommerce.api.model.RigaCarrelloDto;
import com.ecommerce.ecommerce.entity.Carrello;
import com.ecommerce.ecommerce.entity.Prodotto;
import com.ecommerce.ecommerce.entity.RigaCarrello;
import com.ecommerce.ecommerce.handler.GlobalExceptionHandler;
import com.ecommerce.ecommerce.repository.CarrelloRepository;
import com.ecommerce.ecommerce.repository.ProdottoRepository;
import com.ecommerce.ecommerce.repository.RigaCarrelloRepository;
import com.ecommerce.ecommerce.service.CarrelloService;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.List;

import java.util.stream.Collectors;

@Service
public class CarrelloServiceImpl implements CarrelloService {

    @Autowired
    private CarrelloRepository carrelloRepository;
    @Autowired
    private ProdottoRepository prodottoRepository;
    @Autowired
    private RigaCarrelloRepository rigaCarrelloRepository;

    @Autowired
    ModelMapper modelMapper;

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${core-user.base-url:http://localhost:8081}")
    private String coreUserBaseUrl;


    @Override
    public CarrelloDto getActiveCart(String utenteId) {
        Carrello carrello = carrelloRepository.findByUtenteAndStato(utenteId, "NEW");
        if (carrello == null) {
            throw new GlobalExceptionHandler.ResourceNotFoundException("Nessun carrello attivo trovato");
        }
        return modelMapper.map(carrello, CarrelloDto.class);
    }

    @Override
    @Transactional
    public RigaCarrelloDto addFromCatalog(RigaCarrelloDto dto, int cartId) {
        Carrello c =carrelloRepository.findByIdAndStato(cartId,"NEW");
        Prodotto p = prodottoRepository.findById(dto.getProdotto().getId()).orElse(null);
        if (c == null || p == null) {
            throw new GlobalExceptionHandler.ResourceNotFoundException("Carrello o prodotto non trovati");
        }
        RigaCarrello riga = rigaCarrelloRepository.findByCarrelloIdAndProdottoId(c.getId(), p.getId());
        if (riga != null) {
            riga.setQuantita(riga.getQuantita() + dto.getQuantita());
            riga.setDataUltimaModifica(LocalDateTime.now());
        }else{
            riga = new RigaCarrello();
            riga.setCarrello(c);
            riga.setProdotto(p);
            riga.setQuantita(dto.getQuantita());
            riga.setDataCreazione(LocalDateTime.now());
            riga.setDataUltimaModifica(LocalDateTime.now());
        }
        try{
            rigaCarrelloRepository.save(riga);
        } catch(Exception e){
            e.printStackTrace();
        }
        return  modelMapper.map(riga, RigaCarrelloDto.class);

    }



    @Override
    public List<RigaCarrelloDto> getRowByCartId(int carrelloId) {

        List<RigaCarrello> righe = rigaCarrelloRepository.findByCarrelloId(carrelloId);
        if (righe==null){
            throw new GlobalExceptionHandler.ResourceNotFoundException("Righe del carrello: "+carrelloId+" non trovate");
        }
        return righe.stream()
                .map(riga -> modelMapper.map(riga, RigaCarrelloDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RigaCarrelloDto getRowByCartIdAndProductsId(int cartId, int prodottoId) {
        RigaCarrello riga = rigaCarrelloRepository.findByCarrelloIdAndProdottoId(cartId, prodottoId);
        return  modelMapper.map(riga, RigaCarrelloDto.class);
    }


    @Override
    @Transactional
    public RigaCarrelloDto updateRowQuantity(int rigaId, int quantita) {
        RigaCarrello riga = rigaCarrelloRepository.findById(rigaId);
        if (riga == null) {
            throw new GlobalExceptionHandler.ResourceNotFoundException("Riga Carrello non trovata");
        }
        Carrello c =carrelloRepository.findByIdAndStato(riga.getCarrello().getId(),"NEW");
        if (c == null) {
            throw new GlobalExceptionHandler.ResourceNotFoundException("Riga Carrello appertenente a un carrello già completato");
        }
        if (quantita <1){
            quantita = 1;
        }

        riga.setQuantita(quantita);
        riga.setDataUltimaModifica(LocalDateTime.now());
        try {
            rigaCarrelloRepository.save(riga);
        }catch(Exception e){
            e.printStackTrace();
        }
        return  modelMapper.map(riga, RigaCarrelloDto.class);

    }

    @Override
    @Transactional
    public void deleteRow(int rigaId) {
        RigaCarrello riga = rigaCarrelloRepository.findById(rigaId);
        if (riga == null) {
            throw new GlobalExceptionHandler.ResourceNotFoundException("Riga Carrello non trovata");
        }
        Carrello c =carrelloRepository.findByIdAndStato(riga.getCarrello().getId(),"NEW");
        if (c == null) {
            throw new GlobalExceptionHandler.ResourceNotFoundException("Riga Carrello appartenente a un carrello già completato");
        }
        rigaCarrelloRepository.delete(riga);
    }

    @Override
    public CarrelloDto createCarrello(String userId) {

        String url = coreUserBaseUrl + "/api/user/" + userId;
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String authorizationHeader = (attrs != null) ? attrs.getRequest().getHeader("Authorization") : null;
        HttpHeaders headers = new HttpHeaders();

        if (authorizationHeader != null && !authorizationHeader.isBlank()) {
            headers.set("Authorization", authorizationHeader);
        }

        ResponseEntity<Boolean> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                Boolean.class
        );

        if (!Boolean.TRUE.equals(response.getBody())) {
            throw new GlobalExceptionHandler.ResourceNotFoundException("Utente non trovato con UUID: " + userId);
        }
        Carrello c = carrelloRepository.findByUtenteAndStato(userId,"NEW");
        if (c == null) {
            c = new Carrello();
            c.setUtente(userId);
            c.setStato("NEW");
            c.setDataCreazione(LocalDateTime.now());
            c.setUltimoAggiornamento(LocalDateTime.now());
            carrelloRepository.save(c);
        }

        return  modelMapper.map(c,CarrelloDto.class);
    }



    @Override
    public boolean existRiga(int rigaId) {
        return rigaCarrelloRepository.existsById(rigaId);
    }


}

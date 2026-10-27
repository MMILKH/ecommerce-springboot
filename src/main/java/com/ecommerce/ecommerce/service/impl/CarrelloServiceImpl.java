package com.ecommerce.ecommerce.service.impl;

import com.ecommerce.ecommerce.dto.CarrelloDto;
import com.ecommerce.ecommerce.dto.RigaCarrelloDto;
import com.ecommerce.ecommerce.entity.Carrello;
import com.ecommerce.ecommerce.entity.Prodotto;
import com.ecommerce.ecommerce.entity.RigaCarrello;
import com.ecommerce.ecommerce.repository.CarrelloRepository;
import com.ecommerce.ecommerce.repository.ProdottoRepository;
import com.ecommerce.ecommerce.repository.RigaCarrelloRepository;
import com.ecommerce.ecommerce.repository.UtenteRepository;
import com.ecommerce.ecommerce.service.CarrelloService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public CarrelloDto getActiveCart(int utenteId) {
        Carrello carrello = carrelloRepository.findByUtenteIdAndStato(utenteId, "NEW");
        return modelMapper.map(carrello, CarrelloDto.class);
    }

    @Override
    public String addFromCatalog(RigaCarrelloDto dto) {
        Carrello c =carrelloRepository.findById(dto.getCarrello().getId()).get();
        Prodotto p = prodottoRepository.findById(dto.getProdotto().getId()).get();
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
        rigaCarrelloRepository.save(riga);
        return "ok";
    }

    @Override
    public BigDecimal getTotal(int carrelloId) {
        List<RigaCarrello> righe ;
        righe = rigaCarrelloRepository.findByCarrelloId(carrelloId);
        BigDecimal totale = BigDecimal.ZERO;
        BigDecimal temp;
        for (RigaCarrello r : righe){
            temp= r.getProdotto().getPrezzo().multiply(new BigDecimal(r.getQuantita()));
            totale = totale.add(temp);
        }
        return totale;
    }

    @Override
    public List<RigaCarrelloDto> getRowByCartId(int carrelloId) {
        List<RigaCarrello> righe ;
        righe = rigaCarrelloRepository.findByCarrelloId(carrelloId);
        return righe.stream()
                .map(riga -> modelMapper.map(riga, RigaCarrelloDto.class))
                .collect(Collectors.toList());
    }


    @Override
    public RigaCarrelloDto updateRowQuantity(int rigaId, int quantita) {
        RigaCarrello riga = rigaCarrelloRepository.findById(rigaId);
        riga.setQuantita(quantita);
        riga.setDataUltimaModifica(LocalDateTime.now());
        rigaCarrelloRepository.save(riga);
        return  modelMapper.map(riga, RigaCarrelloDto.class);

    }

    @Override
    public void deleteRow(int rigaId) {
        RigaCarrello riga = rigaCarrelloRepository.findById(rigaId);
        rigaCarrelloRepository.delete(riga);
    }

    @Override
    public int countElements(int utenteId) {
        Carrello carrello = carrelloRepository.findByUtenteIdAndStato(utenteId, "NEW");
        List<RigaCarrello> righe ;
        righe = rigaCarrelloRepository.findByCarrelloId(carrello.getId());
        int totaleElementi= 0;
        for (RigaCarrello riga : righe) {
            totaleElementi+= riga.getQuantita();
        }
        return totaleElementi;
    }

    @Override
    public boolean existRiga(int rigaId) {
        return rigaCarrelloRepository.existsById(rigaId);
    }
}

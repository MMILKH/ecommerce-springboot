package com.ecommerce.ecommerce.service.impl;

import com.ecommerce.ecommerce.api.model.OrdineDto;
import com.ecommerce.ecommerce.api.model.RigaOrdineDto;
import com.ecommerce.ecommerce.entity.*;
import com.ecommerce.ecommerce.handler.GlobalExceptionHandler;
import com.ecommerce.ecommerce.repository.*;
import com.ecommerce.ecommerce.service.OrdineService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
public class OrdineServiceImpl implements OrdineService {


    @Autowired
    private CarrelloRepository carrelloRepository;
    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private RigaCarrelloRepository rigaCarrelloRepository;
    @Autowired
    private StatoRepository statoRepository;
    @Autowired
    private OrdineRepository ordineRepository;
    @Autowired
    private RigaOrdineRepository rigaOrdineRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public OrdineDto createOrder(OrdineDto dto,int userId) {

        Utente user = utenteRepository.findById(userId);
        if (dto.getIndirizzoSpedizione() == null || dto.getIndirizzoSpedizione().isBlank() || user == null){
            throw new IllegalArgumentException("Campo/i non valido/i");
        }
        Carrello carrello = carrelloRepository.findByUtenteIdAndStato(user.getId(),"NEW");

        if(carrello==null){
            throw new IllegalArgumentException("Nessun carrello trovato");
        }

        List<RigaCarrello> righeC = rigaCarrelloRepository.findByCarrelloId(carrello.getId());

        if(isEmpty(righeC)){
            throw new GlobalExceptionHandler.ResourceNotFoundException("Carrello vuoto");
        }

        Stato s = statoRepository.findByCodice("PEN");

        BigDecimal totale = BigDecimal.ZERO;
        for (RigaCarrello r : righeC) {
            BigDecimal prezzoProdotto = r.getProdotto().getPrezzo();
            BigDecimal subtotale = prezzoProdotto.multiply(BigDecimal.valueOf(r.getQuantita()));
            totale = totale.add(subtotale);
        }

        Ordine o = new Ordine();
        o.setUtente(carrello.getUtente());
        if (dto.getIndirizzoFatturazione() == null || dto.getIndirizzoFatturazione().trim().isEmpty()) {
            dto.setIndirizzoFatturazione(dto.getIndirizzoSpedizione());
        }
        o.setDataCreazione(LocalDateTime.now());
        o.setIndirizzoFatturazione(dto.getIndirizzoFatturazione());
        o.setIndirizzoSpedizione(dto.getIndirizzoSpedizione());
        o.setCodice(UUID.randomUUID().toString());
        o.setUtente(carrello.getUtente());
        o.setImporto(totale);
        o.setStato(s);
        try {
            ordineRepository.save(o);
        }catch (Exception e){
            e.printStackTrace();
        }

        for (RigaCarrello r : righeC) {
            RigaOrdine rO = new RigaOrdine();
            rO.setOrdine(o);
            BigDecimal prezzoRiga = r.getProdotto().getPrezzo().multiply(BigDecimal.valueOf(r.getQuantita()));
            rO.setPrezzoRiga(prezzoRiga);
            rO.setProdotto(r.getProdotto());
            rO.setDataCreazione(LocalDateTime.now());
            rO.setQuantita(r.getQuantita());
            rO.setRigaCarrelloId(r.getId());
            rO.setPrezzoUnitario(r.getProdotto().getPrezzo());
            try{
                rigaOrdineRepository.save(rO);
            }catch (Exception e){
                e.printStackTrace();
            }
            r.setDataUltimaModifica(LocalDateTime.now());
            try{
                rigaCarrelloRepository.save(r);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        carrello.setStato("COMPLETED");
        carrello.setUltimoAggiornamento(LocalDateTime.now());
        try {
            carrelloRepository.save(carrello);
        }catch (Exception e){
            e.printStackTrace();
        }
        Carrello cNew = new Carrello();
        cNew.setUtente(carrello.getUtente());
        cNew.setStato("NEW");
        cNew.setDataCreazione(LocalDateTime.now());
        cNew.setUltimoAggiornamento(LocalDateTime.now());
        try {
            carrelloRepository.save(cNew);
        }catch (Exception e){
            e.printStackTrace();
        }
        return modelMapper.map(o,OrdineDto.class);

    }

}

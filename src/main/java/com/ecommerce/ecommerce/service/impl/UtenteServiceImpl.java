package com.ecommerce.ecommerce.service.impl;


import com.ecommerce.ecommerce.dto.LoginRequest;
import com.ecommerce.ecommerce.dto.RegisterRequest;
import com.ecommerce.ecommerce.dto.UtenteDto;
import com.ecommerce.ecommerce.entity.Carrello;
import com.ecommerce.ecommerce.entity.Ruolo;
import com.ecommerce.ecommerce.entity.Stato;
import com.ecommerce.ecommerce.entity.Utente;
import com.ecommerce.ecommerce.repository.CarrelloRepository;
import com.ecommerce.ecommerce.repository.RuoloRepository;
import com.ecommerce.ecommerce.repository.UtenteRepository;
import com.ecommerce.ecommerce.service.UtenteService;
import com.ecommerce.ecommerce.util.Password;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UtenteServiceImpl implements UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private CarrelloRepository carrelloRepository;
    @Autowired
    private RuoloRepository ruoloRepository;
    @Autowired
    private ModelMapper modelMapper;



    @Override
    @Transactional(readOnly = true)
    public UtenteDto autentica(LoginRequest requestLogin) {

        if (requestLogin == null || requestLogin.getEmail() == null || requestLogin.getPassword() == null) {
            return null;
        }
        Utente utente = utenteRepository.findByEmail(requestLogin.getEmail());
        if (utente == null) {
            return null;
        }
        try {
            String hashPassword = Password.getHash(requestLogin.getPassword());
            if (hashPassword.equals(utente.getPasswordu())) {
                Carrello c = carrelloRepository.findByUtenteIdAndStato(utente.getId(), "NEW");
                if (c == null) {
                    c = new Carrello();
                    c.setUtente(utente);
                    c.setStato("NEW");
                    c.setUltimoAggiornamento(new java.util.Date());
                    c.setDataCreazione(new java.util.Date());
                    carrelloRepository.save(c);
                }
                return modelMapper.map(utente, UtenteDto.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional
    public String registra(RegisterRequest dto) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        if (!dto.getPassword().equals(dto.getPasswordConf())) {
            return "Le password non coincidono.";
        }

        if (utenteRepository.findByEmail(dto.getEmail()) != null) {
            return "Email già registrata nel sistema.";
        }

        Ruolo ruolo = ruoloRepository.findByNome("customer");
        if (ruolo == null) {
            return "Errore: Ruolo CUSTOMER non trovato nel DB.";
        }


        Utente utente = new Utente();
        utente.setRuolo(ruolo);
        utente.setNome(dto.getNome());
        utente.setCognome(dto.getCognome());
        utente.setEmail(dto.getEmail());
        utente.setNumero(dto.getNumero());
        utente.setIndirizzo(dto.getIndirizzo());
        utente.setDataCreazione(new java.util.Date());
        utente.setDataUltimaModifica(new java.util.Date());
        String passwordHash = Password.getHash(dto.getPassword());
         utente.setPasswordu(passwordHash);
        utente.setPasswordu(dto.getPassword());

        Utente utenteSalvato = utenteRepository.save(utente);

        Carrello c = new Carrello();
        c.setUtente(utenteSalvato);
        c.setStato("NEW");
        c.setDataCreazione(new java.util.Date());
        c.setUltimoAggiornamento(new java.util.Date());

        carrelloRepository.save(c);

        return "ok";
    }

    @Override
    public List<UtenteDto> showAll() {
        List<Utente> utenti = utenteRepository.findAll();
        return utenti.stream()
                .map(u -> modelMapper.map(u, UtenteDto.class))
                .collect(Collectors.toList());
    }
}
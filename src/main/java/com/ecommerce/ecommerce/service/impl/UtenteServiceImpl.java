package com.ecommerce.ecommerce.service.impl;


import com.ecommerce.ecommerce.dto.LoginRequest;
import com.ecommerce.ecommerce.dto.RegisterRequest;
import com.ecommerce.ecommerce.dto.UtenteDto;
import com.ecommerce.ecommerce.entity.*;
import com.ecommerce.ecommerce.repository.CarrelloRepository;
import com.ecommerce.ecommerce.repository.RuoloRepository;
import com.ecommerce.ecommerce.repository.UtenteRepository;
import com.ecommerce.ecommerce.service.UtenteService;
import com.ecommerce.ecommerce.specification.UtenteSpecifications;
import com.ecommerce.ecommerce.util.Password;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
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
    @PersistenceContext
    EntityManager em;



    @Override
    @Transactional(readOnly = true)
    public String login(LoginRequest requestLogin) {

        if (requestLogin == null || requestLogin.getEmail() == null || requestLogin.getPassword() == null) {
            return "campi non compilati correttamente";
        }
        Utente utente = utenteRepository.findByEmail(requestLogin.getEmail());
        if (utente == null) {
            return "email non esistente";
        }
        try {
            String hashPassword = Password.getHash(requestLogin.getPassword());
            if (hashPassword.equals(utente.getPasswordu())) {
                Carrello c = carrelloRepository.findByUtenteIdAndStato(utente.getId(), "NEW");
                if (c == null) {
                    c = new Carrello();
                    c.setUtente(utente);
                    c.setStato("NEW");
                    c.setUltimoAggiornamento(LocalDateTime.now());
                    c.setDataCreazione(LocalDateTime.now());
                    carrelloRepository.save(c);
                }
                return "ok";
            }else{
                return "Password errata";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
       return "Errore";
    }

    @Override
    @Transactional
    public String register(RegisterRequest dto) throws UnsupportedEncodingException, NoSuchAlgorithmException {

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
        utente.setDataCreazione(LocalDateTime.now());
        utente.setDataUltimaModifica(LocalDateTime.now());
        String passwordHash = Password.getHash(dto.getPassword());
         utente.setPasswordu(passwordHash);
        utente.setPasswordu(dto.getPassword());

        Utente utenteSalvato = utenteRepository.save(utente);

        Carrello c = new Carrello();
        c.setUtente(utenteSalvato);
        c.setStato("NEW");
        c.setDataCreazione(LocalDateTime.now());
        c.setUltimoAggiornamento(LocalDateTime.now());

        carrelloRepository.save(c);

        return "ok";
    }

    @Override
    public List<UtenteDto> getUsers(String nome, String cognome, String email, String numero, LocalDate inizio, LocalDate fine) {

        Specification<Utente> spec = Specification
                .where(UtenteSpecifications.containsName(nome))
                .and(UtenteSpecifications.containsSurname(cognome))
                .and(UtenteSpecifications.hasEmail(email))
                .and(UtenteSpecifications.hasNumber(numero))
                .and(UtenteSpecifications.isBetweenDates(inizio, fine));
        List<Utente> utenti = utenteRepository.findAll(spec);
        return utenti.stream()
                .map(u -> modelMapper.map(u, UtenteDto.class))
                .toList();
    }
}
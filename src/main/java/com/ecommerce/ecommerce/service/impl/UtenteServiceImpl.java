package com.ecommerce.ecommerce.service.impl;


import com.ecommerce.ecommerce.dto.LoginRequest;
import com.ecommerce.ecommerce.dto.RegisterRequest;
import com.ecommerce.ecommerce.dto.UtenteDto;
import com.ecommerce.ecommerce.entity.*;
import com.ecommerce.ecommerce.repository.CarrelloRepository;
import com.ecommerce.ecommerce.repository.RuoloRepository;
import com.ecommerce.ecommerce.repository.UtenteRepository;
import com.ecommerce.ecommerce.service.UtenteService;
import com.ecommerce.ecommerce.util.Password;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
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
    public String autentica(LoginRequest requestLogin) {

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
                return "ok";
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
    public List<UtenteDto> getUsers(String nome, String cognome, String email,String numero,Date inizio, Date fine) {
        List<Utente> utenti ;
        List<Predicate> predicates = new ArrayList<>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Utente> cq = cb.createQuery(Utente.class);
        Root<Utente> root = cq.from(Utente.class);
        if (nome != null && !nome.isBlank()) {
            Predicate predicateNome = cb.like(
                    cb.lower(root.get("nome")),
                    "%" + nome.toLowerCase() + "%");
            predicates.add(predicateNome);
        }
        if (cognome != null && !cognome.isBlank()) {
            Predicate predicateCognome = cb.like(
                    cb.lower(root.get("cognome")),
                    "%" + cognome.toLowerCase() + "%");
            predicates.add(predicateCognome);
        }

        if (email != null && !email.isBlank()) {
            Predicate predicateEmail = cb.equal(cb.lower(root.get("email")), email);
            predicates.add(predicateEmail);
        }

        if  (numero != null && !numero.isBlank()) {
            Predicate predicateNumero = cb.equal(root.get("numero"), numero);
            predicates.add(predicateNumero);
        }

        if (inizio != null && fine != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(fine);
                cal.set(Calendar.HOUR_OF_DAY, 23);
                cal.set(Calendar.MINUTE, 59);
                cal.set(Calendar.SECOND, 59);
                cal.set(Calendar.MILLISECOND, 999);
                fine = cal.getTime();
            Predicate predicateDataCreazione = cb.between(root.get("dataCreazione"), inizio, fine);
            predicates.add(predicateDataCreazione);

        }

        cq.select(root).where(predicates.toArray(new Predicate[0]));
        utenti = em.createQuery(cq).getResultList();
        return utenti.stream()
                .map(u -> modelMapper.map(u, UtenteDto.class))
                .collect(Collectors.toList());
    }
}
package com.ecommerce.ecommerce.service.impl;


import com.ecommerce.ecommerce.dto.JwtResponse;
import com.ecommerce.ecommerce.dto.LoginRequest;
import com.ecommerce.ecommerce.dto.RegisterRequest;
import com.ecommerce.ecommerce.dto.UtenteDto;
import com.ecommerce.ecommerce.entity.*;
import com.ecommerce.ecommerce.repository.CarrelloRepository;
import com.ecommerce.ecommerce.repository.RuoloRepository;
import com.ecommerce.ecommerce.repository.UtenteRepository;
import com.ecommerce.ecommerce.security.JwtService;
import com.ecommerce.ecommerce.service.UtenteService;
import com.ecommerce.ecommerce.specification.UtenteSpecifications;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;



    @Override
    public JwtResponse login(LoginRequest requestLogin) {

        if (requestLogin == null || requestLogin.getEmail() == null || requestLogin.getPassword() == null) {
            throw new IllegalArgumentException("Campo/i non valido/i");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        requestLogin.getEmail(),
                        requestLogin.getPassword()
                )
        );
        Utente utente = utenteRepository.findByEmail(requestLogin.getEmail());
        if (utente == null) {
            throw new IllegalArgumentException("Email non esistente");
        }
        Carrello c = carrelloRepository.findByUtenteIdAndStato(utente.getId(), "NEW");
        if (c == null) {
            c = new Carrello();
            c.setUtente(utente);
            c.setStato("NEW");
            c.setUltimoAggiornamento(LocalDateTime.now());
            c.setDataCreazione(LocalDateTime.now());
            carrelloRepository.save(c);
        }
        String tokenJwt = jwtService.generateToken(utente);
        UtenteDto utenteDto = modelMapper.map(utente, UtenteDto.class);
        return new JwtResponse(tokenJwt, utenteDto);
    }

    @Override
    @Transactional
    public void register(RegisterRequest dto) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        if (dto.getPassword() == null || dto.getPassword().isEmpty() ||
                dto.getEmail() == null || dto.getEmail().isEmpty()
                || dto.getNome() == null || dto.getNome().isEmpty()
                || dto.getCognome() == null || dto.getCognome().isEmpty()
                || dto.getNumero() == null || dto.getNumero().isEmpty()
                || dto.getPasswordConf() == null || dto.getPasswordConf().isEmpty())
        {
            throw new IllegalArgumentException("Campo/i non valido/i");

        }

        if (!dto.getPassword().equals(dto.getPasswordConf())) {
            throw new IllegalArgumentException("Le password non coincidono");
        }

        if (utenteRepository.findByEmail(dto.getEmail()) != null) {
            throw new IllegalArgumentException("Email già registrata nel sistema");
        }

        Ruolo ruolo = ruoloRepository.findByNome("customer");


        Utente utente = new Utente();
        utente.setRuolo(ruolo);
        utente.setNome(dto.getNome());
        utente.setCognome(dto.getCognome());
        utente.setEmail(dto.getEmail());
        utente.setNumero(dto.getNumero());
        utente.setIndirizzo(dto.getIndirizzo());
        utente.setDataCreazione(LocalDateTime.now());
        utente.setDataUltimaModifica(LocalDateTime.now());
        String passwordHash = passwordEncoder.encode(dto.getPassword());
        utente.setPasswordu(passwordHash);
        Utente utenteSalvato=new Utente();
        try {
            utenteSalvato = utenteRepository.save(utente);
        }catch (Exception e){
            e.printStackTrace();
        }
        Carrello c = new Carrello();
        c.setUtente(utenteSalvato);
        c.setStato("NEW");
        c.setDataCreazione(LocalDateTime.now());
        c.setUltimoAggiornamento(LocalDateTime.now());
        try {
            carrelloRepository.save(c);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<UtenteDto> getUsers(String nome, String cognome, String email, String numero, LocalDate inizio, LocalDate fine,String ruolo) {

        Specification<Utente> spec = Specification
                .where(UtenteSpecifications.containsName(nome))
                .and(UtenteSpecifications.containsSurname(cognome))
                .and(UtenteSpecifications.hasEmail(email))
                .and(UtenteSpecifications.hasRole(ruolo))
                .and(UtenteSpecifications.hasNumber(numero))
                .and(UtenteSpecifications.isBetweenDates(inizio, fine));
        List<Utente> utenti = utenteRepository.findAll(spec);
        return utenti.stream()
                .map(u -> modelMapper.map(u, UtenteDto.class))
                .toList();
    }
}
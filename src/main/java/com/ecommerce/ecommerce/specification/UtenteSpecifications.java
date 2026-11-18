package com.ecommerce.ecommerce.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class UtenteSpecifications {

    public static Specification<Utente> containsName(String nome) {
        return (root, query, cb) -> {
            if (nome == null || nome.isBlank()) return null;
            return cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%");
        };
    }

    public static Specification<Utente> containsSurname(String cognome) {
        return (root, query, cb) -> {
            if (cognome == null || cognome.isBlank()) return null;
            return cb.like(cb.lower(root.get("cognome")), "%" + cognome.toLowerCase() + "%");
        };
    }

    public static Specification<Utente> hasEmail(String email) {
        return (root, query, cb) -> {
            if (email == null || email.isBlank()) return null;
            return cb.equal(cb.lower(root.get("email")), email.toLowerCase());
        };
    }


    public static Specification<Utente> hasRole(String ruoloNome) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(ruoloNome)) {
                return criteriaBuilder.conjunction(); 
            }
            return criteriaBuilder.equal(
                    criteriaBuilder.lower(root.get("ruolo").get("nome")),
                    ruoloNome.toLowerCase()
            );
        };
    }

    public static Specification<Utente> hasNumber(String numero) {
        return (root, query, cb) -> {
            if (numero == null || numero.isBlank()) return null;
            return cb.equal(root.get("numero"), numero);
        };
    }



    public static Specification<Utente> isBetweenDates(LocalDate DataInizio, LocalDate DataFine) {
        return (root, query, cb) -> {
            if (DataInizio == null || DataFine == null) return null;

            LocalDateTime inizio = DataInizio.atStartOfDay();
            LocalDateTime fine= DataFine.atTime(LocalTime.MAX);

            return cb.between(root.get("dataCreazione"), inizio, fine);
        };
    }
}
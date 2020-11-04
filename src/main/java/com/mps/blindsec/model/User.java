package com.mps.blindsec.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "blindsec_user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private ZonedDateTime created;

    private String name;

    private String email;

    private String publicKeyPath;

    private String passwordHash;
}
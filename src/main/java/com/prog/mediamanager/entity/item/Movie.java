package com.prog.mediamanager.entity.item;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Movie extends Item {
    private String director;
    private String actor;
}

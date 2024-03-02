package com.prog.mediamanager.entity.item;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Book extends Item {
    private String author;
    private String isbn;
}

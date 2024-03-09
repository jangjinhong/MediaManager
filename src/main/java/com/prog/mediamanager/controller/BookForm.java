package com.prog.mediamanager.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookForm {
    private Long id;

    @NotEmpty(message = "상품명 입력은 필수입니다")
    private String name;

    @NotNull(message = "상품 가격 입력은 필수입니다")
    private int price;

    @NotNull(message = "재고 수량 입력은 필수입니다")
    private int stockQuantity;

    private String author;

    private String isbn;
}

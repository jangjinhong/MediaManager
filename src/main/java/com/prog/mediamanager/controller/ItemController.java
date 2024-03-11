package com.prog.mediamanager.controller;

import com.prog.mediamanager.entity.item.Book;
import com.prog.mediamanager.entity.item.Item;
import com.prog.mediamanager.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("bookForm", new BookForm());
        return "/items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(@Valid BookForm form, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "/items/createItemForm";

        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/items";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "/items/itemList";
    }

    @GetMapping("/items/{id}/edit")
    public String updateItemForm(@PathVariable Long id, Model model) {
        Book item = (Book) itemService.findOne(id); // 수정 대상
        BookForm form = new BookForm(); // 수정 내용

        form.setId(item.getId());
        form.setName(item.getName());
        form.setStockQuantity(item.getStockQuantity());
        form.setPrice(item.getPrice());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());

        // 수정 대상 view에 넘기기
        model.addAttribute("form", form);

        return "/items/updateItemForm";
    }

    @PostMapping("/items/{id}/edit")
    public String updateItem(@PathVariable("id") Long itemId, @ModelAttribute("form") BookForm form) {      // view 단에서 수정한 내용이 담긴 객체 form
        Book book = new Book(); 

//        book.setId(form.getId()); // 객체는 새로운 객체지만, id가 세팅되어있음 -> JPA에게 들어갔다 나온 객체!
//        book.setName(form.getName());
//        book.setStockQuantity(form.getStockQuantity());
//        book.setPrice(form.getPrice());
//        book.setAuthor(form.getAuthor());
//        book.setIsbn(form.getIsbn());
//        //book은 준영속 엔티티
//        itemService.saveItem(book);

        itemService.udpateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());

        return "redirect:/items";
    }
}

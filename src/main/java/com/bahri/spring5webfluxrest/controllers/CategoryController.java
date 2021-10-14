package com.bahri.spring5webfluxrest.controllers;

import com.bahri.spring5webfluxrest.domain.Category;
import com.bahri.spring5webfluxrest.repositories.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@RestController
public class CategoryController {

    private final CategoryRepository categoryRepository;


    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/api/v1/categories")
    Flux<Category> list() {
        return categoryRepository.findAll();
    }

    @GetMapping("/api/v1/categories/{id}")
    Mono<Category> getById(@PathVariable String id) {
        return categoryRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/categories")
    Flux<Category> create(@RequestBody Publisher<Category> categoryPublisher) {
        return categoryRepository.saveAll(categoryPublisher);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/api/v1/categories/{id}")
    Mono<Category> update(@RequestBody Category category,@PathVariable String id) {
       category.setId(id);
        return categoryRepository.save(category);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/api/v1/categories/{id}")
    Mono<Category> patch(@RequestBody Category category,@PathVariable String id) {
        return categoryRepository.findById(id).map(category2 -> {
            if(!Objects.equals(category2.getName(), category.getName())) {
                System.out.println(category2.getId());
                category2.setName(category.getName());
            }
            return category2;
        }).flatMap(categoryRepository::save);
    }


}

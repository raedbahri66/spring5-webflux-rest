package com.bahri.spring5webfluxrest.bootstrap;

import com.bahri.spring5webfluxrest.domain.Category;
import com.bahri.spring5webfluxrest.domain.Vendor;
import com.bahri.spring5webfluxrest.repositories.CategoryRepository;
import com.bahri.spring5webfluxrest.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.count().block() == 0 ){
            System.out.println("Load Data");
            categoryRepository.save(Category.builder().name("Fruits").build()).block();
            categoryRepository.save(Category.builder().name("Eggs").build()).block();
            categoryRepository.save(Category.builder().name("Meats").build()).block();

            vendorRepository.save(Vendor.builder().firstName("Raed").lastName("Bahri").build()).block();
            vendorRepository.save(Vendor.builder().firstName("Sami").lastName("Dridi").build()).block();
            vendorRepository.save(Vendor.builder().firstName("Ahmed").lastName("Rami").build()).block();

            System.out.println(vendorRepository.count().block());

        }
    }
}

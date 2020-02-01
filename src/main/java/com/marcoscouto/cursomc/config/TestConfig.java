package com.marcoscouto.cursomc.config;

import com.marcoscouto.cursomc.domain.*;
import com.marcoscouto.cursomc.domain.enums.TypeClient;
import com.marcoscouto.cursomc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public void run(String... args) throws Exception {

        Category cat1 = new Category(null, "Computing");
        Category cat2 = new Category(null, "Office");

        Product p1 = new Product(null, "Computer", 2000.0);
        Product p2 = new Product(null, "Printer", 800.0);
        Product p3 = new Product(null, "Mouse", 80.0);

        cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
        cat1.getProducts().addAll(Arrays.asList(p2));

        p1.getCategories().addAll(Arrays.asList(cat1));
        p2.getCategories().addAll(Arrays.asList(cat1, cat2));
        p3.getCategories().addAll(Arrays.asList(cat1));

        categoryRepository.saveAll(Arrays.asList(cat1, cat2));
        productRepository.saveAll(Arrays.asList(p1, p2, p3));

        State state1 = new State(null, "São Paulo");
        State state2 = new State(null, "Minas Gerais");

        City city1 = new City(null, "Uberlândia", state2);
        City city2 = new City(null, "São Paulo", state1);
        City city3 = new City(null, "Campinas", state1);

        stateRepository.saveAll(Arrays.asList(state1, state2));
        cityRepository.saveAll(Arrays.asList(city1, city2, city3));

        Client cli1 = new Client(
                null,
                "Maria Silva",
                "maria@gmail.com",
                "34567876523",
                TypeClient.PHYSICAL_PERSON);

        cli1.getPhones().addAll(
                Arrays.asList("11 98982733", "11 97267931"));


        Address ad1 = new Address(
                null,
                "Rua Flores",
                "300",
                "Apto 303",
                "Jardim",
                "12333421",
                cli1,
                city1);

        Address ad2 = new Address(
                null,
                "Avenida Matos",
                "105",
                "Sala 800",
                "Centro",
                "45693214",
                cli1,
                city2);

        cli1.getAddresses().addAll(Arrays.asList(ad1, ad2));

        clientRepository.save(cli1);
        addressRepository.saveAll(Arrays.asList(ad1, ad2));



    }
}

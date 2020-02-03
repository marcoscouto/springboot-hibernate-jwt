package com.marcoscouto.cursomc.config;

import com.marcoscouto.cursomc.domain.*;
import com.marcoscouto.cursomc.domain.enums.StatePayment;
import com.marcoscouto.cursomc.domain.enums.TypeClient;
import com.marcoscouto.cursomc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.SimpleDateFormat;
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

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

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

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Order or1 = new Order(null, sdf.parse("30/09/2017 10:32"), cli1, ad1);
        Order or2 = new Order(null, sdf.parse("10/10/2017 19:35"), cli1, ad2);

        Payment pay1 = new PaymentCreditCard(null, StatePayment.PAID.getCode(), or1, 6);
        or1.setPayment(pay1);

        Payment pay2 = new PaymentSlip(null, StatePayment.PENDING.getCode(), or2, sdf.parse("20/10/2017 00:00"), null);
        or2.setPayment(pay2);

        cli1.getOrders().addAll(Arrays.asList(or1, or2));

        orderRepository.saveAll(Arrays.asList(or1, or2));
        paymentRepository.saveAll(Arrays.asList(pay1, pay2));

        OrderItem oi1 = new OrderItem(or1, p1, 0.00, 1, 2000.00);
        OrderItem oi2 = new OrderItem(or1, p3, 0.00, 2, 80.00);
        OrderItem oi3 = new OrderItem(or2, p2, 100.00, 1, 800.00);

        or1.getItens().addAll(Arrays.asList(oi1, oi2));
        or2.getItens().addAll(Arrays.asList(oi3));

        p1.getItens().add(oi1);
        p2.getItens().add(oi3);
        p3.getItens().add(oi2);

        orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3));



    }
}

package com.marcoscouto.cursomc.services;

import com.marcoscouto.cursomc.domain.*;
import com.marcoscouto.cursomc.domain.enums.StatePayment;
import com.marcoscouto.cursomc.domain.enums.TypeClient;
import com.marcoscouto.cursomc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

@Component
public class DBService {

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

    public void populateDatabase() throws ParseException {
        Category cat1 = new Category(null, "Computing");
        Category cat2 = new Category(null, "Office");
        Category cat3 = new Category(null, "Home");
        Category cat4 = new Category(null, "Electronics");
        Category cat5 = new Category(null, "Garden");
        Category cat6 = new Category(null, "Decoration");
        Category cat7 = new Category(null, "Perfume");


        Product p1 = new Product(null, "Computer", 2000.0);
        Product p2 = new Product(null, "Printer", 800.0);
        Product p3 = new Product(null, "Mouse", 80.0);
        Product p4 = new Product(null, "Office Desk", 300.0);
        Product p5 = new Product(null, "Towel", 50.0);
        Product p6 = new Product(null, "Sheet", 200.0);
        Product p7 = new Product(null, "Smart TV", 1200.0);
        Product p8 = new Product(null, "Cutter", 1200.0);
        Product p9 = new Product(null, "Lamp", 100.0);
        Product p10 = new Product(null, "Chandelier", 180.0);
        Product p11 = new Product(null, "Shampoo", 90.0);

        cat1.getProducts().addAll(Arrays.asList(p1, p2, p3));
        cat2.getProducts().addAll(Arrays.asList(p2, p4));
        cat3.getProducts().addAll(Arrays.asList(p5, p6));
        cat4.getProducts().addAll(Arrays.asList(p1, p2, p3, p7));
        cat5.getProducts().addAll(Arrays.asList(p8));
        cat6.getProducts().addAll(Arrays.asList(p9, p10));
        cat7.getProducts().addAll(Arrays.asList(p11));

        p1.getCategories().addAll(Arrays.asList(cat1, cat4));
        p2.getCategories().addAll(Arrays.asList(cat1, cat2, cat4));
        p3.getCategories().addAll(Arrays.asList(cat1, cat4));
        p4.getCategories().addAll(Arrays.asList(cat2));
        p5.getCategories().addAll(Arrays.asList(cat3));
        p6.getCategories().addAll(Arrays.asList(cat3));
        p7.getCategories().addAll(Arrays.asList(cat4));
        p8.getCategories().addAll(Arrays.asList(cat5));
        p9.getCategories().addAll(Arrays.asList(cat6));
        p10.getCategories().addAll(Arrays.asList(cat6));
        p11.getCategories().addAll(Arrays.asList(cat7));

        categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
        productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

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

        or1.getItems().addAll(Arrays.asList(oi1, oi2));
        or2.getItems().addAll(Arrays.asList(oi3));

        p1.getItens().add(oi1);
        p2.getItens().add(oi3);
        p3.getItens().add(oi2);

        orderItemRepository.saveAll(Arrays.asList(oi1, oi2, oi3));
    }
}

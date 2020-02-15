package com.marcoscouto.cursomc.services;

import com.marcoscouto.cursomc.domain.*;
import com.marcoscouto.cursomc.domain.enums.StatePayment;
import com.marcoscouto.cursomc.repositories.*;
import com.marcoscouto.cursomc.security.UserSS;
import com.marcoscouto.cursomc.services.exceptions.AuthorizationException;
import com.marcoscouto.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SlipPaymentService slipPaymentService;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private EmailService emailService;

    public Order findById(Integer id){
        Optional<Order> order = orderRepository.findById(id);
        return order.orElseThrow(() -> new ObjectNotFoundException("Object not found! Id: " + id +
                                                ", Type: " + Order.class.getName()));
    }

    @Transactional
    public Order save(Order order){
        order.setId(null);
        order.setInstant(new Date());
        order.setClient(clientService.findById(order.getClient().getId()));
        order.getPayment().setStatePayment(StatePayment.PENDING);
        order.getPayment().setOrder(order);
        if(order.getPayment() instanceof PaymentSlip) {
            PaymentSlip pay = (PaymentSlip) order.getPayment();
            slipPaymentService.calculateDateforSlip(pay, order.getInstant());
        }
        order = orderRepository.save(order);
        paymentRepository.save(order.getPayment());
        for (OrderItem item : order.getItems()){
            item.setDiscount(0.0);
            item.setProduct(productService.findById(item.getProduct().getId()));
            item.setPrice(item.getProduct().getPrice());
            item.setOrder(order);
        }
        orderItemRepository.saveAll(order.getItems());
        emailService.sendOrderConfirmationEmail(order);
        return order;
    }

    public Page<Order> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        UserSS user = UserService.authenticated();
        if(user == null) throw new AuthorizationException("Access denied");
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Client cli = clientService.findById(user.getId());
        return orderRepository.findByClient(cli, pageRequest);
    }

}

package com.marcoscouto.cursomc.services;

import com.marcoscouto.cursomc.domain.PaymentSlip;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class SlipPaymentService {

    public void calculateDateforSlip(PaymentSlip paymentSlip, Date instant){
        Calendar cal = Calendar.getInstance();
        cal.setTime(instant);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        paymentSlip.setPaymentDate(cal.getTime());
    }

}

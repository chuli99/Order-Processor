package ar.progamacion.julian.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GetOrderService {

    private final Logger log = LoggerFactory.getLogger(GetOrderService.class);
}

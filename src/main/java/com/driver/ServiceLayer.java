package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ServiceLayer {
    @Autowired
    RepositoryLayer repositoryLayer;

    public void add_order(Order order) {
        repositoryLayer.add_order(order);
    }

    public void add_partner(String partnerId) {
        repositoryLayer.add_partner(partnerId);
    }

    public void order_partnerPair(String orderId, String partnerId) {
        repositoryLayer.order_partnerPair(orderId,partnerId);
    }

    public Order get_order_by_Id(String orderId) {
        return repositoryLayer.get_order_by_id(orderId);
    }

    public DeliveryPartner get_partner_by_id(String partnerId) {
        return repositoryLayer.get_partner_by_id(partnerId);
    }

    public Integer get_order_count_byPartnerId(String partnerId) {
        return repositoryLayer. get_order_count_byPartnerId(partnerId);
    }

    public List<String> get_orders_by_partnerId(String partnerId) {
        return repositoryLayer.get_orders_by_partnerId(partnerId);
    }

    public List<String> get_all_orders() {
        return repositoryLayer.get_all_orders();
    }

    public Integer count_unassined_orders() {
        return repositoryLayer.count_unassined_orders();
    }

    public Integer get_orders_left_after_giventime_byPartnerId(String time, String partnerId) {
        return repositoryLayer.get_orders_left_after_giventime_byPartnerId(time,partnerId);
    }

    public String get_last_delivary_time_bypartnerId(String partnerId) {
        return repositoryLayer.get_last_delivary_time_bypartnerId(partnerId);
    }

    public void delete_partner_by_id(String partnerId) {
        repositoryLayer.delete_partner_by_id(partnerId);
    }

    public void delete_order_byId(String orderId) {
        repositoryLayer.delete_order_byId(orderId);
    }
}

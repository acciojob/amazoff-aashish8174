package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ServiceLayer {


    //    @Autowired
    RepositoryLayer orderRepository=new RepositoryLayer();


    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }


    public void addPartner(String partnerId) {
        orderRepository.addPartner(partnerId);
    }


    public void addOrderPartnerPair(String orderId, String partnerId) {
        orderRepository.addOrderPartnerPair(orderId,partnerId);
    }

    public Order getOrderById(String orderId) {
        return orderRepository.getOrderById(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return orderRepository.getPartnerById(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return orderRepository.getOrderCountByPartnerId(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        return orderRepository.getOrdersByPartnerId(partnerId);
    }

    public List<String> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public Integer getCountOfUnassignedOrders() {
        return orderRepository.getCountOfUnassignedOrders();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        return orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(time,partnerId);
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        return orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
    }

    public void deletePartnerById(String partnerId) {
        orderRepository.deletePartnerById(partnerId);
    }

    public void deleteOrderById(String orderId) {
        orderRepository.deleteOrderById(orderId);
    }
//    @Autowired
//    RepositoryLayer repositoryLayer;
//
//    public void add_order(Order order) {
//        repositoryLayer.add_order(order);
//    }
//
//    public void add_partner(String partnerId) {
//        repositoryLayer.add_partner(partnerId);
//    }
//
//    public void order_partnerPair(String orderId, String partnerId) {
//        repositoryLayer.order_partnerPair(orderId,partnerId);
//    }
//
//    public Order get_order_by_Id(String orderId) {
//        return repositoryLayer.get_order_by_id(orderId);
//    }
//
//    public DeliveryPartner get_partner_by_id(String partnerId) {
//        return repositoryLayer.get_partner_by_id(partnerId);
//    }
//
//    public int get_order_count_byPartnerId(String partnerId) {
//        return repositoryLayer. get_order_count_byPartnerId(partnerId);
//    }
//
//    public List<String> get_orders_by_partnerId(String partnerId) {
//        return repositoryLayer.get_orders_by_partnerId(partnerId);
//    }
//
//    public List<String> get_all_orders() {
//        return repositoryLayer.get_all_orders();
//    }
//
//    public int count_unassined_orders() {
//        return repositoryLayer.count_unassined_orders();
//    }
//
//    public int get_orders_left_after_giventime_byPartnerId(String time, String partnerId) {
//        return repositoryLayer.get_orders_left_after_giventime_byPartnerId(time,partnerId);
//    }
//
//    public String get_last_delivary_time_bypartnerId(String partnerId) {
//        return repositoryLayer.get_last_delivary_time_bypartnerId(partnerId);
//    }
//
//    public void delete_partner_by_id(String partnerId) {
//        repositoryLayer.delete_partner_by_id(partnerId);
//    }
//
//    public void delete_order_byId(String orderId) {
//        repositoryLayer.delete_order_byId(orderId);
//    }
}

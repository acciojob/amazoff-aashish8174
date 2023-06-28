package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RepositoryLayer {
    private HashMap<String,Order> orderDB = new HashMap<>();
    private HashMap<String,DeliveryPartner> partnerDB = new HashMap<>();
    private HashMap<String,DeliveryPartner> order_partnerPairDB = new HashMap<>(); // id->orderId
    private HashMap<String, List<String>> partner_orderDB = new HashMap<>();// id-> partnerId

    public void add_order(Order order) {
        orderDB.put(order.getId(),order);
    }

    public void add_partner(String partnerId) {
        if(partnerDB.containsKey(partnerId)){
           partnerDB.put(partnerId, partnerDB.get(partnerId));
        }
        else {
            partnerDB.put(partnerId,new DeliveryPartner(partnerId));
        }
    }

    public void order_partnerPair(String orderId, String partnerId) {
        order_partnerPairDB.put(orderId,new DeliveryPartner(partnerId));//
        if(partner_orderDB.containsKey(partnerId)){   // assign order to partnerId
            List<String> a = partner_orderDB.get(partnerId);
            a.add(orderId);
            partner_orderDB.put(partnerId,a);
        }
        else {
           List<String> firstOrder =new ArrayList<>();
                   firstOrder.add(orderId);
            partner_orderDB.put(partnerId,firstOrder);
        }
    }

    public Order get_order_by_id(String orderId) {
        if(orderDB.containsKey(orderId)){
            return orderDB.get(orderId);
        }
        return null;
    }

    public DeliveryPartner get_partner_by_id(String partnerId) {
        if(partnerDB.containsKey(partnerId)){
            return partnerDB.get(partnerId);
        }
        return null;
    }

    public Integer get_order_count_byPartnerId(String partnerId) {
        if (partner_orderDB.containsKey(partnerId)){
            return partner_orderDB.get(partnerId).size();
        }
        return 0;
    }

    public List<String> get_orders_by_partnerId(String partnerId) {
        if(partner_orderDB.containsKey(partnerId)){
            return partner_orderDB.get(partnerId);
        }
        return null;
    }

    public List<String> get_all_orders() {
        List<String> allOrders = new ArrayList<>();
        // traversing on all key and adding it to list
       for(Map.Entry<String,Order> entry : orderDB.entrySet()){
           allOrders.add(entry.getKey());
       }
       return allOrders;
    }

    public Integer count_unassined_orders() {
        return orderDB.size()-order_partnerPairDB.size();
    }

    public Integer get_orders_left_after_giventime_byPartnerId(String time, String partnerId) {
        int Time = Integer.parseInt(time);
        List<String> listOfOrders = partner_orderDB.get(partnerId);
        int totalLeft = 0;
        for (String orderId :listOfOrders){
            int t = orderDB.get(orderId).getDeliveryTime();
            if(t>Time) totalLeft++;
        }
        return totalLeft;
    }


    public String get_last_delivary_time_bypartnerId(String partnerId) {
        List<String> listOfOrders = partner_orderDB.get(partnerId);
        int Time =0;
        for (String orderId :listOfOrders){
            int t = orderDB.get(orderId).getDeliveryTime();
            if(t>Time) Time=t;
        }
        return Time+"";//has to be updated
    }

    public void delete_partner_by_id(String partnerId) {
        partnerDB.remove(partnerId);
        for(Map.Entry<String,DeliveryPartner> entry:order_partnerPairDB.entrySet()){
           DeliveryPartner d = entry.getValue();

           if(d.getId().equals(partnerId)){
               String id = entry.getKey();
               order_partnerPairDB.remove(id);
           }
        }
        partner_orderDB.remove(partnerId);
    }

    public void delete_order_byId(String orderId) {
        orderDB.remove(orderId);
        order_partnerPairDB.remove(orderId);
        for(Map.Entry<String ,List<String>> entry:partner_orderDB.entrySet()){
            List<String> id = entry.getValue();
            id.remove(Integer.valueOf(orderId));
        }
    }
}

package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RepositoryLayer {
    private Map<String,Order> orderDB = new HashMap<>();
    private Map<String,DeliveryPartner> partnerDB = new HashMap<>();
    private Map<String,String> order_partnerPairDB = new HashMap<>(); // id->orderId & id partner
    private Map<String, List<String>> partner_orderDB = new HashMap<>();// id-> partnerId

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
        order_partnerPairDB.put(orderId,partnerId);//
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
        (partnerDB.get(partnerId)).setNumberOfOrders(partner_orderDB.get(partnerId).size());// setting number of orders to delivary partner

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

        for(String ordr:orderDB.keySet()){
            allOrders.add(ordr);
        }
       // another way
//        for(Map.Entry<String,Order> entry : orderDB.entrySet()){
//           allOrders.add(entry.getKey());
//       }
       return allOrders;
    }

    public Integer count_unassined_orders() {
        return orderDB.size()-order_partnerPairDB.size();
    }

    public Integer get_orders_left_after_giventime_byPartnerId(String time, String partnerId) {
        int t1  = Integer.parseInt(time.substring(0,2));
        int t2  = Integer.parseInt(time.substring(3));
        int Time =t1*60+t2;;
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
        String hh =String.valueOf (Time/60);
        String mm = String.valueOf(Time%60);
        if(hh.length()<2){
            hh="0"+hh;
        }
        if(mm.length()<2){
            mm="0"+mm;
        }
        String t1 =hh+":"+mm;

        return t1;//has to be updated
    }

    public void delete_partner_by_id(String partnerId) {
        partnerDB.remove(partnerId);
        List<String> order = partner_orderDB.get(partnerId);
        partner_orderDB.remove(partnerId);
        for (String id:order){ // removing orders pair with partner id given
            order_partnerPairDB.remove(id);
        }
    }

    public void delete_order_byId(String orderId) {
        orderDB.remove(orderId);
        String partnerId = order_partnerPairDB.get(orderId);
        order_partnerPairDB.remove(orderId);
        partner_orderDB.get(partnerId).remove(orderId);
        partnerDB.get(partnerId).setNumberOfOrders(partner_orderDB.get(partnerId).size());
    }
}

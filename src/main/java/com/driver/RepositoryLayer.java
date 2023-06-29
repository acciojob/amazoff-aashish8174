package com.driver;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class RepositoryLayer {
    //database//Hashmap
    HashMap<String,Order> orderDB;
    HashMap<String,DeliveryPartner> partnerDB;
    HashMap<String, List<String>> pairDB;
    HashSet<String> orderNotAssigned;

    public RepositoryLayer() {
        orderDB=new HashMap<>();
        partnerDB=new HashMap<>();
        pairDB=new HashMap<>();
        orderNotAssigned =new HashSet<>();

    }

    public void addOrder(Order order) {
        orderDB.put(order.getId(),order);
        orderNotAssigned.add(order.getId());
    }

    public void addPartner(String partnerId) {
        DeliveryPartner d1=new DeliveryPartner(partnerId);
        partnerDB.put(partnerId,d1);
    }


    public void addOrderPartnerPair(String orderId, String partnerId) {
        List<String> list=pairDB.getOrDefault(partnerId,new ArrayList<>());
        list.add(orderId);
        pairDB.put(partnerId,list);
        partnerDB.get(partnerId).setNumberOfOrders(partnerDB.get(partnerId).getNumberOfOrders()+1);

        orderNotAssigned.remove(orderId);
    }

    public Order getOrderById(String orderId) {
        return orderDB.get(orderId);
    }


    public DeliveryPartner getPartnerById(String partnerId) {
        return partnerDB.get(partnerId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
//        return partnerDB.get(partnerId).getNumberOfOrders();
        return pairDB.get(partnerId).size();
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        List<String> list=pairDB.getOrDefault(partnerId,new ArrayList<>());
        return list;
    }

    public List<String> getAllOrders() {
        List<String> list=new ArrayList<>();
        for(String s: orderDB.keySet()){
            list.add(s);
        }
        return list;
    }

    public Integer getCountOfUnassignedOrders() {
        return orderNotAssigned.size();
    }

    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        Integer count=0;
        //converting given string time to integer
        String arr[]=time.split(":"); //12:45
        int hr=Integer.parseInt(arr[0]);
        int min=Integer.parseInt(arr[1]);

        int total=(hr*60+min);

        List<String> list=pairDB.getOrDefault(partnerId,new ArrayList<>());
        if(list.size()==0)return 0; //no order assigned to partnerId

        for(String s: list){
            Order currentOrder=orderDB.get(s);
            if(currentOrder.getDeliveryTime()>total){
                count++;
            }
        }

        return count;

    }

    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        //return in HH:MM format
        String str="00:00";
        int max=0;

        List<String>list=pairDB.getOrDefault(partnerId,new ArrayList<>());
        if(list.size()==0)return str;
        for(String s: list){
            Order currentOrder=orderDB.get(s);
            max=Math.max(max,currentOrder.getDeliveryTime());
        }
        //convert int to string (140-> 02:20)
        int hr=max/60;
        int min=max%60;

        if(hr<10){
            str="0"+hr+":";
        }else{
            str=hr+":";
        }

        if(min<10){
            str+="0"+min;
        }
        else{
            str+=min;
        }
        return str;


    }

    public void deletePartnerById(String partnerId) {
        if(!pairDB.isEmpty()){
            orderNotAssigned.addAll(pairDB.get(partnerId));
        }

        partnerDB.remove(partnerId);

        pairDB.remove(partnerId);

    }

    public void deleteOrderById(String orderId) {
        //Delete an order and the corresponding partner should be unassigned
        if(orderDB.containsKey(orderId)){
            if(orderNotAssigned.contains(orderId)){
                orderNotAssigned.remove(orderId);
            }
            else{

                for(String str : pairDB.keySet()){
                    List<String> list=pairDB.get(str);
                    list.remove(orderId);
                }
            }
        }


    }
//    private Map<String,Order> orderDB = new HashMap<>();
//    private Map<String,DeliveryPartner> partnerDB = new HashMap<>();
//    private Map<String,String> order_partnerPairDB = new HashMap<>(); // id->orderId & id partner
//    private Map<String, List<String>> partner_orderDB = new HashMap<>();// id-> partnerId
//
//    public void add_order(Order order) {
//        orderDB.put(order.getId(),order);
//    }
//
//    public void add_partner(String partnerId) {
//        if(partnerDB.containsKey(partnerId)){
//           partnerDB.put(partnerId, partnerDB.get(partnerId));
//        }
//        else {
//            partnerDB.put(partnerId,new DeliveryPartner(partnerId));
//        }
//    }
//
//    public void order_partnerPair(String orderId, String partnerId) {
//        order_partnerPairDB.put(orderId,partnerId);//
//        if(partner_orderDB.containsKey(partnerId)){   // assign order to partnerId
//            List<String> a = partner_orderDB.get(partnerId);
//            a.add(orderId);
//            partner_orderDB.put(partnerId,a);
//        }
//        else {
//           List<String> firstOrder =new ArrayList<>();
//                   firstOrder.add(orderId);
//            partner_orderDB.put(partnerId,firstOrder);
//        }
//        (partnerDB.get(partnerId)).setNumberOfOrders(partner_orderDB.get(partnerId).size());// setting number of orders to delivary partner
//
//    }
//
//    public Order get_order_by_id(String orderId) {
//        if(orderDB.containsKey(orderId)){
//            return orderDB.get(orderId);
//        }
//        return null;
//    }
//
//    public DeliveryPartner get_partner_by_id(String partnerId) {
//        if(partnerDB.containsKey(partnerId)){
//            return partnerDB.get(partnerId);
//        }
//        return null;
//    }
//
//    public int get_order_count_byPartnerId(String partnerId) {
//
//            return partner_orderDB.get(partnerId).size();
//
//    }
//
//    public List<String> get_orders_by_partnerId(String partnerId) {
//
//            return partner_orderDB.get(partnerId);
//
//
//    }
//
//    public List<String> get_all_orders() {
//        List<String> allOrders = new ArrayList<>();
//        // traversing on all key and adding it to list
//
//        for(String ordr:orderDB.keySet()){
//            allOrders.add(ordr);
//        }
//       // another way
////        for(Map.Entry<String,Order> entry : orderDB.entrySet()){
////           allOrders.add(entry.getKey());
////       }
//       return allOrders;
//    }
//
//    public int count_unassined_orders() {
//
//        return orderDB.size()-order_partnerPairDB.size();
//    }
//
//    public int get_orders_left_after_giventime_byPartnerId(String time, String partnerId) {
//        int t1  = Integer.parseInt(time.substring(0,2));
//        int t2  = Integer.parseInt(time.substring(3));
//        int Time =t1*60+t2;;
//        List<String> listOfOrders = partner_orderDB.get(partnerId);
//        int totalLeft = 0;
//        for (String orderId :listOfOrders){
//            int t = orderDB.get(orderId).getDeliveryTime();
//            if(t>Time) totalLeft++;
//        }
//        return totalLeft;
//    }
//
//
//    public String get_last_delivary_time_bypartnerId(String partnerId) {
//        List<String> listOfOrders = partner_orderDB.get(partnerId);
//        int Time =0;
//        for (String orderId :listOfOrders){
//            int t = orderDB.get(orderId).getDeliveryTime();
//            if(t>Time) Time=t;
//        }
//        String hh =String.valueOf (Time/60);
//        String mm = String.valueOf(Time%60);
//        if(hh.length()<2){
//            hh="0"+hh;
//        }
//        if(mm.length()<2){
//            mm="0"+mm;
//        }
//        String t1 =hh+":"+mm;
//
//        return t1;//has to be updated
//    }
//
//    public void delete_partner_by_id(String partnerId) {
//        partnerDB.remove(partnerId);
//        List<String> order = partner_orderDB.get(partnerId);
//        partner_orderDB.remove(partnerId);
//        for (String id:order){ // removing orders pair with partner id given
//            order_partnerPairDB.remove(id);
//        }
//    }
//
//    public void delete_order_byId(String orderId) {
//        orderDB.remove(orderId);
//        String partnerId = order_partnerPairDB.get(orderId);
//        order_partnerPairDB.remove(orderId);
//        partner_orderDB.get(partnerId).remove(orderId);
//        partnerDB.get(partnerId).setNumberOfOrders(partner_orderDB.get(partnerId).size());
//    }
}

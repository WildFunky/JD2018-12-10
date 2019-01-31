package by.it.subach.jd02_02;

import java.util.Deque;
import java.util.LinkedList;

class DequeBuyer {
    private static  final Integer MONITOR = 0 ;

    static  Integer getMonitor(){
        return MONITOR;
    }

    private static Deque<Buyer> q = new LinkedList<>();

    static void add(Buyer buyer) {
        synchronized (MONITOR) {
            q.addLast(buyer);
        }
    }

    static Buyer poll() {
        synchronized (MONITOR) {
            return q.pollFirst();
        }
    }

    static int getBuyerSize(){
        synchronized (MONITOR) {
            return q.size();
        }
    }



}
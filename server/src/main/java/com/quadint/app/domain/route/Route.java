package com.quadint.app.domain.route;

import com.quadint.app.domain.transportation.Bus;
import com.quadint.app.domain.transportation.Subway;
import com.quadint.app.domain.transportation.TransportationType;
import com.quadint.app.domain.transportation.Transportation;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Route implements Comparable<Route>{
    private int totalTime;
    private String busTransitCount;
    private String subwayTransitCount;
    private String firstStartStation;
    private String lastEndStation;
    private List<Transportation> transportationList = new ArrayList();

    public Route(String busTransitCount, String subwayTransitCount, String firstStartStation, String lastEndStation) {
        this.busTransitCount = busTransitCount;
        this.subwayTransitCount = subwayTransitCount;
        this.firstStartStation = firstStartStation;
        this.lastEndStation = lastEndStation;
    }

    @Override
    public int compareTo(Route o) {
        return this.totalTime - o.totalTime;
    }

    public List<String> getFirstTransportation() {
        Integer walkTime = 0; //처음으로 탑승장까지 도보로 걷는 시간
        for (int i = 0; i < transportationList.size(); ++i) {
            Transportation t = transportationList.get(i);

            if (t.getTransportationType() == TransportationType.WALK) {
                walkTime += t.getTime();
            }
            else if (t.getTransportationType() == TransportationType.BUS){
                Bus bus = (Bus) t;
                return List.of(TransportationType.BUS.name(), bus.getStartLocalStationID(), bus.getRouteId(), walkTime.toString());
            }
            else if (t.getTransportationType() == TransportationType.SUBWAY) {
                Subway subway = (Subway) t;
                return List.of(TransportationType.SUBWAY.name(), subway.getStartID(), subway.getWayCode(), walkTime.toString());
            }
        }
        //todo: 경로에 도보만 있는 경우 예외처리 추가
        return null;
    }

    private List<Transportation> getTransportation() {
        return List.copyOf(transportationList);
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public void addTransportation(Transportation t) {
        transportationList.add(t);
    }

    @Override
    public String toString() {
        return "총시간:" + totalTime + "분 출발:" + firstStartStation + " 도착:" + lastEndStation + " 버스:" + busTransitCount + " 지하철:" + subwayTransitCount;
    }
}

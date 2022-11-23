package com.quadint.app.domain.route;

import com.quadint.app.domain.transportation.Bus;
import com.quadint.app.domain.transportation.TrafficType;
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
        Integer walkTime = 0; //처음으로 탑승할 정류장 혹은 역까지 도보로 걷는 시간
        for (int i = 0; i < transportationList.size(); ++i) {
            Transportation t = transportationList.get(i);

            if (t.getTrafficType() == TrafficType.WALK) {
                walkTime += t.getTime();
            }
            else if (t.getTrafficType() == TrafficType.BUS){
                //todo: 반환값 DTO, 추상 메서드등을 사용하여 리팩토링 예정
                Bus bus = (Bus) t;
                return List.of(bus.getStartLocalStationID(), bus.getRouteId(), walkTime.toString());
            }
        }
        //todo: 경로에 도보만 있는 경우
        return null;
    }

    public List<Transportation> getTransportationList() {
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
        return "총시간:" + totalTime + "분 출발정류장:" + firstStartStation + " 최종도착정류장:" + lastEndStation + " 버스:" + busTransitCount + " 지하철:" + subwayTransitCount;
    }
}

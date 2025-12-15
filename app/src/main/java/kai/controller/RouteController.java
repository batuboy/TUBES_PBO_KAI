package kai.controller;

import java.util.List;

import kai.models.train.Route;
import kai.models.train.Station;
import kai.repository.RouteRepository;

public class RouteController {
    private final RouteRepository routeRepository;

    public RouteController() {
        routeRepository = new RouteRepository();
    }

    public void addRoute(String routeId, Station origin, Station destination, List<Station> stops) {
        routeRepository.addRoute(new Route(routeId, origin, destination, stops));
    }

    public Route getRouteById(String routeId) {
        return routeRepository.getRouteById(routeId);
    }

    public void updateRoute(Route route) {
        routeRepository.updateRoute(route);
    }

    public List<Route> getAllRoute() {
        return routeRepository.getAllRoute();
    }
}

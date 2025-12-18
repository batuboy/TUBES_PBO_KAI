package kai.repository;

import kai.models.train.Route;
import kai.models.train.Station;
import kai.database.DbConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RouteRepository {

    private DbConnect dbConnect;

    public RouteRepository() {
        dbConnect = new DbConnect();
    }

    public List<Route> getAllRoutes() {
        List<Route> list = new ArrayList<>();
        String sql = "SELECT routeId, originId, destinationId FROM route";

        StationRepository stationRepo = new StationRepository();

        try (Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Station origin = stationRepo.getStationById(rs.getString("originId"));
                Station destination = stationRepo.getStationById(rs.getString("destinationId"));

                Route route = new Route(
                        rs.getString("routeId"),
                        origin,
                        destination);
                list.add(route);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Get route by ID
    public Route getRouteById(String routeId) {
        String sql = "SELECT routeId, originId, destinationId FROM route WHERE routeId = ?";
        try (Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, routeId);
            ResultSet rs = ps.executeQuery();

            
            if (rs.next()) {
                StationRepository stationRepo = new StationRepository();
    
                Station origin = stationRepo.getStationById(rs.getString("originId"));
                Station destination = stationRepo.getStationById(rs.getString("destinationId"));
                return new Route(
                        rs.getString("routeId"),
                        origin,
                        destination);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // // Helper to display nicely in dropdown
    // public String getDisplayName(Route r, StationRepository stationRepo) {
    //     Station origin = stationRepo.getById(r.getOriginId());
    //     Station dest = stationRepo.getById(r.getDestinationId());
    //     return origin.getName() + " → " + dest.getName();
    // }

    // public void addRoute(Route route) {
    // String sqlRoute = "INSERT INTO Routes (route_id, origin_id, destination_id)
    // VALUES (?, ?, ?)";
    // String sqlStops = "INSERT INTO Route_Stops (route_id, station_id, stop_order)
    // VALUES (?, ?, ?)";

    // try (Connection conn = dbConnect.getConnection()) {

    // PreparedStatement psRoute = conn.prepareStatement(sqlRoute);
    // psRoute.setString(1, route.getRouteId());
    // psRoute.setString(2, route.getOrigin());
    // psRoute.setString(3, route.getDestination());
    // psRoute.executeUpdate();

    // PreparedStatement psStops = conn.prepareStatement(sqlStops);
    // int order = 1;

    // for (String stationId : route.getStops()) {
    // psStops.setString(1, route.getRouteId());
    // psStops.setString(2, stationId);
    // psStops.setInt(3, order);
    // psStops.executeUpdate();
    // order++;
    // }

    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }

    // public Route getRouteById(String routeId) {
    // String sqlRoute = "SELECT origin_id, destination_id FROM Routes WHERE
    // route_id = ?";
    // String sqlStops = "SELECT rs.station_id, s.name, s.city " +
    // "FROM Route_Stops rs " +
    // "JOIN Stations s ON rs.station_id = s.station_id " +
    // "WHERE rs.route_id = ? " +
    // "ORDER BY rs.stop_order";

    // try (Connection conn = dbConnect.getConnection()) {

    // // Ambil origin dan destination
    // PreparedStatement psRoute = conn.prepareStatement(sqlRoute);
    // psRoute.setString(1, routeId);
    // ResultSet rsRoute = psRoute.executeQuery();

    // String originId = "";
    // String destinationId = "";
    // if (rsRoute.next()) {
    // originId = rsRoute.getString("origin_id");
    // destinationId = rsRoute.getString("destination_id");
    // }

    // // Ambil daftar station (stops)
    // PreparedStatement psStops = conn.prepareStatement(sqlStops);
    // psStops.setString(1, routeId);
    // ResultSet rsStops = psStops.executeQuery();

    // List<Station> stops = new ArrayList<>();
    // while (rsStops.next()) {
    // Station s = new Station(
    // rsStops.getString("station_id"),
    // rsStops.getString("name"),
    // rsStops.getString("city"));
    // stops.add(s);
    // }

    // return new Route(
    // routeId,
    // originId,
    // destinationId,
    // convertStationToId(stops));

    // } catch (SQLException e) {
    // e.printStackTrace();
    // }

    // return null;
    // }

    private List<String> convertStationToId(List<Station> stations) {
        List<String> ids = new ArrayList<>();
        for (Station s : stations) {
            ids.add(s.getStationId());
        }
        return ids;
    }

}

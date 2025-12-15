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

import javax.naming.spi.DirStateFactory.Result;

public class RouteRepository {
    private DbConnect dbConnect;

    public RouteRepository() {
        dbConnect = new DbConnect();
    }

    public void addRoute(Route route) {
        String sqlRoute = "INSERT INTO Route (routeId, originId, destinationId) VALUES (?, ?, ?)";
        try (Connection conn = DbConnect.getConnection()) {

            List<Station> stops = route.getStops();
            if (!stops.get(0).equals(route.getOrigin())) {
                throw new IllegalArgumentException("Stop pertama harus sama dengan origin.");
            }

            if (!stops.get(stops.size() - 1).equals(route.getDestination())) {
                throw new IllegalArgumentException("Stop terakhir harus sama dengan destination.");
            }

            PreparedStatement psRoute = conn.prepareStatement(sqlRoute);
            psRoute.setString(1, route.getRouteId());
            psRoute.setString(2, route.getOrigin().getStationId());
            psRoute.setString(3, route.getDestination().getStationId());
            psRoute.executeUpdate();
            
            insertRouteStops(conn, route);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Route getRouteById(String routeId) {
        String sqlStops = "SELECT rs.stationId, s.name, s.city " +
                "FROM Route_Stops rs " +
                "JOIN Station s ON rs.stationId = s.stationId " +
                "WHERE rs.routeId = ? " +
                "ORDER BY rs.stopOrder";

        try (Connection conn = DbConnect.getConnection()) {
            PreparedStatement psStops = conn.prepareStatement(sqlStops);
            psStops.setString(1, routeId);
            ResultSet rsStops = psStops.executeQuery();

            List<Station> stops = new ArrayList<>();
            while (rsStops.next()) {
                Station s = new Station(
                        rsStops.getString("stationId"),
                        rsStops.getString("name"),
                        rsStops.getString("city"));
                stops.add(s);
            }

            if (stops.isEmpty()) {
                return null;
            }

            Station origin = stops.get(0);
            Station destination =stops.get(stops.size()-1);

            return new Route(
                routeId,
                origin,
                destination,
                stops);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Route> getAllRoute() {
        List<Route> routes = new ArrayList<>();

        String sqlRoute = "SELECT * FROM Route";
        String sqlStops = "SELECT rs.station_id, s.name, s.city " +
        "FROM Route_Stops rs " +
        "JOIN Stations s ON rs.station_id = s.station_id " +
        "WHERE rs.route_id = ? " +
        "ORDER BY rs.stop_order";

        try (Connection conn = DbConnect.getConnection()){
            PreparedStatement psRoute = conn.prepareStatement(sqlRoute);
            ResultSet rsRoute = psRoute.executeQuery();

            while (rsRoute.next()) {
                PreparedStatement psStops = conn.prepareStatement(sqlStops);
                psStops.setString(1, rsRoute.getString("routeId"));
                ResultSet rsStops = psStops.executeQuery();

                List<Station> stops = new ArrayList<>();
                while (rsStops.next()) {
                    Station s = new Station(
                        rsStops.getString("stationId"),
                        rsStops.getString("name"),
                        rsStops.getString("city"));
                    stops.add(s);
                }

                Route route = new Route(rsRoute.getString("routeId"), stops.get(0), stops.get(stops.size() -1), stops);
                routes.add(route);
            }     
        } catch (Exception e) {
            e.printStackTrace();
        }
        return routes;
    }

    public void updateRoute(Route route) {
        String sqlUpdate = "UPDATE Route SET originId = ?, destinationId = ? WHERE routeId = ?";
        String sqlDelete = "DELETE FROM Route_Stops WHERE routeId = ?";

        try(Connection conn = DbConnect.getConnection()) {
            conn.setAutoCommit(false);

            try(PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate)) {
                psUpdate.setString(1, route.getOrigin().getStationId());
                psUpdate.setString(2, route.getDestination().getStationId());
                psUpdate.setString(3, route.getRouteId());
                psUpdate.executeUpdate();

            }

            try(PreparedStatement psDelete = conn.prepareStatement(sqlDelete)) {
                psDelete.setString(1, route.getRouteId());
                psDelete.executeUpdate();
            }

            insertRouteStops(conn, route);
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void insertRouteStops(Connection conn, Route route) throws SQLException {
        String sqlStops = "INSERT INTO Route_Stops (routeId, stationId, stopOrder) VALUES (?, ?, ?)";
        PreparedStatement psStops = conn.prepareStatement(sqlStops);
        int order = 1;
        for (Station station : route.getStops()) {
            psStops.setString(1, route.getRouteId());
            psStops.setString(2, station.getStationId());
            psStops.setInt(3, order++;);
            psStops.executeUpdate();
        }
    }
}

package kai.repository;

import kai.models.train.*;
import kai.models.train.num.*;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;

import kai.database.DbConnect;

public class RailCarRepository{
    private DbConnect dbConnect; 

    public RailCarRepository(){
        this.dbConnect = new DbConnect();
    }

    public List<Railcar> findAllRailCar() {
        List<Railcar> list = new ArrayList<>();

        String sql = "SELECT railcarId, name, status FROM railcar";

        try (
                Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Status status = Status.valueOf(rs.getString("status").toUpperCase());

                Railcar railcar = new Railcar(
                        rs.getString("railcarId"),
                        rs.getString("name"),
                        status);
                list.add(railcar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Railcar> findAvailableAllRailCar() {
        List<Railcar> list = new ArrayList<>();

        String sql = "SELECT railcarId, name, status FROM railcar WHERE status = 'AVAILABLE'";

        try (
                Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Status status = Status.valueOf(rs.getString("status").toUpperCase());

                Railcar railcar = new Railcar(
                        rs.getString("railcarId"),
                        rs.getString("name"),
                        status);
                list.add(railcar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateStatusRailCar(String id, Status status) {
        String sql = "UPDATE railcar SET status = ? WHERE railcarId = ?";

        try (
                Connection conn = DbConnect.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status.name()); 
            ps.setString(2, id);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void addCarriage(Carriage carriage){
        String sqlCarriage = "INSERT INTO carriage(railcar_id, capacity) VALUES (?, ?)";
        String sqlSeat = "INSERT INTO railcar_seats(railcar_id, seat_number, status) VALUES (?,?,?)";

        try (Connection conn = DbConnect.getConnection()) {
            PreparedStatement psCarriage = conn.prepareStatement(sqlCarriage);
            psCarriage.setString(1, carriage.getRailcarId());
            psCarriage.setInt(2, carriage.getCapacity());
            psCarriage.execute();
            
            PreparedStatement psSeat = conn.prepareStatement(sqlSeat);
            for (int i = 0; i > carriage.getSeat().size(); i++) { 
                psSeat.setString(1, carriage.getRailcarId());
                psSeat.setString(2, carriage.getSeat().get(i).getSeatNumber());
                psSeat.setString(3, carriage.getSeat().get(i).getSeatStatus()); 
                psSeat.addBatch();
            }
            psSeat.executeBatch();
        } catch (Exception e) {
        }
    }

    public void addRailcar(Railcar railcar){
        String sqlRailcar = "INSERT INTO Railcar(railcar_id, name, type, status) VALUES(?, ?, ?, ?)";
        
        try(Connection conn = DbConnect.getConnection()){
            PreparedStatement psRailcar = conn.prepareStatement(sqlRailcar);
            psRailcar.setString(1, railcar.getRailcarId());
            psRailcar.setString(2, railcar.getName());
            psRailcar.setString(3, railcar.getRailcarType());
            psRailcar.execute();
        }catch(Exception e){

        }
    }

    public void addWagon(Wagon wagon){
        String sqlWagon = "INSERT INTO wagon (trainId, cargoCapacity) VALUES (?, ?)";
        try (Connection conn = DbConnect.getConnection()){
            PreparedStatement psWagon = conn.prepareStatement(sqlWagon);
            psWagon.setString(1, wagon.getRailcarId());
            psWagon.setDouble(2, wagon.getCargoCapacity());
            psWagon.execute();
        } catch (Exception e) {
        }

    }

    public Railcar getRailcarById(String railcarId){
        String sqlRailcar = "SELECT * FROM railcar WHERE railcar_id = ?";
        String sqlSeats = "SELECT seat_number FROM railcar_seats WHERE railcar_id = ?";
     
        try (Connection conn = DbConnect.getConnection()) {
            PreparedStatement psRailcar = conn.prepareStatement(sqlRailcar);
            psRailcar.setString(1, railcarId);
            ResultSet rsRailcar = psRailcar.executeQuery();

            if (rsRailcar.next()){
                String name = rsRailcar.getString("name");
                TrainClass trainClass = TrainClass.valueOf(rsRailcar.getString("train_class"));
                int capacity = rsRailcar.getInt("capacity");
                Status status = Status.valueOf(rsRailcar.getString("status"));
            }            
        }catch (Exception e){
            e.printStackTrace();

        }

        return null;
    }
}


    
 
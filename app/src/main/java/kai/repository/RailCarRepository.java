package kai.repository;

import kai.models.train.*;
import kai.models.train.num.*;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;

import kai.database.DbConnect;

public class RailCarRepository{
    private DbConnect dbConnect; 

    public RailCarRepository(){
        dbConnect = new DbConnect();
    }
    
    // INSERT
    public void addCarriage(Carriage carriage){
        String sqlCarriage = "INSERT INTO carriage(railcar_id, capacity) VALUES (?, ?)";
        String sqlSeat = "INSERT INTO railcar_seats(railcar_id, seat_number, status) VALUES (?,?,?)";

        try (Connection conn = DbConnect.getConnection()) {
            PreparedStatement psCarriage = conn.prepareStatement(sqlCarriage);
            psCarriage.setString(1, carriage.getRailcarId());
            psCarriage.setInt(2, carriage.getCapacity());
            psCarriage.executeUpdate();
            
            PreparedStatement psSeat = conn.prepareStatement(sqlSeat);
            for (int i = 0; i > carriage.getSeat().size(); i++) { 
                psSeat.setString(1, carriage.getRailcarId());
                psSeat.setString(2, carriage.getSeat().get(i).getSeatNumber());
                psSeat.setString(3, carriage.getSeat().get(i).getSeatStatus()); 
                psSeat.addBatch();
            }
            psSeat.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addRailcar(Railcar railcar){
        String sqlRailcar = "INSERT INTO Railcar(railcar_id, name, type, status) VALUES(?, ?, ?, ?)";
        
        try(Connection conn = DbConnect.getConnection()){
            PreparedStatement psRailcar = conn.prepareStatement(sqlRailcar);
            psRailcar.setString(1, railcar.getRailcarId());
            psRailcar.setString(2, railcar.getName());
            psRailcar.setString(3, railcar.getRailcarType());
            psRailcar.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void addWagon(Wagon wagon){
        String sqlWagon = "INSERT INTO wagon (trainId, cargoCapacity) VALUES (?, ?)";
        try (Connection conn = DbConnect.getConnection()){
            PreparedStatement psWagon = conn.prepareStatement(sqlWagon);
            psWagon.setString(1, wagon.getRailcarId());
            psWagon.setDouble(2, wagon.getCargoCapacity());
            psWagon.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // SELECT

    public Wagon getWagonById(String railcarId){
        String strSql = "SELECT * FROM wagon WHERE railcar_id = ?";
        Railcar rc = getRailcarById(railcarId);

        try(Connection conn = DbConnect.getConnection()){
            PreparedStatement psGetWagon = conn.prepareStatement(strSql);
            psGetWagon.setString(1, railcarId);
            ResultSet rsGetWagon = psGetWagon.executeQuery();

            if(rsGetWagon.next()){
                double cargoCapacity = rsGetWagon.getDouble("cargoCapacity");
                return new Wagon(railcarId, rc.getName(), rc.getStatus(), cargoCapacity);
            }
                
        }catch(Exception e){
            e.printStackTrace();
        }
        return null; 
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
                RailcarType type = RailcarType.valueOf(rsRailcar.getString("type"));
                int capacity = rsRailcar.getInt("capacity");
                Status status = Status.valueOf(rsRailcar.getString("status"));
                return new Railcar(railcarId, name, type, status);
            }            
        }catch (Exception e){
            e.printStackTrace();

        }
        return null;
    }

    public Wagon getCarriageById(String railcarId){
        String strSql = "SELECT * FROM carriage WHERE railcar_id = ?";
        Railcar rc = getRailcarById(railcarId);

        try(Connection conn = DbConnect.getConnection()){
            PreparedStatement psGetCarriage = conn.prepareStatement(strSql);
            psGetCarriage.setString(1, railcarId);
            ResultSet rsGetCarriage = psGetCarriage.executeQuery();

            if(rsGetCarriage.next()){
                double cargoCapacity = rsGetCarriage.getDouble("cargoCapacity");
                return new Wagon(railcarId, rc.getName(), rc.getStatus(), cargoCapacity);
            }
                
        }catch(Exception e){
            e.printStackTrace();
        }
        return null; 
    }

    // UPDATE 
    public boolean updateRailcarById(String railcarId, String name, RailcarType type, Status status){
        String strSql = "UPDATE railcar SET name = ?, type = ?, status = ? WHERE railcar_id = ?";

        try(Connection conn = DbConnect.getConnection()){
            PreparedStatement psUpdateRailcar = conn.prepareStatement(strSql);
            psUpdateRailcar.setString(1, name);
            psUpdateRailcar.setString(2, type.toString());
            psUpdateRailcar.setString(3, status.toString());
            psUpdateRailcar.executeUpdate();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateWagonById(String railcarId, double cargoCapacity){
        String strSql = "UPDATE wagon SET cargoCapacity = ? WHERE railcar_id = ?";

        try(Connection conn = DbConnect.getConnection()){
            PreparedStatement psUpdateWagon = conn.prepareStatement(strSql);
            psUpdateWagon.setDouble(1, cargoCapacity);
            psUpdateWagon.setString(2, railcarId);
            psUpdateWagon.executeUpdate();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateCarriageById(String railcarId, TrainClass trainClass){
        String strSql = "UPDATE carriage SET capacity = ? WHERE railcar_id = ?";

        try(Connection conn = DbConnect.getConnection()){
            PreparedStatement psUpdateCarriage = conn.prepareStatement(strSql);
            psUpdateCarriage.setString(1, trainClass.toString());
            psUpdateCarriage.setString(2, railcarId);
            psUpdateCarriage.executeUpdate();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

}


    
 
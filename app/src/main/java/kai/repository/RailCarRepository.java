package kai.repository;

import kai.models.train.*;
import kai.models.train.num.*;
import kai.models.train.num.TrainClass;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;

import kai.database.DbConnect;

public class RailCarRepository{
    private DbConnect dbConnect; 

    public RailCarRepository(){
        dbConnect = new DbConnect();
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


    
 
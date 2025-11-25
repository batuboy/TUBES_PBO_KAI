package kai.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import kai.database.DbConnect;
import kai.models.train.*;

import java.util.ArrayList;
import java.util.List;

public interface ITrainRepository{

    void addTrain(Locomotive train);
    Locomotive getTrainById(String id);
    List<Locomotive> getAllTrains();
    void updateTrain(Locomotive train);
    void deleteTrain(String id);

    // public List<Locomotive> getAllLocomotive(){
    //     List<Locomotive> arrList = new ArrayList<>();
    //     String sql = "SELECT l.id ";
        
    //     try {
    //         Connection conn = DbConnect.getConnection();
    //         PreparedStatement ps = conn.prepareCall(sql);
    //         ResultSet rs = ps.executeQuery();
            
    //         while (rs.next()) { 
    //             int locomotiveId = rs.getInt("locomotiveID");
    //             String locomotiveName = rs.getString("locomotiveName");
    //             String status = rs.getString("status");
    //             String locomotiveType = rs.getString("locomotiveType");

    //             if(locomStringType == "DIESEL"){
    //                 DieselTrain locomotive = new DieselTrain(locomotiveId, locomotiveName, status, locomotiveType);
    //             }
    //             arrList.add(locomotive);
    //         }

    //         return arrList;

    //     } catch (Exception e) {

    //     }

    //     return null;
    // }
}

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

    void addLocomotive(Locomotive train);
    Locomotive getLocomotiveById(String id);
    List<Locomotive> getAllLocomotive();
    void updateLocomotive(Locomotive locomotive);
    void deletelocomotive(String id);

}

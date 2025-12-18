package kai.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kai.database.DbConnect;
import kai.repository.ReportRepository;

public class ReportController {
    ReportRepository reportRepo;

    public ReportController(){
        reportRepo = new ReportRepository();
    }

    
    public int getTotalTicketsSold() {
        return reportRepo.getTotalTicketsSold();
    }

    public double getTotalRevenue() {
        return reportRepo.getTotalRevenue();
    }
    public int getTotalSchedules() {
        return reportRepo.getTotalSchedules();
    }
}

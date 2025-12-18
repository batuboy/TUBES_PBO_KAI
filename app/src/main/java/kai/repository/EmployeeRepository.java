package kai.repository;

import kai.models.user.*;
import kai.models.user.num.Position;
import kai.database.DbConnect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

    private DbConnect dbConnect; 
    private final UserRepository userRepo;

    public EmployeeRepository(){
        userRepo = new UserRepository();
        dbConnect = new DbConnect();
    }
    
    public boolean addEmployee(Employee emp){
       
        boolean userCreated = userRepo.register(emp);
        
        if(!userCreated) return false;
        String sql = "INSERT employee INTO (nik, namaLengkap, nomorTelepon, email, password, employeeId, position, salary) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnect.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, emp.getNik());
            ps.setString(2, emp.getNamaLengkap());
            ps.setString(3, emp.getNomorTelepon());
            ps.setString(4, emp.getEmail());
            ps.setString(5, emp.getPassword());
            ps.setString(6, emp.getEmployeeId());
            ps.setDouble(7, emp.getSalary());
        } catch (Exception e) {
        }

        return true;
    }

    public void addEmployee(Conductor conductor){
        String sql = "INSERT conductor INTO (nik, namaLengkap, nomorTelepon, email, password, employeeId, position, salary, vehicleId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnect.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, conductor.getNik());
            ps.setString(2, conductor.getNamaLengkap());
            ps.setString(3, conductor.getNomorTelepon());
            ps.setString(4, conductor.getEmail());
            ps.setString(5, conductor.getPassword());
            ps.setString(6, conductor.getEmployeeId());
            ps.setDouble(7, conductor.getSalary());
            ps.setString(8, conductor.getAreaAssigned().getStationId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Employee getEmployeeById(String empId){

    }

    public List<Employee> getAllEmployees(){

    }

    public void updateEmployee(Employee emp){

    }

    public void deleteEmployee(String empId){

    }

    public boolean save(Employee emp) {
        String sql = "INSERT INTO employees (user_id, nik, nama_lengkap, nomor_telepon, email, password, "
                + "employee_id, position, salary, admin_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, emp.getUserId());
            ps.setString(2, emp.getNik());
            ps.setString(3, emp.getNamaLengkap());
            ps.setString(4, emp.getNomorTelepon());
            ps.setString(5, emp.getEmail());
            ps.setString(6, emp.getPassword());
            ps.setString(7, emp.getEmployeeId());
            ps.setString(8, emp.getPosition().name());
            ps.setDouble(9, emp.getSalary());
            
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public Employee findByEmployeeId(String id) {
        String sql = "SELECT * FROM employees WHERE employee_id = ?";

        try (Connection conn = DbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapToEmployee(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public Employee login(String email, String password) {
        String sql = "SELECT * FROM employees WHERE email = ? AND password = ?";

        try (Connection conn = DbConnect.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapToEmployee(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public List<Employee> getAll() {
        String sql = "SELECT * FROM employees";
        List<Employee> list = new ArrayList<>();

        try (Connection conn = DbConnect.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapToEmployee(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    private Employee mapToEmployee(ResultSet rs) throws Exception {
        boolean isAdmin = rs.getString("admin_id") != null;

        if (isAdmin) {
            return new Admin(
                    rs.getString("user_id"),
                    rs.getString("nik"),
                    rs.getString("nama_lengkap"),
                    rs.getString("nomor_telepon"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("employee_id"),
                    Position.valueOf(rs.getString("position")),
                    rs.getDouble("salary")
            );
        }

        return new Employee(
                rs.getString("user_id"),
                rs.getString("nik"),
                rs.getString("nama_lengkap"),
                rs.getString("nomor_telepon"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("employee_id"),
                Position.valueOf(rs.getString("position")),
                rs.getDouble("salary")
        );
    }
}

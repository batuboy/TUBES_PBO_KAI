package kai.models.user;

import kai.models.user.num.Position;

public abstract class Employee extends User {
    private String employeeId;
    private Position position;   
    private double salary;
    
    public Employee(String userId, String nik, String namaLengkap, String nomorTelepon, String email, String password,
            String employeeId, Position position, double salary) {
        super(userId, nik, namaLengkap, nomorTelepon, email, password);
        this.employeeId = employeeId;
        this.position = position;
        this.salary = salary;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }    

    
}

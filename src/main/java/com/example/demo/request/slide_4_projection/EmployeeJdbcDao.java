package com.example.demo.request.slide_4_projection;

import com.example.demo.domain.Employee;
import com.example.demo.dto.SankiDto;
import com.example.demo.dto.ShortEmployeeDto;
import com.example.demo.request.dao.BaseJdbcDao;

import java.sql.*;
import java.util.*;

@SuppressWarnings("ALL")
public class EmployeeJdbcDao extends BaseJdbcDao<Employee> {

    public void save(Employee employee) throws Exception {
        String sql = "insert into employee(id, first_mame, last_name, department_id) values(?, ?, ?, ?)";
        try (Connection con = getConnection()) {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setLong(1, employee.getId());
            statement.setString(2, employee.getFirstName());
            statement.setString(3, employee.getLastName());
            statement.setLong(4, employee.getDepartment().getId());
            statement.executeUpdate(sql);
        }
    }

    public boolean update(Employee employee) throws Exception {
        String sql = "update employee set first_mame = ?, last_name = ?, department_id = ? where id = ?";
        boolean flag = false;
        try (Connection con = getConnection()) {
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setLong(3, employee.getDepartment().getId());
            statement.setLong(4, employee.getId());
            if (statement.executeUpdate(sql) > 0) {
                flag = true;
            }
        }
        return flag;
    }

    public String getById(int empNo) throws Exception {
        String sql = "select * from emp where empno=" + empNo;
        StringBuilder sb = new StringBuilder();
        try (Connection con = getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                sb.append(rs.getInt(1));
                sb.append("t");
                sb.append(rs.getString(2));
                sb.append("t");
                sb.append(rs.getDouble(3));
                sb.append("t");
                sb.append(rs.getInt(4));
            }
        }
        return sb.toString();
    }

    public String getAll(int empNo) throws Exception {
        String sql = "select * from emp where empno=" + empNo;
        StringBuilder sb = new StringBuilder();
        Connection con = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "manager");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                sb.append(rs.getInt(1));
                sb.append("t");
                sb.append(rs.getString(2));
                sb.append("t");
                sb.append(rs.getDouble(3));
                sb.append("t");
                sb.append(rs.getInt(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (Exception e) {
            }
        }
        return sb.toString();
    }

    public Collection<Employee> getByDepartmentId(int id) throws Exception {
        String sql = "select * from employee where department_id = ?";
        Connection connection = null;
        Collection<Employee> result = new ArrayList<>();
        try (Connection con = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                result.add(mapRowToEmployee(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (Exception e) {
            }
        }
        return result;
    }

    public Map<Long, Long> getContractsByEmployee() throws Exception {
        String sql = "SELECT e.id, COUNT(ct.id) FROM employee e " +
                "LEFT JOIN client c on c.account_id=e.id " +
                "LEFT JOIN contract ct on ct.client_id = c.id " +
                "GROUP BY e.id";
        try (Connection con = getConnection()) {
            Map<Long, Long> result = new HashMap<>();
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.put(
                        resultSet.getLong(1),
                        resultSet.getLong(2));
            }
            return result;
        }
    }


    public Collection<ShortEmployeeDto> getCustomEmployeeByDepartmentId(int id) throws Exception {
        String sql = "select id, first_name from employee where department_id = ?";
        try (Connection con = getConnection()) {
            Collection<ShortEmployeeDto> result = new ArrayList<>();
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                result.add(mapRowToCustomEmployeeDto(resultSet));
            }
            return result;
        }
    }

    private Collection<Employee> mapToCustomEmployee(ResultSet resultSet) {
        System.out.println();
        return null;
    }

    public boolean delete(Long empno) throws Exception {
        String sql = "delete from emp where empno=" + empno;
        Connection con = null;
        boolean flag = false;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "manager");
            Statement st = con.createStatement();
            if (st.executeUpdate(sql) > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (Exception e) {
            }
        }
        return flag;
    }

    private Employee mapRowToEmployee(ResultSet resultSet) {
        System.out.printf("");
        return null;
    }

    private ShortEmployeeDto mapRowToCustomEmployeeDto(ResultSet resultSet) {
        System.out.printf("");
        return null;
    }

    public List<SankiDto> getSankiReport() throws Exception {
        String sql = "SELECT cl.name, c.amount, c.contract_type from contract c " +
                "join client cl on c.client_id = cl.id " +
                "UNION ALL " +
                "select c.contract_type, c.amount, " +
                "e.first_name || ' ' || e.last_name from contract c " +
                "join employee e on c.account_id = e.id";
        try (Connection conn = getConnection()) {
            List<SankiDto> result = new LinkedList<>();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(new SankiDto(
                        rs.getString(1),
                        rs.getLong(2),
                        rs.getString(3)));
            }
            return result;
        }
    }
}

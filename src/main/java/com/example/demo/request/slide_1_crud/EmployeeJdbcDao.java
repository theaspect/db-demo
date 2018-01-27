package com.example.demo.request.slide_1_crud;

import com.example.demo.domain.Employee;
import com.example.demo.request.dao.BaseJdbcDao;

import java.sql.*;

@SuppressWarnings("ALL")
public class EmployeeJdbcDao extends BaseJdbcDao<Employee>{

    public void save(Employee employee) throws Exception {
        String sql = "insert into employee(id, first_mame, last_name) values(?, ?, ?)";
        try (Connection con = getConnection()){
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setLong(1, employee.getId());
            statement.setString(2, employee.getFirstName());
            statement.setString(3, employee.getLastName());
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean update(Employee employee) throws Exception {
        String sql = "update employee set first_mame = ?, last_name = ? where id = ?";
        boolean flag = false;
        try (Connection con = getConnection()){
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setLong(3, employee.getId());
            if (statement.executeUpdate(sql) > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public String getById(Long empno) throws Exception {
        String sql = "select * from emp where empno=" + empno;
        StringBuilder sb = new StringBuilder();
        try (Connection con = getConnection()){
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                sb.append(rs.getInt(1));
                sb.append("\t");
                sb.append(rs.getString(2));
                sb.append("\t");
                sb.append(rs.getDouble(3));
                sb.append("\t");
                sb.append(rs.getInt(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public String getAll() throws Exception {
        String sql = "select * from emp";
        StringBuilder sb = new StringBuilder();
        try (Connection con = getConnection()){
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                sb.append(rs.getInt(1));
                sb.append("\t");
                sb.append(rs.getString(2));
                sb.append("\t");
                sb.append(rs.getDouble(3));
                sb.append("\t");
                sb.append(rs.getInt(4));
                sb.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public boolean delete(Long empno) throws Exception {
        String sql = "delete from emp where empno=" + empno;
        boolean flag = false;
        try (Connection con = getConnection()){
            Statement st = con.createStatement();
            if (st.executeUpdate(sql) > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}

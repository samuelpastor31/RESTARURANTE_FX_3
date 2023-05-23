package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.entidades.Order;
import es.progcipfpbatoi.modelo.entidades.producttypes.Product;
import es.progcipfpbatoi.modelo.entidades.producttypes.Status;
import es.progcipfpbatoi.modelo.entidades.producttypes.types.Sandwich;
import es.progcipfpbatoi.services.MySqlConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SQLOrderDAO implements OrderDao {

    private static final String TABLE_NAME = "pedidos";

    private Connection connection;
    @Override
    public void add(Order order) {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public ArrayList<Order> findAll() throws DatabaseErrorException {
        String sql = String.format("SELECT * FROM %s", TABLE_NAME);

        ArrayList<Order>orders = new ArrayList<>();
        connection =  new MySqlConnection("192.168.56.101", "Restaurante", "batoi", "1234").getConnection();

        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {

            while(resultSet.next()) {
                Order product = getTaskFromResultset(resultSet);
                orders.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en la conexión o acceso a la base de datos (select)");
        }

        return orders;
    }

    private Order getTaskFromResultset(ResultSet rs) throws SQLException {
        String id = String.valueOf(rs.getInt("id"));
        String name = rs.getString("nombre_cliente");
        Date surname = rs.getDate("creado_en");
        Status estado = Status.valueOf(rs.getString("descuento"));
        int producto = rs.getInt("producto");
        return new Order(id,name){
        };
    }


    @Override
    public Order getByCod(String code) throws NotFoundException {
        return null;
    }

    @Override
    public List<Order> getByDate(LocalDateTime date) throws NotFoundException {
        return null;
    }
}

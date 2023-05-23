package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.entidades.producttypes.Product;
import es.progcipfpbatoi.modelo.entidades.producttypes.Status;
import es.progcipfpbatoi.modelo.entidades.producttypes.types.Sandwich;
import es.progcipfpbatoi.services.MySqlConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;

public class SQLProductoDAO implements ProductosDAO{

    private Connection connection;
    private static final String TABLE_NAME = "productos";

    @Override
    public ArrayList<Product> findAll(Class<? extends Product> productClass) throws DatabaseErrorException {
        return null;
    }

    @Override
    public ArrayList<Product> findAll() throws DatabaseErrorException {
        String sql = String.format("SELECT * FROM %s", TABLE_NAME);

        ArrayList<Product>products = new ArrayList<>();
        connection =  new MySqlConnection("192.168.56.101", "Restaurante", "batoi", "1234").getConnection();

        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {

            while(resultSet.next()) {
                Product product = getTaskFromResultset(resultSet);
                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en la conexión o acceso a la base de datos (select)");
        }

        return products;
    }

    @Override
    public ArrayList<Product> findAllAvailable() throws DatabaseErrorException {
        String sql = String.format("SELECT * FROM %s", TABLE_NAME);

        ArrayList<Product>products = new ArrayList<>();
        connection =  new MySqlConnection("192.168.56.101", "Restaurante", "batoi", "1234").getConnection();

        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {

            while(resultSet.next()) {
                Product user = getTaskFromResultset(resultSet);
                if (user.isDadoAlta()==true) {
                    products.add(user);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en la conexión o acceso a la base de datos (select)");
        }

        return products;
    }

    @Override
    public Product getByCod(int id) throws NotFoundException, DatabaseErrorException {
        String sql = String.format("SELECT * FROM %s WHERE id = ?",TABLE_NAME);
        connection =  new MySqlConnection("192.168.56.101", "Restaurante", "batoi", "1234").getConnection();

        try (
                PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()) {
                Product product = getTaskFromResultset(resultSet);
                if (Objects.equals(product.getCod(), String.valueOf(id))) {
                    return product;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (select)");
        }
        return null;
    }

    @Override
    public Product findByCod(int cod) throws DatabaseErrorException {
        try {
            return getByCod(cod);
        } catch (NotFoundException ex) {
            return null;
        }
    }

    @Override
    public void save(Product product) throws DatabaseErrorException {
        if (findByCod(Integer.parseInt(product.getCod()))==null){
            insert(product);
        } else {
           update(product);
        }
    }

    private Product insert(Product product) throws DatabaseErrorException {
        String sql = "INSERT INTO "+ TABLE_NAME + "(id,nombre,precio_base,descuento,iva,id_categoria,estado) VALUES (?,?,?,?,?,?,?);";
        connection =  new MySqlConnection("192.168.56.101", "Restaurante", "batoi", "1234").getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, product.getCod());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setFloat(3, product.getPrize());
            preparedStatement.setFloat(4, product.getDiscount());
            preparedStatement.setFloat(5, product.getVat());
            preparedStatement.setInt(6, product.getTipoBD());
            preparedStatement.setString(7, product.isDadoAlta()?"ALTA":"BAJA");

            preparedStatement.executeUpdate();

            return product;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (insert)");
        }
    }

    private Product update(Product product) throws DatabaseErrorException{
        String sql = String.format("UPDATE %s SET nombre = ?, precio_base = ?, descuento = ?, iva = ?, estado = ?, id_categoria = ? WHERE id = ?",
                TABLE_NAME);
        connection =  new MySqlConnection("192.168.56.101", "Restaurante", "batoi", "1234").getConnection();
        try (

                PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setFloat(2, product.getPrize());
            preparedStatement.setFloat(3, product.getDiscount());
            preparedStatement.setFloat(4, product.getVat());
            preparedStatement.setString(5, product.isDadoAlta()?"ALTA":"BAJA");
            preparedStatement.setInt(6, product.getTipoBD());
            preparedStatement.setString(7, product.getCod());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (update)");
        }

        return product;
    }

    @Override
    public boolean remove(int id) throws DatabaseErrorException, NotFoundException {
        String sql = String.format("DELETE FROM %s WHERE id = ?", TABLE_NAME);
        connection =  new MySqlConnection("192.168.56.101", "Restaurante", "batoi", "1234").getConnection();
        try (
                PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ha ocurrido un error en el acceso o conexión a la base de datos (delete)");
        }
        return true;
    }

    @Override
    public boolean isNameExists(String productName) {
        return false;
    }

    private Product getTaskFromResultset(ResultSet rs) throws SQLException {
        String id = String.valueOf(rs.getInt("id"));
        String name = rs.getString("nombre");
        Float surname = rs.getFloat("precio_base");
        Float descuento = rs.getFloat("descuento");
        Float iva = rs.getFloat("iva");
        boolean estado;
        if (rs.getString("estado").equals("ALTA")){
            estado = true;
        }else{
            estado = false;
        }
        return new Sandwich(id,name,surname,descuento,iva,estado) {
        };
    }

}

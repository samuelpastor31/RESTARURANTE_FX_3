package es.progcipfpbatoi.modelo.dao;

import es.progcipfpbatoi.exceptions.DatabaseErrorException;
import es.progcipfpbatoi.exceptions.NotFoundException;
import es.progcipfpbatoi.modelo.entidades.producttypes.Product;
import es.progcipfpbatoi.modelo.entidades.producttypes.types.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileProductoDAO implements ProductosDAO{

    private static final String DATABASE_FILE = "src/main/resources/database/productos.txt";
    private static final int ID = 0;
    private static final int TIPO_PRODUCTO = 1;
    private static final int DESCRIPCION = 2;
    private static final int PRECIO = 3;
    private static final int DESCUENTO = 4;
    private static final int IVA = 5;
    private static final int ESTADO = 6;
    private static final int RELLENABLE=7;
    private static final int TAMANYO=8;

    private static final String FIELD_SEPARATOR = ";";

    private File file;

    public FileProductoDAO() {
        this.file = new File(DATABASE_FILE);
    }

    @Override
    public ArrayList<Product> findAll(Class<? extends Product> productClass) throws DatabaseErrorException{
        ArrayList<Product> products = findAll();
        ArrayList<Product> filteredProducts = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getClass() == productClass) {
                filteredProducts.add(products.get(i));
            }
        }
        return filteredProducts;
    }

    @Override
    public ArrayList<Product> findAll() throws DatabaseErrorException{
        ArrayList<Product> productArrayList = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(DATABASE_FILE));

            do {
                String linea = bufferedReader.readLine();
                if (linea == null) {
                    return productArrayList;
                }
                String[] fields = linea.split(";");
                String id = fields[ID];
                String tipo = fields[TIPO_PRODUCTO];
                String descripcion = fields[DESCRIPCION];
                float precio = Float.parseFloat(fields[PRECIO]);
                float descuento = Float.parseFloat(fields[DESCUENTO]);
                float iva = Float.parseFloat(fields[IVA]);
                Boolean estado = Boolean.valueOf(fields[ESTADO]);

                Product product;
                switch (tipo) {
                    case "DRINK":
                        Boolean rellenable = Boolean.parseBoolean(fields[RELLENABLE]);
                        Size tamanyo = Size.valueOf(fields[TAMANYO]);
                        product = new Drink(id, descripcion, precio, descuento, iva, estado, rellenable, tamanyo);
                        break;
                    case "SANDWICH":
                        product = new Sandwich(id, descripcion, precio, descuento, iva, estado);
                        break;
                    case "DESERT":
                        Characteristic characteristic = Characteristic.valueOf(fields[RELLENABLE]);
                        product = new Desert(id, descripcion, precio, descuento, iva, estado, characteristic);
                        break;
                    case "STARTER":
                        product = new Starter(id, descripcion, precio, descuento, iva, estado);
                        break;
                    default:
                        product = null;
                        break;
                }

                productArrayList.add(product);

            } while (true);

        } catch (IOException e) {
            throw new DatabaseErrorException("Error en el acceso al fichero productos.txt");
        }
    }

    @Override
    public ArrayList<Product> findAllAvailable() throws DatabaseErrorException {
        try {
            ArrayList<Product> arrayListProductsAvailables = new ArrayList<>();
            try ( BufferedReader bufferedReader = getReader() ) {
                do {
                    String register = bufferedReader.readLine();
                    if ( register == null ) {
                        return arrayListProductsAvailables;
                    }
                    Product product = getProductFromRegister( register );
                    if ( product.isDadoAlta() ) {
                        arrayListProductsAvailables.add( product );
                    }
                } while ( true );
            }
        } catch ( IOException ex ) {
            System.out.println( ex.getMessage() );
            throw new DatabaseErrorException( "Error en el acceso a la base de datos de tareas" );
        }
    }

    private BufferedReader getReader() throws IOException {
        return new BufferedReader( new FileReader( DATABASE_FILE ) );
    }
    @Override
    public Product findByCod(int id) throws DatabaseErrorException {
        try {
            return getByCod(id);
        } catch (DatabaseErrorException | NotFoundException ex) {
            return null;
        }
    }

    @Override
    public Product getByCod(int id) throws NotFoundException, DatabaseErrorException {
        try (FileReader fileReader = new FileReader(this.file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            do {
                String register = bufferedReader.readLine();
                if (register == null) {
                    throw new NotFoundException("Producto no encontrado");
                } else if (!register.isBlank()) {
                    Product product = getProductFromRegister(register);
                    if (product.getCod().equals(String.valueOf(id))) {
                        return product;
                    }
                }
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new DatabaseErrorException("Ocurrió un error en el acceso a la base de datos");
        }
    }

    @Override
    public void save(Product product) throws DatabaseErrorException{
        try {
            if (findByCod(Integer.parseInt(product.getCod())) == null) {
                append(product);
            } else {
                update(product);
            }
        } catch (IOException ex) {
            throw new DatabaseErrorException(ex.getMessage());
        }
    }

    @Override
    public boolean remove(int id) throws DatabaseErrorException, NotFoundException {
        return false;
    }

    private String getRegisterFromProduct(Product product) {
        List<String> fields = new ArrayList<>();
        fields.add(String.valueOf(product.getCod()));
        fields.add(String.valueOf(product.getTipo()));
        fields.add(String.valueOf(product.getName()));
        fields.add(String.valueOf(product.getPrize()));
        fields.add(String.valueOf(product.getDiscount()));
        fields.add(String.valueOf(product.getVat()));
        fields.add(String.valueOf(product.isDadoAlta()));
        if (product instanceof Drink) {
            Drink drink = (Drink) product;
            fields.add(String.valueOf(drink.isRefillable()));
            fields.add(String.valueOf(drink.getSize()));
        } else if (product instanceof Desert) {
            Desert desert = (Desert) product;
            fields.add(quitarCorchetes(desert));
        }
        return String.join(FIELD_SEPARATOR, fields);
    }

    private String quitarCorchetes(Desert desert){
        String conCorchetes = desert.getCharacteristic().toString();
        String nuevaCadena = conCorchetes.substring(1, conCorchetes.length() - 1);
        return nuevaCadena;

    }
    private void append(Product product) throws IOException {
        try (BufferedWriter bufferedWriter = getWriter(true)) {
            bufferedWriter.write(getRegisterFromProduct(product));
            bufferedWriter.newLine();
        }
    }

    private void update(Product product) throws DatabaseErrorException{
        updateOrRemove(product, true);
    }

    public void remove(Product product) throws DatabaseErrorException{
        updateOrRemove(product, false);
    }

    private void updateOrRemove(Product product, boolean update) throws DatabaseErrorException {
        ArrayList<Product> products = findAll();
        try (BufferedWriter bufferedWriter = getWriter(false)) {
            for (Product product1 : products) {
                if (!product1.containsThisCode(product.getCod())) {
                    bufferedWriter.write(getRegisterFromProduct(product1));
                    bufferedWriter.newLine();
                } else if (update) {
                    bufferedWriter.write(getRegisterFromProduct(product));
                    bufferedWriter.newLine();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new DatabaseErrorException("Error en el acceso a la base de datos de tareas");
        }
    }

    @Override
    public boolean isNameExists(String productName) {
        try {
            for ( Product productItem :
                    findAll() ) {
                if ( productItem.getName().equalsIgnoreCase( productName ) ){
                    return true;
                }
            }
        } catch ( DatabaseErrorException e ) {
            throw new RuntimeException( e );
        }
        return false;
    }

    private BufferedWriter getWriter(boolean append) throws IOException {
        return new BufferedWriter(new FileWriter(file, append));
    }

    private Product getProductFromRegister(String register) {
        String[] fields = register.split(FIELD_SEPARATOR);
        String id = fields[ID];
        String tipo = fields[TIPO_PRODUCTO];
        String descripcion = fields[DESCRIPCION];
        float precio = Float.parseFloat(fields[PRECIO]);
        float descuento = Float.parseFloat(fields[DESCUENTO]);
        float iva = Float.parseFloat(fields[IVA]);
        boolean estado = Boolean.parseBoolean(fields[ESTADO]);

        Product product;
        switch (tipo) {
            case "DRINK":
                 Boolean rellenable = Boolean.parseBoolean(fields[RELLENABLE]);
                Size tamanyo = Size.valueOf(fields[TAMANYO]);
                product = new Drink(id, descripcion, precio, descuento, iva, estado, rellenable, tamanyo);
                break;
            case "SANDWICH":
                product = new Sandwich(id, descripcion, precio, descuento, iva, estado);
                break;
            case "DESERT":
                Characteristic characteristic = Characteristic.valueOf(fields[RELLENABLE]);
                product = new Desert(id, descripcion, precio, descuento, iva, estado, characteristic);
                break;
            case "STARTER":
                product = new Starter(id, descripcion, precio, descuento, iva, estado);
                break;
            default:
                product = null;
                break;
        }

        return product;
    }
}

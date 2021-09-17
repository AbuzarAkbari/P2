import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

public class Main {

    static Connection connection;

    public static void main(String[] args) throws SQLException {

        ReizigerDAOPsql rdao = new ReizigerDAOPsql(getConnection());

        testReizigerDAO(rdao);

        closeConnection();

    }

    private static Connection getConnection(){
        try {
            String url = "jdbc:postgresql://localhost/ovchip";
            Properties props = new Properties();
            props.setProperty("user","postgres");
            props.setProperty("password","VoetBal1223");
            connection = DriverManager.getConnection(url, props);

        } catch(Exception e ){
            System.out.println("connection failed");
        }
        return connection;
    }

    private static void closeConnection(){
        try {
            if (connection != null){
                connection.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**
     * P2. Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");

        rdao.save(sietske);

        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        System.out.println(rdao.findById(77));

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.

        System.out.println(rdao.findById(77));

        sietske.setVoorletters("abuzar");
        sietske.setTussenvoegsel(null);
        sietske.setAchternaam("akbari");
        sietske.setGeboortedatum(java.sql.Date.valueOf("1999-08-01"));
        rdao.update(sietske);

        System.out.println(rdao.findById(77));

        rdao.delete(sietske);


    }



}

import java.sql.*;
import java.util.Scanner;

public class Main {
    static final String URL = "jdbc:mysql://localhost:3306/cinema_db";
    static final String USERNAME = "root";
    static final String PASSWORD = "mouad2004charadi";
    private static Connection cn;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        try {
            cn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        while (true) {
            System.out.println("""
                        1. Ajouter Spectateur 
                        2. Affecher ALL Spectateur
                        0. Exit
                    """);
            System.out.print("Enter choix : ");
            int chois = input.nextInt();

            switch (chois) {
                case 1: ajouteSpectateur(input);
                 break;
                case 2: afficherAllspectateur();
                break;
                case 0: System.exit(0);
                break;
                default: System.out.println("chois invaled ");
            }


        }


    }


    // Mouad---------------------------

    public static void ajouteSpectateur(Scanner input) {
        System.out.println("==== Ajouter Spectateur =====");
        System.out.print("Enter name : ");
        String name = input.next();

        System.out.print("Enter email : ");
        String email = input.next();

        Spectateur s = new Spectateur(name,email);

        if(s.sauvegarder(cn)) {
            System.out.println("Accsecc 😊");
        }else {
            System.out.println(" 😒 ");
        }

    }
    public static void afficherAllspectateur(){
        String sql = "SELECT * FROM spectateurs";
        try(Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql)) {

            System.out.println("==== Affecher Tout Spectateur ====");
            boolean is = false;
            while(rs.next()) {
                is = true;
                Spectateur s = new Spectateur(rs);
                System.out.println(s);
            }
        }catch (SQLException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }
    //----------------------------------------------

}
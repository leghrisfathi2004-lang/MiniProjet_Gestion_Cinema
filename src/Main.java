
import javax.swing.plaf.PanelUI;
import java.sql.*;
import java.util.Scanner;

public class Main {
    static final String URL = "jdbc:mysql://localhost:3306/cinema_db";
    static final String USERNAME = "root";
    static final String PASSWORD = "mouad2004charadi";

    public static int menu(){
        Scanner input = new Scanner(System.in);
        System.out.println("|_________| cinema |_________|" +
                "| 1 - admine mode            |" +
                "| 2 - spectateur mode        |" +
                "| 0 - quitte                 |" +
                "|____________________________|");
        System.out.println("...->");
        int choi = input.nextInt();
        return choi;
    }

    public static int menuAdmine(){
        Scanner input = new Scanner(System.in);
        System.out.println("|_________| admine |_________|" +
                "| 1 - ajoute film           |" +
                "| 2 - ajoute seance         |" +
                "| 3 - affiche spectateur    |" +
                "| 0 - quitte                |" +
                "|___________________________|");
        System.out.println("...->");
        int choi = input.nextInt();
        return choi;
    }

    public static Film ajouteFilm(){
        Scanner input = new Scanner(System.in);
        System.out.println("+________ ajoute film ________+");
        System.out.println("entre titre: ");
        String titre = input.nextLine();
        System.out.println("entre dure(min): ");
        int dure = input.nextInt();
        System.out.println("entre categorie: ");
        String categore = input.nextLine();
        return new Film(titre, dure, categore);
    }

    public static int findFilm(Connection cnnx,int i){
        int j = -1;
        try(PreparedStatement ps = cnnx.prepareStatement("SELECT * FROM films where id = ?")){
            ps.setInt(1,i);
            ResultSet rs = ps.executeQuery();
            j =rs.getInt("id");
        }catch (SQLException e){
            System.out.println("Database error: " + e.getMessage());
        }
        if (j == -1) {
            System.out.println("film Id n exist pas!!");
            return j;
        }
        System.out.println("Film ID : " + j);
        return j;
    }

    public static Seance ajouteSeance(){
        Scanner input = new Scanner(System.in);
        System.out.println("+_______ ajoute seance _______+");
        System.out.println("entre horaire(an/mm/jj  h:m): ");
        String horaire = input.nextLine();
        System.out.println("entre capacite: ");
        int capacite = input.nextInt();
        return new Seance( capacite, horaire);
    }

    public static boolean findSeance(Connection cnnx,int i){
        int j = -1;
        try(PreparedStatement ps = cnnx.prepareStatement("SELECT * FROM seances where id = ?")){
            ps.setInt(1,i);
            ResultSet rs = ps.executeQuery();
            j =rs.getInt("id");
        }catch (SQLException e){
            System.out.println("Database error: " + e.getMessage());
        }
        if (j == -1) {
            System.out.println("seance Id n exist pas!!");
            return false;
        }
        i = j;
        System.out.println("Seance ID : " + j);
        return true;
    }

    public static int ajouteSpectateur(Connection cn) {
        Scanner input = new Scanner(System.in);
        System.out.println("+________ ajoute spectateur ________+");
        System.out.print("Enter name : ");
        String name = input.nextLine();

        System.out.print("Enter email : ");
        String email = input.nextLine();

        Spectateur s = new Spectateur(name,email);

        if(s.sauvegarder(cn)) {
            System.out.println("Accsecc 😊");
            return s.getId();
        }else {
            System.out.println(" 😒 ");
            return 0;
        }
    }

    public static void afficherAllspectateur(Connection cn){
        String sql = "SELECT * FROM spectateurs";
        try(Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql)) {

            System.out.println("affichage tous spectateur:");
            boolean is = false;
            while(rs.next()) {
                is = true;
                Spectateur s = new Spectateur(rs);
                System.out.println(s.toString());
            }
        }catch (SQLException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }

    public static void assigneSpect(Connection cnnx){
        Scanner input = new Scanner(System.in);
        System.out.println("____ les Seances disponible ____");
        Seance.affiche(cnnx);
        System.out.println("entre Id de seance: ");
        int choi = input.nextInt();
        if (findSeance(cnnx,choi)) {
            int id_spct = ajouteSpectateur(cnnx);
            Ticket ticket = new Ticket(id_spct, choi);
            return;
        }
        System.out.println("invalid! try again");
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int choi;
        int choiA;
        try(Connection cnnx = DriverManager.getConnection(URL, USERNAME, PASSWORD);) {
            do {
                choi = menu();
                switch (choi){
                    case 1:
                        choiA = menuAdmine();
                        switch (choiA){
                            case 0:
                                choi = 0;
                                break;
                            case 1:
                                Film film = ajouteFilm();
                                if (film != null)
                                    film.ajouterFilm(cnnx);
                                else System.out.println("invalid! try again later");
                                break;
                            case 2:
                                Seance seance = ajouteSeance();
                                Film.afficherFilms(cnnx);
                                System.out.println("entre film ID: ");
                                int i = input.nextInt();
                                i = findFilm(cnnx,i);
                                if (i > -1)
                                    seance.applyfilm(cnnx,i);
                                else System.out.println("invalid! try again later");
                                break;
                            case 3:
                                afficherAllspectateur(cnnx);
                                break;
                            default:
                                System.out.println("invalid! try again later");
                                break;
                        }
                        break;
                    case 2:
                        assigneSpect(cnnx);
                        break;
                    default:
                        System.out.println("invalid! try again later");
                        break;
                }
            }while (choi !=0 );
        } catch (SQLException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }
}
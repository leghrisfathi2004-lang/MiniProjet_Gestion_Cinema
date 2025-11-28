
import java.sql.*;
import java.util.Scanner;

public class Seance {

    private int id;
    private int capacite;
    private String horaire;

    public Seance(int capacite, String horaire){
        this.id = id;
        this.capacite = capacite;
        this.horaire=horaire;
    }

    public void applyfilm( Connection cnnx, int film_id) {
        try(PreparedStatement stmt = cnnx.prepareStatement("INSERT INTO seances (capacite, horaire) VALUES (? ,? )")) {
            stmt.setInt(1,capacite);
            stmt.setString(2,horaire);
            stmt.setInt(3,film_id);
            int resaultat = stmt.executeUpdate();
            System.out.println("Rows inserted: " + resaultat);
        } catch (SQLException e) {
            System.out.println("DataBase error: "+e.getMessage());
        }
    }

    public int applyspect(Connection cnnx) {
        Scanner input = new Scanner(System.in);
        System.out.println("entre id de seance: ");
        int i = input.nextInt();
        boolean flag = false;
        try( PreparedStatement stmt = cnnx.prepareStatement("Update senaces SET capacite = capacite-1 WHERE id = ? AND capacite>0") ) {
            stmt.setInt(1,i);
            stmt.setInt(2,i);
            stmt.setInt(3,i);
            if (stmt.executeUpdate() > 0)
                flag = true;
        }catch (SQLException e) {
            System.out.println("DataBase error: " + e.getMessage());
        }
        if (flag) {
            System.out.println("place register!");
            return i;
        }
        System.out.println("invalid!");
        return 0;
    }

    public static void affiche(Connection cnnx) {
        try(PreparedStatement stmt = cnnx.prepareStatement("SELECT * from seances")){
            ResultSet r = stmt.executeQuery();
            while (r.next())
                System.out.println("- seancr id: " + r.getInt("id") + " | seance film id: " + r.getInt("film_id") +
                        " | horaire: "+r.getInt("horaire")+" | capacite available: "+r.getInt("capacite"));
    }catch (SQLException e){
            System.out.println("DataBase error: "+e.getMessage());
        }
    }

    public void affiche(Connection cnnx, int i){
        try(PreparedStatement stmt = cnnx.prepareStatement("SELECT * from seances WHERE id = ?")){
            stmt.setInt(1,i);
            ResultSet r = stmt.executeQuery();
            System.out.println("- seancr id: " + r.getInt("id") + " | seance film id: " + r.getInt("film_id") +
                    " | horaire: "+r.getInt("horaire")+" | capacite available: "+r.getInt("capacite"));
        }catch (SQLException e){
            System.out.println("DataBase error: "+e.getMessage());
        }
    }

}
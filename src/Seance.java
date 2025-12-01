
import java.sql.*;
import java.util.Scanner;

public class Seance {

    private int id;
    private int capacite;
    private String horaire;

    public Seance(int capacite, String horaire){
        this.capacite = capacite;
        this.horaire=horaire;
    }

    public int getId(Connection c) {
        try(PreparedStatement stmt = c.prepareStatement("SELECT LAST_INSERT_ID() from seances")){
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                this.id = rs.getInt(1);
        }catch (SQLException e) {
            System.out.println("DataBase error: "+e.getMessage());
        }
        return id;
    }

    public void applyfilm(Connection cnnx, int film_id) {
        try(PreparedStatement stmt1 = cnnx.prepareStatement("INSERT INTO seances (capacite, horaire) VALUES (? ,? )",Statement.RETURN_GENERATED_KEYS)) {
            stmt1.setInt(1,capacite);
            stmt1.setString(2,horaire);
            int r1 = stmt1.executeUpdate();
            ResultSet rs = stmt1.getGeneratedKeys();
            if (rs.next())
                this.id = rs.getInt(1);
            System.out.println("new id:"+id);
            stuck(cnnx,film_id);
            System.out.println("Rows inserted: " + r1);
        } catch (SQLException e) {
            System.out.println("DataBase error: "+e.getMessage());
        }
    }

    public void stuck(Connection c,int film_id){
        try(PreparedStatement stmt = c.prepareStatement("INSERT INTO films_seances (films_id,seances_id) VALUES (?,?)")){
            stmt.setInt(1,film_id);
            stmt.setInt(2,id);
            int rs = stmt.executeUpdate();
            System.out.println("rows inserted dans seances et film: "+rs);
        }catch (SQLException e) {
            System.out.println("DataBase error: "+e.getMessage());
        }

    }

    public static int applyspect(Connection cnnx) {
        Scanner input = new Scanner(System.in);
        System.out.println("entre id de seance: ");
        int i = input.nextInt();
        boolean flag = false;
        try( PreparedStatement stmt = cnnx.prepareStatement("Update seances SET capacite = capacite-1 WHERE id = ? AND capacite>0") ) {
            stmt.setInt(1,i);
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
                System.out.println("- seance id: " + r.getInt("id") +
                        " | horaire: "+r.getString("horaire")+" | capacite available: "+r.getInt("capacite"));
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
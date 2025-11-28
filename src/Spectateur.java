import java.sql.*;

public class Spectateur {
    private int id;
    private String nom;
    private String email;

    public Spectateur(String nom, String email) {
        this.nom = nom;
        this.email = email;
    }

    public Spectateur(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.nom = rs.getString("name");
        this.email = rs.getString("email");
    }


    public boolean sauvegarder(Connection con) {
        if(findEmail(con, this.email)) {
            System.out.println("Email est deja existe 🤨");
            return false;
        }
        if(findNom(con, this.nom)) {
            System.out.println("Name est deja existe 🤨");
            return false;
        }
        try {
                String sql = "INSERT INTO spectateurs (name , email)" +
                        "VALUES (?,?) ";
               try( PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                   pst.setString(1, this.nom);
                   pst.setString(2, this.email);

                   int rs = pst.executeUpdate();

                   if (rs > 0) {
                       ResultSet generatKey = pst.getGeneratedKeys();
                       if(generatKey.next()){
                           this.id = generatKey.getInt(1);
                       }
                       return true;
                   }
               }


        } catch (SQLException e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        return false;
    }
    public boolean findEmail(Connection con, String email) {

        String sql = "SELECT * FROM spectateurs  WHERE email = ?";

        try(PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();

            if(rs.next()){
                return true;
            }

        }catch (SQLException e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        return false;
    }
    public boolean findNom(Connection con, String nom) {

        String sql = "SELECT * FROM spectateurs  WHERE name = ?";

        try(PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, nom);
            ResultSet rs = pst.executeQuery();

            if(rs.next()) return true;

        }catch (SQLException e) {
            System.err.println("ERROR: " + e.getMessage());
        }


        return false;
    }




    public int getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "- Spectateur id: " + id + " | nom: " + nom +" | email: " + email ;
    }
}


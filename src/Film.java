import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Film {
    private int id;
    private String titre;
    private int duree;
    private String categorie;

    public Film(String titre, int duree, String categorie) {
        this.titre = titre;
        this.duree = duree;
        this.categorie = categorie;
    }

    public int getId() {
        return id;
    }

    public String getCategorie() {
        return categorie;
    }

    public int getDuree() {
        return duree;
    }

    public String getTitre() {
        return titre;
    }

    public void ajouterFilm(Connection con) {
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO films (titre, duree, categorie) VALUES (?, ?, ?)");

            ps.setString(1, titre);
            ps.setInt(2, duree);
            ps.setString(3, categorie);
            ps.executeUpdate();
            System.out.println("Film ajouté avec succès !");
        } catch (Exception e) {
            System.out.println("Erreur about film : " + e.getMessage());
        }
    }

    public static void afficherFilms(Connection con) {
        try(PreparedStatement ps = con.prepareStatement("SELECT * FROM films"))   {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println("Film ID : " + rs.getInt("id")+" | titre: "+ rs.getString("titre")+
                        " | categorie: "+ rs.getString("categorie")+
                        " | dure: "+ rs.getInt("duree"));
            }
        } catch (Exception e) {
            System.out.println("Erreur affichage : " + e.getMessage());
        }
    }


}


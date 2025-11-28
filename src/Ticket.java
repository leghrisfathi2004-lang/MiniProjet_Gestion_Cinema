import java.sql.Connection;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Ticket{
    private int numero;
    private double prix;
    private int spectateur_id;
    private int seance_id;

    public Ticket(int spectateur_id,int seance_id){
        this.numero = genererNumero();
        this.prix = 30;
        this.spectateur_id = spectateur_id;
        this.seance_id = seance_id;
    }

    private int genererNumero()
    {
        return (int)(Math.random()*1000);
    }

    public void effectuerPaiement()
    {
        System.out.println("Paiement effectuer pour le ticket " + this.numero);
    }

    public void ajouteTicket(Connection con)
    {
        String sql = "INSERT INTO tickets " +
                     "VALUES (?,?,?,?)";
        try(PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1,this.numero);
            pst.setDouble(2,this.prix);
            pst.setInt(3,this.spectateur_id);
            pst.setInt(4,this.seance_id);

            int rs = pst.executeUpdate();

            if(rs > 0) {
                System.out.println("👌");
            }else {
                System.out.println("😒");
            }


        }catch (SQLException e) {
            System.err.println("ERROR: " + e.getMessage());
        }
   }


}

package banking;

import java.sql.Connection;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

public class bankManagement {

    private static final int NULL = 0;

    static Connection con = connection.getConnection();
    static String sql = "";

    /**
     * Fonction pour créer un compte
     * @param name nom du client
     * @param passCode code de compte
     * @return Vrai ou fausse
     */
    public static boolean createAccount(String name, int passCode){

        try {

//            Validation des informations
            if (name.isEmpty() || passCode == NULL){
                System.out.println("Tous les champs sont obligatoires !");
                return false;
            }
            // Requête pour inserer des informations
            Statement stm = con.createStatement();
            sql = "INSERT INTO customer(cname, balance, pass_code) VALUES('" + name + "', 1000" + passCode + ")";

            // Exécuté la requête
            if (stm.executeUpdate(sql)==1){
                System.out.println(name + " Connectez-vous maintenant");
                return true;
            }
            // Retourne
        }
        catch (SQLIntegrityConstraintViolationException e){
            System.out.println("Nom d'utilisateur non disponible");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        
    //Retourne
        return false;
    }

}

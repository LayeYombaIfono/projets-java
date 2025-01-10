package banking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class bankManagement {

    private static final int NULL = 0;
    static Connection con = connection.getConnection();
    static String sql = "";

    /**
     * Méthode pour créer un compte
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

    /**
     * Méthode pour la connexion au compte
     * @param name Nom du client
     * @param passCode code d'accès
     * @return Vrai ou false
     */
    public static boolean loginAccount(String name, int passCode){

        try{
            // Vérification et validation des informations
            if (name.isEmpty() || passCode == NULL){
                System.out.println("Tous les champs sont obligatoires !");
                return false;
            }
            // Requête
            sql = "select * from customer where cname='" + name + "' et code d'accès=" + passCode;
            PreparedStatement stm = con.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();

            // Exécution
            BufferedReader sc = new BufferedReader(
                    new InputStreamReader(System.in)
            );

            if (rs.next()){
                // Après la connexion, méthode d'interface pilotée par menu
                int ch = 5;
                int amt = 0;
                int senderAc = rs.getInt("ac_no");

                int receiveAc;

                while (true){
                 try{
                     System.out.println("Bonjour, " + rs.getString("cname"));

                     System.out.println("1)Transfert d'argent");
                     System.out.println("2)Voir le solde");
                     System.out.println("3)Déconnexion");

                     System.out.println("Entrer votre choix :");

                     ch = Integer.parseInt(sc.readLine());

                     if (ch == 1){
                         System.out.println("Entrez le numéro de compte de récepteur : ");
                         receiveAc = Integer.parseInt(sc.readLine());

                         System.out.println("Entrez le montant: ");
                         amt = Integer.parseInt(sc.readLine());

                         if (bankManagement.transfertMoney(senderAc, receiveAc, amt)){
                             System.out.println("MSG : Argent envoyé avec succès !\n");
                         }else{
                             System.out.println("ERR : Echec !\n");
                         }
                     }
                     else if (ch == 2){
                         bankManagement.getBalance(senderAc);
                     } else if (ch == 5) {
                         break;
                     }else {
                         System.out.println("Err : Entrez une entrée valide !\n" );
                     }
                 }
                 catch (Exception e){
                     e.printStackTrace();
                 }
                }
            }
            else {
                return false;
            }
            // Retourne
            return true;

        }
        catch (SQLIntegrityConstraintViolationException e){
            System.out.println("Nom d'utilisateur non disponible !");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    /**
     * Méthode de consultation du solde
     * @param acNo le solde
     */
    public static void getBalance(int acNo) {

        try {
            // Requête

            sql = "select * from customer where ac_no " + acNo;
            PreparedStatement stm = con.prepareStatement(sql);

            ResultSet rs = stm.executeQuery(sql);

            System.out.println("" +
                    "-------------------------------------------");
            System.out.printf("%12s %10s %10s\n", "Numéro de compte", "Nom", "Solde");

            // Exécution
            while (rs.next()){
                System.out.printf("%12d %10s %10d.00\n",
                        rs.getInt("ac_no"),
                        rs.getString("cname"),
                        rs.getInt("balance")

                );
            }

            System.out.println("------------------------------------------------------\n");

        }catch (Exception e ){
            e.printStackTrace();
        }
    }


    /**
     * Méthode de transfert d'argent
     * @param sender_ac Envoyer
     * @param reveiver_ac Recevoir
     * @param amount Montant
     * @return Vrai ou faux
     * @throws SQLException Gestion des erreurs
     */
    public static boolean transfertMoney(int sender_ac, int reveiver_ac, int amount) throws SQLException {

        //Validation
        if (reveiver_ac == NULL || amount == NULL){
            System.out.println("Tous les champs sont obligatoires !");
            return false;
        }

        try{
            con.setAutoCommit(false);
            sql = "select * from customer where ac_no=" + sender_ac;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                if (rs.getInt("balance")< amount){
                    System.out.println("Le solde est insufficient !");
                    return false;
                }
            }

            Statement st = con.createStatement();

            //Débit
            con.setSavepoint();

            sql = "update customer set balance=balance-" + amount + " where ac_no" + sender_ac;
            if (st.executeUpdate(sql) == 1){
                System.out.println(" Montant débité ! ");
            }

            // Crédit
            sql = "update customer set balance=balance+" + amount + " where ac_no=" + reveiver_ac;
            st.executeUpdate(sql);

            con.commit();
            return true;


        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

}


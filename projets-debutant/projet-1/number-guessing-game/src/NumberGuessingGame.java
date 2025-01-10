import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class NumberGuessingGame {

// Fonction qui implémente le
// jeu de devinettes de nombres
    public static void guessingNumberGame(){

//        Classe Scanner
        Scanner sc = new Scanner(System.in);

//        Générer les nombres aléatoires
        int number = 1 + (int)(100 * Math.random());

        // Étant donné K essais
        int k = 5;

        int i, guess;

        System.out.println("Un nombre est choisi "
                + "entre 1 et 100. "
                + "Devinez le nombre"
                + " en 5 essais.");

//        Itérer sur K essais
        for ( i = 0; i < k; i++) {
            System.out.println("Déviner le nombre:");

//          Prendre des informations pour deviner
            guess = sc.nextInt();

//            Si le nombre est deviner
            if (number == guess){
                System.out.println("Félicitation vous avez deviner le nombre.");
                break;
            }else if (number > guess && i != k -1){
                System.out.println("Le nombre est supérieur" +
                        " à " + guess);
            } else if (number < guess && i != k -1) {
                System.out.println("Le nombre est inférieur" +
                        " à " + guess);
            }
        }

        if (i == k){
            System.out.println("Vous avez épuisé"
                    + " K essais.");

            System.out.println("Le nombre était " + number);
        }

    }


    public static void main(String[] args) {

//        Appelle de la fonction
        guessingNumberGame();
    }
}
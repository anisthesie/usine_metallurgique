package usine.geometrie;

public class Geometrie {

    public static int cartesienVersLineaire(int x, int y, int tailleX) {
        return y * tailleX + x;
    }

    public static int[] lineaireVersCartesien(int indexLineaire, int tailleX) {
        return new int[]{indexLineaire % tailleX, indexLineaire / tailleX};
    }


}

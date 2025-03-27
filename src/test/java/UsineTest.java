import org.junit.jupiter.api.Test;
import usine.IdentiteProduit;
import usine.PlacementIncorrectException;
import usine.TapisRoulant;
import usine.Usine;
import usine.stations.Mine;
import usine.stations.Vendeur;
import usine.stations.machines.*;

import static org.junit.jupiter.api.Assertions.*;


class UsineTest {

    @Test
    void testMinePlacer() {
        Usine usine = new Usine(3, 4);
        Mine mine = new Mine(IdentiteProduit.ACANTHITE, 1, 1);

        assertDoesNotThrow(() -> mine.placer(1, 1, usine));
        assertEquals(mine, usine.getCase(1, 1).getStation());
    }

    @Test
    void testMinePlacer2() {
        Usine usine = new Usine(3, 4);
        Mine mine = new Mine(IdentiteProduit.ACANTHITE, 1, 1);

        assertDoesNotThrow(() -> mine.placer(1, 1, usine));
        assertEquals(mine, usine.getCase(1, 1).getStation());
        assertEquals(TapisRoulant.OCCUPE, usine.getLogistique().getTapis(1, 1));
    }

    @Test
    void testMinePlacer3() {
        Usine usine = new Usine(3, 4);
        usine.getLogistique().setTapis(1, 1, TapisRoulant.DROITE_BAS);
        Mine mine = new Mine(IdentiteProduit.ACANTHITE, 1, 1);

        assertThrows(PlacementIncorrectException.class, () -> mine.placer(1, 1, usine));
        assertNull(usine.getCase(1, 1).getStation());
        assertEquals(TapisRoulant.DROITE_BAS, usine.getLogistique().getTapis(1, 1));
    }

    @Test
    void testMineAcanthiteTic() {
        Usine usine = new Usine(3, 4);
        Mine mine = new Mine(IdentiteProduit.ACANTHITE, 1, 1);
        mine.placer(1, 1, usine);
        usine.getLogistique().setTapis(2, 1, TapisRoulant.GAUCHE_DROITE);

        mine.tic(usine);
        assertNull(usine.trouverItem(2, 1));
        mine.tic(usine);
        assertEquals(IdentiteProduit.ACANTHITE, usine.trouverItem(2, 1).getIdentite());
    }

    @Test
    void testMineCassiteriteTic() {
        Usine usine = new Usine(3, 4);
        Mine mine = new Mine(IdentiteProduit.CASSITERITE, 1, 2);
        mine.placer(1, 2, usine);
        usine.getLogistique().setTapis(2, 2, TapisRoulant.GAUCHE_DROITE);

        for (int i = 0; i < 6; ++i) {
            mine.tic(usine);
            assertNull(usine.trouverItem(2, 2));
        }
        mine.tic(usine);
        assertEquals(IdentiteProduit.CASSITERITE, usine.trouverItem(2, 2).getIdentite());
    }

    @Test
    void testMineChalcociteTic() {
        Usine usine = new Usine(3, 4);
        Mine mine = new Mine(IdentiteProduit.CHALCOCITE, 1, 2);
        mine.placer(1, 2, usine);
        usine.getLogistique().setTapis(2, 2, TapisRoulant.GAUCHE_DROITE);

        for (int i = 0; i < 2; ++i) {
            mine.tic(usine);
            assertNull(usine.trouverItem(2, 2));
        }
        mine.tic(usine);
        assertEquals(IdentiteProduit.CHALCOCITE, usine.trouverItem(2, 2).getIdentite());
    }

    @Test
    void testMineCharbonTic() {
        Usine usine = new Usine(3, 4);
        Mine mine = new Mine(IdentiteProduit.CHARBON, 1, 2);
        mine.placer(1, 2, usine);
        usine.getLogistique().setTapis(2, 2, TapisRoulant.GAUCHE_DROITE);

        mine.tic(usine);
        assertEquals(IdentiteProduit.CHARBON, usine.trouverItem(2, 2).getIdentite());
    }

    @Test
    void testMoulinPlacer1() {
        Usine usine = new Usine(4, 4);
        Moulin moulin = new Moulin(1, 1);

        assertDoesNotThrow(() -> moulin.placer(1, 1, usine));
        assertEquals(moulin, usine.getCase(1, 1).getStation());
        assertEquals(TapisRoulant.OCCUPE, usine.getLogistique().getTapis(1, 1));
        assertEquals(TapisRoulant.OCCUPE, usine.getLogistique().getTapis(2, 1));
    }

    @Test
    void testMoulinPlacer2() {
        Usine usine = new Usine(3, 4);
        usine.getLogistique().setTapis(1, 1, TapisRoulant.DROITE_BAS);
        Moulin moulin = new Moulin(1, 1);

        assertThrows(PlacementIncorrectException.class, () -> moulin.placer(1, 1, usine));
        assertNull(usine.getCase(1, 1).getStation());
        assertNull(usine.getCase(1, 2).getStation());
        assertEquals(TapisRoulant.DROITE_BAS, usine.getLogistique().getTapis(1, 1));
        assertEquals(TapisRoulant.VIDE, usine.getLogistique().getTapis(2, 1));
    }

    @Test
    void testMoulinPlacer3() {
        Usine usine = new Usine(3, 4);
        usine.getLogistique().setTapis(2, 1, TapisRoulant.DROITE_BAS);
        Moulin moulin = new Moulin(1, 1);

        assertThrows(PlacementIncorrectException.class, () -> moulin.placer(1, 1, usine));
        assertNull(usine.getCase(1, 1).getStation());
        assertNull(usine.getCase(1, 2).getStation());
        assertEquals(TapisRoulant.VIDE, usine.getLogistique().getTapis(1, 1));
        assertEquals(TapisRoulant.DROITE_BAS, usine.getLogistique().getTapis(2, 1));
    }

    @Test
    void testFournaisePlacer1() {
        Usine usine = new Usine(6, 6);
        Fournaise fournaise = new Fournaise(2, 2);

        assertDoesNotThrow(() -> fournaise.placer(2, 2, usine));
        assertEquals(fournaise, usine.getCase(2, 2).getStation());
        assertEquals(TapisRoulant.OCCUPE, usine.getLogistique().getTapis(2, 2));
        assertEquals(TapisRoulant.OCCUPE, usine.getLogistique().getTapis(2, 1));
        assertEquals(TapisRoulant.OCCUPE, usine.getLogistique().getTapis(2, 3));
        assertEquals(TapisRoulant.OCCUPE, usine.getLogistique().getTapis(3, 2));
    }

    @Test
    void testFournaisePlacer2() {
        Usine usine = new Usine(6, 6);
        usine.getLogistique().setTapis(2, 2, TapisRoulant.DROITE_GAUCHE);
        Fournaise fournaise = new Fournaise(2, 2);

        assertThrows(PlacementIncorrectException.class, () -> fournaise.placer(2, 2, usine));
        assertNull(usine.getCase(2, 2).getStation());
        assertNull(usine.getCase(1, 2).getStation());
        assertNull(usine.getCase(3, 2).getStation());
        assertNull(usine.getCase(2, 3).getStation());
        assertEquals(TapisRoulant.DROITE_GAUCHE, usine.getLogistique().getTapis(2, 2));
        assertEquals(TapisRoulant.VIDE, usine.getLogistique().getTapis(2, 3));
        assertEquals(TapisRoulant.VIDE, usine.getLogistique().getTapis(2, 1));
        assertEquals(TapisRoulant.VIDE, usine.getLogistique().getTapis(3, 2));
    }

    @Test
    void testFournaisePlacer3() {
        Usine usine = new Usine(6, 6);
        usine.getLogistique().setTapis(2, 1, TapisRoulant.DROITE_GAUCHE);
        Fournaise fournaise = new Fournaise(2, 2);

        assertThrows(PlacementIncorrectException.class, () -> fournaise.placer(2, 2, usine));
        assertNull(usine.getCase(2, 2).getStation());
        assertNull(usine.getCase(1, 2).getStation());
        assertNull(usine.getCase(3, 2).getStation());
        assertNull(usine.getCase(2, 3).getStation());
        assertEquals(TapisRoulant.VIDE, usine.getLogistique().getTapis(2, 2));
        assertEquals(TapisRoulant.VIDE, usine.getLogistique().getTapis(2, 3));
        assertEquals(TapisRoulant.DROITE_GAUCHE, usine.getLogistique().getTapis(2, 1));
        assertEquals(TapisRoulant.VIDE, usine.getLogistique().getTapis(3, 2));
    }

    @Test
    void testFournaisePlacer4() {
        Usine usine = new Usine(6, 6);
        usine.getLogistique().setTapis(2, 3, TapisRoulant.DROITE_GAUCHE);
        Fournaise fournaise = new Fournaise(2, 2);

        assertThrows(PlacementIncorrectException.class, () -> fournaise.placer(2, 2, usine));
        assertNull(usine.getCase(2, 2).getStation());
        assertNull(usine.getCase(1, 2).getStation());
        assertNull(usine.getCase(3, 2).getStation());
        assertNull(usine.getCase(2, 3).getStation());
        assertEquals(TapisRoulant.VIDE, usine.getLogistique().getTapis(2, 2));
        assertEquals(TapisRoulant.DROITE_GAUCHE, usine.getLogistique().getTapis(2, 3));
        assertEquals(TapisRoulant.VIDE, usine.getLogistique().getTapis(2, 1));
        assertEquals(TapisRoulant.VIDE, usine.getLogistique().getTapis(3, 2));
    }

    @Test
    void testFournaisePlacer5() {
        Usine usine = new Usine(6, 6);
        usine.logistique.setTapis(3, 2, TapisRoulant.DROITE_GAUCHE);
        Fournaise fournaise = new Fournaise(2, 2);

        assertThrows(PlacementIncorrectException.class, () -> fournaise.placer(2, 2, usine));
        assertNull(usine.getCase(2, 2).getStation());
        assertNull(usine.getCase(1, 2).getStation());
        assertNull(usine.getCase(3, 2).getStation());
        assertNull(usine.getCase(2, 3).getStation());
        assertEquals(TapisRoulant.VIDE, usine.logistique.getTapis(2, 2));
        assertEquals(TapisRoulant.VIDE, usine.logistique.getTapis(2, 3));
        assertEquals(TapisRoulant.VIDE, usine.logistique.getTapis(2, 1));
        assertEquals(TapisRoulant.DROITE_GAUCHE, usine.logistique.getTapis(3, 2));
    }

    @Test
    void testFournaiseDeGrillagePlacer1() {
        Usine usine = new Usine(6, 6);
        FournaiseDeGrillage fournaise = new FournaiseDeGrillage(2, 3);

        assertDoesNotThrow(() -> fournaise.placer(2, 3, usine));
        assertEquals(fournaise, usine.getCase(2, 3).getStation());
        assertEquals(TapisRoulant.OCCUPE, usine.logistique.getTapis(2, 3));
        assertEquals(TapisRoulant.OCCUPE, usine.logistique.getTapis(1, 3));
        assertEquals(TapisRoulant.OCCUPE, usine.logistique.getTapis(2, 2));
        assertEquals(TapisRoulant.OCCUPE, usine.logistique.getTapis(3, 3));
    }

    @Test
    void testFournaiseDeGrillagePlacer2() {
        Usine usine = new Usine(6, 6);
        usine.logistique.setTapis(2, 3, TapisRoulant.DROITE_GAUCHE);
        FournaiseDeGrillage fournaise = new FournaiseDeGrillage(2, 3);

        assertThrows(PlacementIncorrectException.class, () -> fournaise.placer(2, 3, usine));
        assertNull(usine.getCase(3, 2).getStation());
        assertNull(usine.getCase(3, 1).getStation());
        assertNull(usine.getCase(2, 2).getStation());
        assertNull(usine.getCase(3, 3).getStation());
        assertEquals(TapisRoulant.DROITE_GAUCHE, usine.logistique.getTapis(2, 3));
        assertEquals(TapisRoulant.VIDE, usine.logistique.getTapis(1, 3));
        assertEquals(TapisRoulant.VIDE, usine.logistique.getTapis(2, 2));
        assertEquals(TapisRoulant.VIDE, usine.logistique.getTapis(3, 3));
    }

    @Test
    void testFournaiseDeGrillagePlacer3() {
        Usine usine = new Usine(6, 6);
        usine.logistique.setTapis(1, 3, TapisRoulant.DROITE_GAUCHE);
        FournaiseDeGrillage fournaise = new FournaiseDeGrillage(2, 3);

        assertThrows(PlacementIncorrectException.class, () -> fournaise.placer(2, 3, usine));
        assertNull(usine.getCase(3, 2).getStation());
        assertNull(usine.getCase(3, 1).getStation());
        assertNull(usine.getCase(2, 2).getStation());
        assertNull(usine.getCase(3, 3).getStation());
        assertEquals(TapisRoulant.VIDE, usine.logistique.getTapis(2, 3));
        assertEquals(TapisRoulant.DROITE_GAUCHE, usine.logistique.getTapis(1, 3));
        assertEquals(TapisRoulant.VIDE, usine.logistique.getTapis(2, 2));
        assertEquals(TapisRoulant.VIDE, usine.logistique.getTapis(3, 3));
    }

    @Test
    void testFournaiseDeGrillagePlacer4() {
        Usine usine = new Usine(6, 6);
        usine.logistique.setTapis(2, 2, TapisRoulant.DROITE_GAUCHE);
        FournaiseDeGrillage fournaise = new FournaiseDeGrillage(2, 3);

        assertThrows(PlacementIncorrectException.class, () -> fournaise.placer(2, 3, usine));
        assertNull(usine.getCase(3, 2).getStation());
        assertNull(usine.getCase(3, 1).getStation());
        assertNull(usine.getCase(2, 2).getStation());
        assertNull(usine.getCase(3, 3).getStation());
        assertEquals(TapisRoulant.VIDE, usine.logistique.getTapis(2, 3));
        assertEquals(TapisRoulant.VIDE, usine.logistique.getTapis(1, 3));
        assertEquals(TapisRoulant.DROITE_GAUCHE, usine.logistique.getTapis(2, 2));
        assertEquals(TapisRoulant.VIDE, usine.logistique.getTapis(3, 3));
    }

    @Test
    void testFournaiseDeGrillagePlacer5() {
        Usine usine = new Usine(6, 6);
        usine.logistique.setTapis(3, 3, TapisRoulant.DROITE_GAUCHE);
        FournaiseDeGrillage fournaise = new FournaiseDeGrillage(2, 3);

        assertThrows(PlacementIncorrectException.class, () -> fournaise.placer(2, 3, usine));
        assertNull(usine.getCase(3, 2).getStation());
        assertNull(usine.getCase(3, 1).getStation());
        assertNull(usine.getCase(2, 2).getStation());
        assertNull(usine.getCase(3, 3).getStation());
        assertEquals(TapisRoulant.VIDE, usine.logistique.getTapis(2, 3));
        assertEquals(TapisRoulant.VIDE, usine.logistique.getTapis(1, 3));
        assertEquals(TapisRoulant.VIDE, usine.logistique.getTapis(2, 2));
        assertEquals(TapisRoulant.DROITE_GAUCHE, usine.logistique.getTapis(3, 3));
    }

    @Test
    void testFournaiseDeCoupellationPlacer1() {
        Usine usine = new Usine(4, 4);
        FournaiseDeCoupellation fournaise = new FournaiseDeCoupellation(1, 1);

        assertDoesNotThrow(() -> fournaise.placer(1, 1, usine));
        assertEquals(fournaise, usine.getCase(1, 1).getStation());
        assertEquals(TapisRoulant.OCCUPE, usine.logistique.getTapis(1, 1));
        assertEquals(TapisRoulant.OCCUPE, usine.logistique.getTapis(1, 0));
    }

    @Test
    void testFournaiseDeCoupellationPlacer2() {
        Usine usine = new Usine(4, 4);
        usine.logistique.setTapis(1, 1, TapisRoulant.HAUT_BAS);
        FournaiseDeCoupellation fournaise = new FournaiseDeCoupellation(1, 1);

        assertThrows(PlacementIncorrectException.class, () -> fournaise.placer(1, 1, usine));
        assertNull(usine.getCase(1, 1).getStation());
        assertNull(usine.getCase(0, 1).getStation());
        assertEquals(TapisRoulant.HAUT_BAS, usine.logistique.getTapis(1, 1));
        assertEquals(TapisRoulant.VIDE, usine.logistique.getTapis(1, 0));
    }

    @Test
    void testFournaiseDeCoupellationPlacer3() {
        Usine usine = new Usine(4, 4);
        usine.logistique.setTapis(1, 0, TapisRoulant.HAUT_BAS);
        FournaiseDeCoupellation fournaise = new FournaiseDeCoupellation(1, 1);

        assertThrows(PlacementIncorrectException.class, () -> fournaise.placer(1, 1, usine));
        assertNull(usine.getCase(1, 1).getStation());
        assertNull(usine.getCase(0, 1).getStation());
        assertEquals(TapisRoulant.VIDE, usine.logistique.getTapis(1, 1));
        assertEquals(TapisRoulant.HAUT_BAS, usine.logistique.getTapis(1, 0));
    }

    @Test
    void testTouraillePlacer1() {
        Usine usine = new Usine(6, 6);
        Touraille touraille = new Touraille(3, 2);

        assertDoesNotThrow(() -> touraille.placer(3, 2, usine));
        assertEquals(touraille, usine.getCase(3, 2).getStation());
        assertEquals(TapisRoulant.OCCUPE, usine.logistique.getTapis(3, 2));
        assertEquals(TapisRoulant.OCCUPE, usine.logistique.getTapis(4, 2));
        assertEquals(TapisRoulant.OCCUPE, usine.logistique.getTapis(3, 3));
        assertEquals(TapisRoulant.OCCUPE, usine.logistique.getTapis(4, 3));
    }

    @Test
    void testTouraillePlacer2() {
        Usine usine = new Usine(6, 6);
        usine.logistique.setTapis(3, 2, TapisRoulant.OCCUPE);
        Touraille touraille = new Touraille(3, 2);

        assertThrows(PlacementIncorrectException.class, () -> touraille.placer(3, 2, usine));
        assertNull(usine.getCase(2, 3).getStation());
        assertNull(usine.getCase(2, 4).getStation());
        assertNull(usine.getCase(3, 3).getStation());
        assertNull(usine.getCase(3, 4).getStation());
        assertEquals(TapisRoulant.OCCUPE, usine.logistique.getTapis(3, 2));
        assertEquals(TapisRoulant.VIDE, usine.logistique.getTapis(4, 2));
        assertEquals(TapisRoulant.VIDE, usine.logistique.getTapis(3, 3));
        assertEquals(TapisRoulant.VIDE, usine.logistique.getTapis(4, 3));
    }

    @Test
    void testTouraillePlacer3() {
        Usine usine = new Usine(6, 6);
        usine.logistique.setTapis(4, 2, TapisRoulant.OCCUPE);
        Touraille touraille = new Touraille(3, 2);

        assertThrows(PlacementIncorrectException.class, () -> touraille.placer(3, 2, usine));
        assertNull(usine.getCase(2, 3).getStation());
        assertNull(usine.getCase(2, 4).getStation());
        assertNull(usine.getCase(3, 3).getStation());
        assertNull(usine.getCase(3, 4).getStation());
        assertEquals(TapisRoulant.VIDE, usine.logistique.getTapis(3, 2));
        assertEquals(TapisRoulant.OCCUPE, usine.logistique.getTapis(4, 2));
        assertEquals(TapisRoulant.VIDE, usine.logistique.getTapis(3, 3));
        assertEquals(TapisRoulant.VIDE, usine.logistique.getTapis(4, 3));
    }

    @Test
    void testTouraillePlacer4() {
        Usine usine = new Usine(6, 6);
        usine.logistique.setTapis(3, 3, TapisRoulant.OCCUPE);
        Touraille touraille = new Touraille(3, 2);

        assertThrows(PlacementIncorrectException.class, () -> touraille.placer(3, 2, usine));
        assertNull(usine.getCase(2, 3).getStation());
        assertNull(usine.getCase(2, 4).getStation());
        assertNull(usine.getCase(3, 3).getStation());
        assertNull(usine.getCase(3, 4).getStation());
        assertEquals(TapisRoulant.VIDE, usine.logistique.getTapis(3, 2));
        assertEquals(TapisRoulant.VIDE, usine.logistique.getTapis(4, 2));
        assertEquals(TapisRoulant.OCCUPE, usine.logistique.getTapis(3, 3));
        assertEquals(TapisRoulant.VIDE, usine.logistique.getTapis(4, 3));
    }

    @Test
    void testTouraillePlacer5() {
        Usine usine = new Usine(6, 6);
        usine.logistique.setTapis(4, 3, TapisRoulant.OCCUPE);
        Touraille touraille = new Touraille(3, 2);

        assertThrows(PlacementIncorrectException.class, () -> touraille.placer(3, 2, usine));
        assertNull(usine.getCase(2, 3).getStation());
        assertNull(usine.getCase(2, 4).getStation());
        assertNull(usine.getCase(3, 3).getStation());
        assertNull(usine.getCase(3, 4).getStation());
        assertEquals(TapisRoulant.VIDE, usine.logistique.getTapis(3, 2));
        assertEquals(TapisRoulant.VIDE, usine.logistique.getTapis(4, 2));
        assertEquals(TapisRoulant.VIDE, usine.logistique.getTapis(3, 3));
        assertEquals(TapisRoulant.OCCUPE, usine.logistique.getTapis(4, 3));
    }

    @Test
    void testVendeurPlacer1() {
        Usine usine = new Usine(3, 3);
        Vendeur vendeur = new Vendeur(1, 1);

        assertDoesNotThrow(() -> vendeur.placer(1, 1, usine));
        assertEquals(vendeur, usine.getCase(1, 1).getStation());
        assertEquals(TapisRoulant.OCCUPE, usine.logistique.getTapis(1, 1));
    }

    @Test
    void testVendeurPlacer2() {
        Usine usine = new Usine(3, 3);
        usine.logistique.setTapis(1, 1, TapisRoulant.DROITE_BAS);
        Vendeur vendeur = new Vendeur(1, 1);

        assertThrows(PlacementIncorrectException.class, () -> vendeur.placer(1, 1, usine));
        assertNull(usine.getCase(1, 1).getStation());
        assertEquals(TapisRoulant.DROITE_BAS, usine.logistique.getTapis(1, 1));
    }
}
package usine;

public enum IdentiteProduit {
    ACANTHITE("Acanthite"),
    CASSITERITE("Cassiterite"),
    CHALCOCITE("Chalcocite"),
    CHARBON("Charbon"),
    COKE("Coke"),
    LINGOT_ARGENT("Lingot d'argent"),
    LINGOT_BRONZE("Lingot de bronze"),
    LINGOT_CUIVRE("Lingot de cuivre"),
    LINGOT_ETAIN("Lingot d'étain"),
    LITHARGE("Litharge"),
    OXYDE_ARGENT("Oxyde d'argent"),
    OXYDE_CUIVRE("Oxyde de cuivre"),
    OXYDE_ETAIN("Oxyde d'étain"),
    POUDRE_ACANTHITE("Poudre d'acanthite"),
    POUDRE_CASSITERITE("Poudre de cassiterite"),
    POUDRE_CHALCOCITE("Poudre de chalcocite"),;

    private String nom;

    IdentiteProduit(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }
}

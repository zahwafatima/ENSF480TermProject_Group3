

public class Membership {
    private boolean isRegistered;
    private List<Promo> promos;
    
    public Membership(boolean isRegistered) {
        this.isRegistered = isRegistered;
        this.promos = new ArrayList<>();
    }

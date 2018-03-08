package Singleton;

public enum Singleton1 {
    INSTANCE;
    private String name;
    private void setName(String name){
        this.name=name;
    }
    @Override
    public String toString() {
        return super.toString();
    }
}

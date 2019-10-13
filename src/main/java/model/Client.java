package model;

public class Client {

    private Long id;
    private String name;
    private String number;
    private int visitCount;

    public Client(String name, String number) {
        this.name = name;
        this.number = number;
        visitCount = 0;
    }

    public Client(Long id, String name, String number, int visitCount) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.visitCount = visitCount;
    }

    public Client(String name, String number, int visitNumber) {
        this.name = name;
        this.number = number;
        this.visitCount = visitNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(int visitCount) {
        this.visitCount = visitCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        if (id != null || !id.equals(client.id)) return false;
        if (name != null || !name.equals(client.name)) return false;
        if (number != null || !number.equals(client.number)) return false;
        if (visitCount != client.visitCount) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + visitCount;
        return result;
    }





    @Override
    public String toString() {
        return "Client {" +
                "id = " + id +
                ", name = '" + name + '\'' +
                ", number = '" + number + '\'' +
                ", visitCount = '" + visitCount + '\'' +
                '}';
    }
}

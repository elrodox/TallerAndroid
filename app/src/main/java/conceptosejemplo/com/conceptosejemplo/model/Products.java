package conceptosejemplo.com.conceptosejemplo.model;



/**
 * Created by Guma on 02-11-2016.
 */

public class Products {

    private int id;
    private String name;
    private int cost;
    private int weight;
    private String type;
    private String season;
    private String origin;
    private double fatIndex;
    private String originCountry;
    private String wholemeal;
    private String unit;
    private String arrivalDate;
    private int duration;

    public Products(int id, String name, int cost, int weight, String type, String season, String origin, double fatIndex, String originCountry, String wholemeal, String unit, String arrivalDate, int duration) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.weight = weight;
        this.type = type;
        this.season = season;
        this.origin = origin;
        this.fatIndex = fatIndex;
        this.originCountry = originCountry;
        this.wholemeal = wholemeal;
        this.unit = unit;
        this.arrivalDate = arrivalDate;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getWeight() {
        return weight;
    }

    public String getType() {
        return type;
    }

    public String getSeason() {
        return season;
    }

    public String getOrigin() {
        return origin;
    }

    public double getFatIndex() {
        return fatIndex;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public String isWholemeal() {
        return wholemeal;
    }

    public String getUnit() {
        return unit;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public int getDuration() {
        return duration;
    }

    public String getWholemeal() {
        return wholemeal;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setFatIndex(double fatIndex) {
        this.fatIndex = fatIndex;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public void setWholemeal(String wholemeal) {
        this.wholemeal = wholemeal;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }



    public void setDuration(int duration) {
        this.duration = duration;
    }
}

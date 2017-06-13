public class FoodItem {

    String foodID;
    int points;


    // Default constructor
    public FoodItem(){
    }

    public FoodItem(int points, String foodID){
        this.foodID = foodID;
        this.points = points;
    }

    public String getFoodID(){
        return foodID;
    }

    public int getPoints(){
        return points;
    }

    public void setFoodID(String foodID){
        this.foodID = foodID;
    }

    public void setPoints(int points){
        this.points = points;
    }
}

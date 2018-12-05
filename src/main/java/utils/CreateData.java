package utils;

import org.joda.time.DateTime;

public class CreateData {
	
	public DateTime createTime() {
		DateTime dateTime = new DateTime();
		dateTime = DateTime.now();
		return dateTime;
	}
	
	public double randomValue() {
		double fee = randomInRange(0.0, 100.0, 2);
		return fee;
	}
	
	public  double randomLng(){
        //make test data lngs three digits for easy differentiation from lats
        double lng = randomInRange(105.0, 107.0, 6);
        //return a negative number if lng is even
        if ((int) Math.round(lng) % 2 == 0) {
            return 0 - lng;
        } else {
            return lng;
        }
    }

    public  double randomLat(){
        //stay away from the poles because they often break software
        return randomInRange(28.0, 29.0, 6);
    }
    
    protected  double randomInRange(double min, double max, int decimalPlaces){
        double unroundedValue = min + Math.random() * (max - min);
        double co = Math.pow(10, decimalPlaces);
        return Math.round(unroundedValue * co) / co;
    }
}

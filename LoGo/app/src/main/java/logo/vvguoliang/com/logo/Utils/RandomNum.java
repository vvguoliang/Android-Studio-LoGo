package logo.vvguoliang.com.logo.Utils;

import java.util.Random;

public class RandomNum {
		
	public static int main(String[] args) {
		 Random rand = new Random();
	     int randomNum = rand.nextInt(99998999)+1000;
	     return randomNum;
	}

}

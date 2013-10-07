package com.example.crystalball;

import java.util.Random;

public class CrystalBall {
	

	public String[] mAnswers = { "It is certain", "It is decidedly so",
			"All signs say YES", "The stars are not aligned",
			"My reply is so", "How about NO!", "Ask again later",
			"Concentrate and ask again", "Don't count on it	" };

	public String getAnAnswer() {

		String answer = "";

		Random rand = new Random(); // Construct a new Random number generator
		int randomNumber = rand.nextInt(mAnswers.length);

		answer = mAnswers[randomNumber];

		return answer;

	}

}

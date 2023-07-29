package com.game.the_gallows_game.servie;

import com.game.the_gallows_game.dict.GameFailedProgress;
import com.game.the_gallows_game.model.Quiz;
import com.game.the_gallows_game.model.User;
import com.game.the_gallows_game.util.QuizUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

	public Quiz generateQuizForUser(User user) {
		Random random = new Random();
		Set<String> setKeys = new HashSet<>(QuizUtil.quizMap.keySet());
		setKeys.removeAll(user.getHistoryQuizQuestions());
		List<String> keys = new ArrayList<>(setKeys);
		String question = keys.get(random.nextInt(keys.size()));
		String rightAnswer = QuizUtil.quizMap.get(question);

		char[] userAnswer = new char[rightAnswer.length()];
		Arrays.fill(userAnswer, '*');

		return Quiz.builder()
				.question(question)
				.userAnswer(String.copyValueOf(userAnswer))
				.rightAnswer(rightAnswer)
				.failedProgress(GameFailedProgress.ZERO)
				.build();
	}

	public boolean validateAttempt(String text) {
		return text != null && text.length() == 1;
	}

	public boolean attemptAndChangeQuiz(Quiz quiz, String symbol) {
		char s = symbol.charAt(0);
		String rightAnswer = quiz.getRightAnswer().toLowerCase();

		for (int i = 0; i < quiz.getUserAnswer().length(); i++) {
			char ua = quiz.getUserAnswer().charAt(i);
			char ra = rightAnswer.charAt(i);
			if (ua == '*' && ra == s) {
				StringBuilder userAnswer = new StringBuilder(quiz.getUserAnswer());
				userAnswer.setCharAt(i, s);
				quiz.setUserAnswer(userAnswer.toString());

				return true;
			}
		}

		quiz.setFailedProgress(GameFailedProgress.getNext(quiz.getFailedProgress()));

		return false;
	}

	public boolean checkWin(Quiz quiz) {
		return quiz.getUserAnswer() != null && quiz.getUserAnswer().equalsIgnoreCase(quiz.getRightAnswer());
	}

	public boolean lose(Quiz quiz) {
		return quiz.getFailedProgress().equals(GameFailedProgress.FIFTH);
	}

}

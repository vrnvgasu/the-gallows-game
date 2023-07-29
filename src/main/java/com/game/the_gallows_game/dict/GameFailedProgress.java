package com.game.the_gallows_game.dict;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GameFailedProgress {

	ZERO(""),
	FIRST("""
 |
 |
 |
 |
 |
/|\\
			"""),
	SECOND("""
  ___
 |
 |
 |
 |
 |
/|\\
			"""),
	THIRD("""
  ___
 |  0
 |
 |
 |
 |
/|\\
			"""),
	FOURTH("""
  ___
 |   0
 | --|--
 |
 |
 |
/|\\
			"""),
	FIFTH("""
  ___
 |   0
 | --|--
 |   |
 |  / \\
 |
/|\\
			""");



	@Getter
	private final String picture;

	public static GameFailedProgress getNext(GameFailedProgress gameFailedProgress) {
		return switch (gameFailedProgress) {
			case ZERO -> FIRST;
			case FIRST -> SECOND;
			case SECOND -> THIRD;
			case THIRD -> FOURTH;
			case FOURTH, FIFTH -> FIFTH;
		};
	}

}

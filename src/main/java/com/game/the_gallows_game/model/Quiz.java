package com.game.the_gallows_game.model;

import com.game.the_gallows_game.dict.GameFailedProgress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {

	private String question;

	private String rightAnswer;

	private String userAnswer;

	@Builder.Default
	private GameFailedProgress failedProgress = GameFailedProgress.ZERO;

}

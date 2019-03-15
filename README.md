# Color-Trap
Color Trap is a game where a user has to identify the color of the main word and match it with the corresponding color name.

![Screenshot of game](https://github.com/Ericnunez/Color-Trap/blob/master/color-trap-screenshot.png?raw=true)

## How to play
The game has a default 15 second timer per gameplay.

- The player has to identify the color of the trap word(in the example the trap word is black but the color is red).
- The player must then click on the corresponding word(in the example the correct choice is "red" in the bottom left corner).

## Notice for changing flashing screen
By default the screen is set to flash every 1/4 second but if this bothers you you need to remove the following code in the startPlay() method.

```java
backgroundTimeline = new Timeline(new KeyFrame(Duration.millis(250), event -> borderPane.setStyle("-fx-background-color: " + flashCol[randomNumber.nextInt(flashCol.length)])));
        backgroundTimeline.setCycleCount(Timeline.INDEFINITE);
        backgroundTimeline.play();
```

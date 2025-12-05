package fr.univ_amu.m1info.board_game_library.graphics.javafx.board;

import fr.univ_amu.m1info.board_game_library.graphics.Shape;
import javafx.collections.ObservableList;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class ShapeFactory {

    public static final double SHAPE_SIZE_RATIO = (3. / 4);

    public static javafx.scene.shape.Shape makeShape(Shape shape, double squareSize){
        return switch (shape) {
            case CIRCLE -> makeCircle(squareSize);
            case SQUARE -> makeSquare(squareSize);
            case DIAMOND -> makeDiamond(squareSize);
            case TRIANGLE -> makeTriangle(squareSize);
            case STAR -> makeStar(squareSize);
        };
    }

    private static javafx.scene.shape.Shape makeStar(double squareSize) {
        Polygon polygon = new Polygon();
        var points = polygon.getPoints();
        for (int i = 0; i < 10; i++) {
            double radius = (i%2 == 0) ? SHAPE_SIZE_RATIO /2 * squareSize : SHAPE_SIZE_RATIO /4 * squareSize;
            double angle = Math.PI * i / 5 + Math.PI / 10;
            points.add(radius * Math.cos(angle));
            points.add(radius * Math.sin(angle));
        }
        return polygon;
    }

    private static javafx.scene.shape.Shape makeTriangle(double squareSize) {
        Polygon polygon = new Polygon();
        ObservableList<Double> points = polygon.getPoints();
        points.addAll(0.0, 0.0);
        points.addAll(SHAPE_SIZE_RATIO * squareSize, 0.);
        points.addAll(SHAPE_SIZE_RATIO / 2 * squareSize, SHAPE_SIZE_RATIO * squareSize);
        return polygon;
    }

    private static Rectangle makeDiamond(double squareSize) {
        Rectangle rectangle = new Rectangle(SHAPE_SIZE_RATIO /Math.sqrt(2) * squareSize,
                SHAPE_SIZE_RATIO /Math.sqrt(2) * squareSize);
        rectangle.getTransforms().add(new Rotate(45, 0, 0));
        return rectangle;
    }

    private static Rectangle makeSquare(double squareSize) {
        return new Rectangle(SHAPE_SIZE_RATIO * squareSize, SHAPE_SIZE_RATIO * squareSize);
    }

    private static Circle makeCircle(double squareSize) {
        return new Circle((3. / 8) * squareSize);
    }
}

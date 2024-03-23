package NP.NPKolokvium1.zad1;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Square {
    int size;

    public Square(int size) {
        this.size = size;
    }

    int perimeter() {
        return 4 * size;
    }
}

class Canvas implements Comparable<Canvas> {
    String id;
    List<Square> squares;

    public Canvas(String id, List<Square> squares) {
        this.id = id;
        this.squares = squares;
    }

    public static Canvas create(String line) {
        String[] parts = line.split("\\s+");
        String id = parts[0];
        List<Square> squares = new ArrayList<>();
//        for (int i = 1; i < parts.length; i++) {
//            squares.add(new Square(Integer.parseInt(parts[i])));
//        }

        squares = Arrays.stream(parts)
                .skip(1)
                .map(sizeStr -> new Square(Integer.parseInt(sizeStr)))
                .collect(Collectors.toList());
        return new Canvas(id, squares);
    }

    int totalSquares() {
        return squares.size();
    }

    int totalPerimeter() {
        return squares.stream()
                .mapToInt(square -> square.perimeter())
                .sum();
    }

    @Override
    public int compareTo(Canvas o) {
        return Integer.compare(this.totalPerimeter(), o.totalPerimeter());
    }

    @Override
    public String toString() {
        return String.format("%s %d %d", id, totalSquares(), totalPerimeter());
    }
}

class ShapesApplication {

    List<Canvas> canvases;

    ShapesApplication() {
        canvases = new ArrayList<>();
    }

    int readCanvases(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        canvases = br.lines()
                .map(line -> Canvas.create(line))
                .collect(Collectors.toList());
        br.close();
        return canvases.stream()
                .mapToInt(canvas -> canvas.totalSquares())
                .sum();
    }

    void printLargestCanvasTo(OutputStream outputStream) {
        PrintWriter pw = new PrintWriter(outputStream);


        Canvas max = canvases.stream()
                .max(Comparator.naturalOrder())
                .get();

        pw.println(max);
        pw.flush();
    }
}

public class Shapes1Test {

    public static void main(String[] args) {
        ShapesApplication shapesApplication = new ShapesApplication();
        System.out.println("===READING SQUARES FROM INPUT STREAM===");
        try {
            System.out.println(shapesApplication.readCanvases(System.in));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("===PRINTING LARGEST CANVAS TO OUTPUT STREAM===");
        shapesApplication.printLargestCanvasTo(System.out);

    }
}
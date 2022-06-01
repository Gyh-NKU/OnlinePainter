package PaintBoard;

import java.awt.*;
import java.io.Serializable;

public interface DrawImage extends Serializable {
    void draw(Graphics g);
}

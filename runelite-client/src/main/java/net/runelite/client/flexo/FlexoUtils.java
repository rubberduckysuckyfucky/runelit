package net.runelite.client.flexo;

import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.plugins.aaaflexo.FlexoOverlay;

import javax.inject.Inject;
import java.awt.*;

public class FlexoUtils {

    public static double scale;
    @Inject
    private static Client client;

    public static Rectangle getItemArea(WidgetItem item, int padding) {
        Rectangle clickArea = item.getCanvasBounds();
        FlexoOverlay.clickArea = clickArea;
        return clickArea;
    }

    public static net.runelite.api.Point getClickPoint(Rectangle rect)
    {
        if (Flexo.isStretched)
        {
            int rand = (Math.random() <= 0.5) ? 1 : 2;
            int x = (int) (rect.getX() + rand + rect.getWidth() / 2);
            int y = (int) (rect.getY() + rand + rect.getHeight() / 2);
            double tScale = 1 + (scale / 100);
            return new net.runelite.api.Point((int) (x * tScale), (int) (y * tScale));
        }
        else
        {
            int rand = (Math.random() <= 0.5) ? 1 : 2;
            int x = (int) (rect.getX() + rand + rect.getWidth() / 2);
            int y = (int) (rect.getY() + rand + rect.getHeight() / 2);
            return new Point(x, y);
        }
    }
}

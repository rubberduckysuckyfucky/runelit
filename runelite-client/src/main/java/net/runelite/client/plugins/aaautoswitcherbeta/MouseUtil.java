package net.runelite.client.plugins.aaautoswitcherbeta;

import java.awt.Rectangle;
import java.awt.event.InputEvent;
import javax.annotation.Nullable;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.api.Query;
import net.runelite.api.queries.InventoryWidgetItemQuery;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.flexo.Flexo;
import net.runelite.client.flexo.FlexoUtils;
import net.runelite.client.ui.ClientUI;
import net.runelite.client.util.QueryRunner;

public class MouseUtil
{

	private Client client;

	@Inject
	private AutoSwitcherPlugin plugin;
	private Point clickPoint = new Point(0, 0);
	@Inject
	private QueryRunner queryRunner = new QueryRunner();

	@Inject
	public MouseUtil(@Nullable Client client, AutoSwitcherPlugin plugin)
	{
		this.client = client;
		this.plugin = plugin;
	}

	private Point getClickPoint(Rectangle rect)
	{
		//if (config.stretchedState())
		//TODO:Theres probably a better implementation than this.
		int rand = (Math.random() <= 0.5) ? 1 : 2;
		int x = (int) (rect.getX() + rand + rect.getWidth() / 2);
		int y = (int) (rect.getY() + rand + rect.getHeight() / 2);
		//TODO:int 75 Should grab config.scalingFactor().
		double scale = 1 + ((double) 75 / 100);
		return new Point((int) (x * scale), (int) (y * scale));
/*		else
		{
			int rand = (Math.random() <= 0.5) ? 1 : 2;
			int x = (int) (rect.getX() + rand + rect.getWidth() / 2);
			int y = (int) (rect.getY() + rand + rect.getHeight() / 2);
			return new Point(x, y);
		}*/
	}


	public void doClick(int id)
	{
		Flexo bot = null;
		try
		{
			bot = new Flexo();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		final Query query = new InventoryWidgetItemQuery();

		final WidgetItem[] widgetItems = (WidgetItem[]) query.result(client);

		for (final WidgetItem item : widgetItems)
		{
			final String group = plugin.getTag(item.getId());
			if (item.getId() == id)
			{
				if (group != null)
				{
					switch (group)
					{
						case "Group 1":
						case "Group 2":
						case "Group 3":
						case "Group 4":
              Rectangle clickArea = FlexoUtils.getItemArea(item, 3);
							if (clickArea.getY() <= 0)
							{
								//TODO: sloppy null check, but it works.
								clickPoint = new Point(0, 0);
							}
							clickPoint = getClickPoint(clickArea);
							System.out.println(clickPoint);
							if (clickPoint.getX() <= 0)
							{
								return;
							}
							int clientX = ClientUI.frame.getX();
							int clientY = ClientUI.frame.getY();
							bot.mouseMove(clickPoint.getX(), clickPoint.getY());
							bot.mousePressAndRelease(1);
					}

				}
			}
		}
	}
}



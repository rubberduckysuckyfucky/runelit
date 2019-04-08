package net.runelite.client.plugins.aaautoswitcherbeta;

import com.google.inject.Provides;
import java.awt.Rectangle;
import javax.annotation.Nullable;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.api.Query;
import net.runelite.api.queries.InventoryWidgetItemQuery;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.flexo.Flexo;
import net.runelite.client.flexo.FlexoUtils;
import net.runelite.client.plugins.stretchedmode.StretchedModeConfig;
import net.runelite.client.util.QueryRunner;

public class MouseUtil
{

	private Client client;

	@Inject
	private AutoSwitcherPlugin plugin;

	@Inject
	private AutoSwitcherConfig config;

	@Inject
	private StretchedModeConfig smConfig;

	@Inject
	private QueryRunner queryRunner = new QueryRunner();
	private Point clickPoint = new Point(0, 0);


	@Provides
	AutoSwitcherConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(AutoSwitcherConfig.class);
	}

	@Provides
	StretchedModeConfig getConfig2(ConfigManager configManager)
	{
		return configManager.getConfig(StretchedModeConfig.class);
	}

	@Inject
	public MouseUtil(@Nullable Client client, AutoSwitcherConfig config, StretchedModeConfig smConfig, AutoSwitcherPlugin plugin)
	{
		this.client = client;
		this.config = config;
		this.smConfig = smConfig;
		this.plugin = plugin;
	}

	public void doClick(int id)
	{
		double scale = 1 + ((double) smConfig.scalingFactor() / 100);
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
								clickPoint = new Point(0, 0);
							}
							clickPoint = FlexoUtils.getClickPoint(clickArea);
							System.out.println(clickPoint);
							if (clickPoint.getX() <= 0)
							{
								return;
							}
							bot.mouseMove(clickPoint.getX(), clickPoint.getY());
							bot.mousePressAndRelease(1);
					}

				}
			}
		}
	}
}



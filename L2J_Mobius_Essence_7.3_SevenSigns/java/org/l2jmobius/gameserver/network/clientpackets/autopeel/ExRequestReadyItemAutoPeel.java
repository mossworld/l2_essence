/*
 * This file is part of the L2J Mobius project.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.l2jmobius.gameserver.network.clientpackets.autopeel;

import org.l2jmobius.commons.network.ReadablePacket;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.actor.request.AutoPeelRequest;
import org.l2jmobius.gameserver.model.item.instance.Item;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.clientpackets.ClientPacket;
import org.l2jmobius.gameserver.network.serverpackets.autopeel.ExReadyItemAutoPeel;

/**
 * @author Mobius
 */
public class ExRequestReadyItemAutoPeel implements ClientPacket
{
	private int _itemObjectId;
	
	@Override
	public void read(ReadablePacket packet)
	{
		_itemObjectId = packet.readInt();
	}
	
	@Override
	public void run(GameClient client)
	{
		final Player player = client.getPlayer();
		if (player == null)
		{
			return;
		}
		
		final Item item = player.getInventory().getItemByObjectId(_itemObjectId);
		if ((item == null) || !item.isEtcItem() || (item.getEtcItem().getExtractableItems() == null) || item.getEtcItem().getExtractableItems().isEmpty())
		{
			player.sendPacket(new ExReadyItemAutoPeel(false, _itemObjectId));
			return;
		}
		
		player.addRequest(new AutoPeelRequest(player, item));
		player.sendPacket(new ExReadyItemAutoPeel(true, _itemObjectId));
	}
}
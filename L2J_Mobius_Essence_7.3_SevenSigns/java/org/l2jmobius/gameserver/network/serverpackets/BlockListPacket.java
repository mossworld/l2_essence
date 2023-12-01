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
package org.l2jmobius.gameserver.network.serverpackets;

import java.util.Set;

import org.l2jmobius.gameserver.data.sql.CharInfoTable;
import org.l2jmobius.gameserver.network.ServerPackets;

/**
 * @author Sdw
 */
public class BlockListPacket extends ServerPacket
{
	private final Set<Integer> _playerIds;
	
	public BlockListPacket(Set<Integer> playerIds)
	{
		_playerIds = playerIds;
	}
	
	@Override
	public void write()
	{
		ServerPackets.BLOCK_LIST.writeId(this);
		writeInt(_playerIds.size());
		for (int playerId : _playerIds)
		{
			writeString(CharInfoTable.getInstance().getNameById(playerId));
			writeString(""); // memo ?
		}
	}
}

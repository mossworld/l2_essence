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
package org.l2jmobius.gameserver.network.serverpackets.ranking;

import java.util.Collection;

import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.holders.RankingHistoryDataHolder;
import org.l2jmobius.gameserver.network.ServerPackets;
import org.l2jmobius.gameserver.network.serverpackets.ServerPacket;

/**
 * @author Serenitty, Brado, Enryu
 */
public class ExRankingCharHistory extends ServerPacket
{
	private final Player _player;
	private final Collection<RankingHistoryDataHolder> _history;
	
	public ExRankingCharHistory(Player player)
	{
		_player = player;
		_history = _player.getRankingHistoryData();
	}
	
	@Override
	public void write()
	{
		ServerPackets.EX_RANKING_CHAR_HISTORY.writeId(this);
		writeInt(_history.size());
		if (_history.isEmpty())
		{
			writeByte(0);
			writeByte(0);
			writeByte(0);
		}
		else
		{
			for (RankingHistoryDataHolder rankingData : _history)
			{
				writeInt((int) (rankingData.getDay()));
				writeInt(rankingData.getRank());
				writeLong(rankingData.getExp());
			}
		}
	}
}

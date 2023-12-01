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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import org.l2jmobius.gameserver.model.ItemInfo;
import org.l2jmobius.gameserver.model.item.instance.Item;

/**
 * @author UnAfraid
 */
public abstract class AbstractInventoryUpdate extends AbstractItemPacket
{
	private final Map<Integer, ItemInfo> _items = new ConcurrentSkipListMap<>();
	
	public AbstractInventoryUpdate()
	{
	}
	
	public AbstractInventoryUpdate(Item item)
	{
		addItem(item);
	}
	
	public AbstractInventoryUpdate(List<ItemInfo> items)
	{
		for (ItemInfo item : items)
		{
			_items.put(item.getObjectId(), item);
		}
	}
	
	public void addItem(Item item)
	{
		_items.put(item.getObjectId(), new ItemInfo(item));
	}
	
	public void addNewItem(Item item)
	{
		_items.put(item.getObjectId(), new ItemInfo(item, 1));
	}
	
	public void addModifiedItem(Item item)
	{
		_items.put(item.getObjectId(), new ItemInfo(item, 2));
	}
	
	public void addRemovedItem(Item item)
	{
		_items.put(item.getObjectId(), new ItemInfo(item, 3));
	}
	
	public void addItems(Collection<Item> items)
	{
		for (Item item : items)
		{
			_items.put(item.getObjectId(), new ItemInfo(item));
		}
	}
	
	public Collection<ItemInfo> getItems()
	{
		return _items.values();
	}
	
	protected void writeItems()
	{
		writeByte(0); // 140
		writeInt(0); // 140
		writeInt(_items.size()); // 140
		for (ItemInfo item : _items.values())
		{
			writeShort(item.getChange()); // Update type : 01-add, 02-modify, 03-remove
			writeItem(item);
		}
	}
}

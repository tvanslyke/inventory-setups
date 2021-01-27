/*
 * Copyright (c) 2019, dillydill123 <https://github.com/dillydill123>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package inventorysetups.ui;

import inventorysetups.InventorySetupsPlugin;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

// Implementation of a cycle button which provides a way to cycle through multiple states in one button (JLabel)
// When clicked, the cycle button will properly set the icons, tooltips, and execute the provided runnable for any necessary logic
public class InventorySetupsCycleButton<T> extends JLabel
{
	private final InventorySetupsPlugin plugin;
	private final ArrayList<T> states;
	private final ArrayList<ImageIcon> icons;
	private final ArrayList<ImageIcon> hoverIcons;
	private final ArrayList<String> tooltips;
	private final Runnable runnable;
	private int currentIndex;

	InventorySetupsCycleButton(final InventorySetupsPlugin plugin, final ArrayList<T> states, final ArrayList<ImageIcon> icons, final ArrayList<ImageIcon> hoverIcons, final ArrayList<String> tooltips, final Runnable runnable)
	{
		super();
		this.plugin = plugin;
		this.states = states;
		this.icons = icons;
		this.hoverIcons = hoverIcons;
		this.runnable = runnable;
		this.tooltips = tooltips;
		this.currentIndex = 0;

		// sizes must be equal
		assert this.states.size() == this.icons.size();
		assert this.icons.size() == this.hoverIcons.size();
		assert this.hoverIcons.size() == this.tooltips.size();

		addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent mouseEvent)
			{
				if (SwingUtilities.isLeftMouseButton(mouseEvent))
				{
					currentIndex = (currentIndex + 1) % states.size();
					runnable.run();
					setToolTipText(tooltips.get(currentIndex));
					setIcon(icons.get(currentIndex));
					plugin.updateConfig();
				}
			}

			@Override
			public void mouseEntered(MouseEvent mouseEvent)
			{
				setIcon(hoverIcons.get(currentIndex));
			}

			@Override
			public void mouseExited(MouseEvent mouseEvent)
			{
				setIcon(icons.get(currentIndex));
			}
		});
	}

	public void setCurrentState(final T state)
	{
		for (int i = 0; i < this.states.size(); i++)
		{
			if (this.states.get(i) == state)
			{
				this.currentIndex = i;
				break;
			}
		}
		setIcon(icons.get(currentIndex));
		setToolTipText(tooltips.get(currentIndex));
	}

	public T getCurrentState()
	{
		return states.get(this.currentIndex);
	}
}

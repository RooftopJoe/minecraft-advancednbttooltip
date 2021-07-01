/*	MIT License
	
	Copyright (c) 2020-present b0iizz
	
	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:
	
	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.
	
	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	SOFTWARE.
*/
package me.b0iizz.advancednbttooltip.api.impl.builtin;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import me.b0iizz.advancednbttooltip.api.JsonTooltips.Optional;
import me.b0iizz.advancednbttooltip.api.JsonTooltips.Required;
import me.b0iizz.advancednbttooltip.api.JsonTooltips.TooltipIdentifier;
import me.b0iizz.advancednbttooltip.api.TooltipFactory;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

/**
 * A factory which can create {@link Formatting} on other factories
 * @author B0IIZZ
 */
@TooltipIdentifier("formatted")
public class FormattedFactory implements TooltipFactory {

	/**
	 * The factory to be formatted
	 */
	@Required
	public TooltipFactory text = null;
	
	/**
	 * Whether the the factory should be formatted bold.
	 */
	@Optional
	public boolean bold = false;
	/**
	 * Whether the the factory should be formatted italic.
	 */
	@Optional
	public boolean italic = false;
	/**
	 * Whether the the factory should be formatted strikethrough.
	 */
	@Optional
	public boolean strikethrough = false;
	/**
	 * Whether the the factory should be formatted underline.
	 */
	@Optional
	public boolean underline = false;
	/**
	 * Whether the the factory should be formatted obfuscated.
	 */
	@Optional
	public boolean obfuscated = false;
	/**
	 * If and how the factory should be colored.
	 */
	@Optional("color")
	public String colorName = "";
	
	@Override
	public List<Text> getTooltipText(Item item, NbtCompound tag, TooltipContext context) {
		ArrayList<Formatting> formattings = new ArrayList<>();
		
		if (!colorName.isEmpty() && Formatting.byName(colorName) != null && Formatting.byName(colorName).isColor())
			formattings.add(Formatting.byName(colorName));
		if (bold)
			formattings.add(Formatting.BOLD);
		if (italic)
			formattings.add(Formatting.ITALIC);
		if (strikethrough)
			formattings.add(Formatting.STRIKETHROUGH);
		if (underline)
			formattings.add(Formatting.UNDERLINE);
		if (obfuscated)
			formattings.add(Formatting.OBFUSCATED);
		
		final Formatting[] formatting = formattings.toArray(Formatting[]::new);
		
		return text.getTooltipText(item, tag, context).stream().map(line -> line.shallowCopy().formatted(formatting)).collect(Collectors.toList());
	}

}

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
package me.b0iizz.advancednbttooltip.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvType;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.text.Text;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableText;

import me.b0iizz.advancednbttooltip.config.ConfigManager;

@Mixin(Enchantment.class)
@Environment(EnvType.CLIENT)
public abstract class EnchantmentMixin {

	@Shadow
	public abstract int getMaxLevel();

	@Inject(method = "Lnet/minecraft/enchantment/Enchantment;getName(I)Lnet/minecraft/text/Text;", at = @At(value = "TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
	private void advancednbttooltip$appendMaxEnchantmentLevel(int level, CallbackInfoReturnable<Text> info, MutableText enchantmentName) { 
		if (ConfigManager.isShowMaxEnchantmentLevel() && (this.getMaxLevel() > 1)) {
			enchantmentName.append("/").append(new TranslatableText("enchantment.level." + this.getMaxLevel()));
		}
	}
}

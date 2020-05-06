package mod.elite_puddle.minecraftedu.init;

import com.google.common.base.Supplier;

import mod.elite_puddle.minecraftedu.util.Reference;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

//creates a new item group in the creative menu for mod elements
public class ModItemGroups {
	
	public static final ItemGroup MOD_ITEM_GROUP = 
	new ModItemGroup(Reference.MOD_ID,() -> new ItemStack(ModItems.EXAMPLE_ITEM));
	
	public static class ModItemGroup extends ItemGroup{
		
		private final Supplier<ItemStack> iconSupplier;
		
		public ModItemGroup(final String name,
		final Supplier<ItemStack> iconSupplier) {
			
			super(name);
			this.iconSupplier = iconSupplier;
			
		}
		
		@Override
		public ItemStack createIcon() {
			return iconSupplier.get();
		}
	}
}

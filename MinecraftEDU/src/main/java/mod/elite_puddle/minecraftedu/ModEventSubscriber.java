package mod.elite_puddle.minecraftedu;

import mod.elite_puddle.minecraftedu.init.ModItemGroups;
import mod.elite_puddle.minecraftedu.init.ModItems;
import mod.elite_puddle.minecraftedu.objects.blocks.Not_Block;
import mod.elite_puddle.minecraftedu.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.IForgeRegistryEntry;


//creates the new items and blocks for minecraft
@EventBusSubscriber(modid = Reference.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventSubscriber {
	
	
	//create a new item in minecraft
	@SubscribeEvent
	public static void onRegisterItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(
		setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP))
		, "example_item"));
		
	}
	
	//create a new block in minecraft
	@SubscribeEvent
	public static void onRegisterBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(
			setup(new Not_Block(Block.Properties.create(Material.ROCK)
			.hardnessAndResistance(3.0F,3.0F).sound(SoundType.STONE))
			, "not_block"));
	}	
	

	
	@SubscribeEvent
	public static void onRegisterBlockItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(
			setup(new BlockItem(ModItems.NOT_BLOCK, new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP))
			, "not_block")	
		);	
	}
	
	//creates a registry in minecraft to uniquely identify mod elements
	public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, 
	final String name){
		return setup(entry, new ResourceLocation(Reference.MOD_ID, name));
	}
	
	public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, 
	final ResourceLocation registryName) {
		entry.setRegistryName(registryName);
		return entry;
	}
}

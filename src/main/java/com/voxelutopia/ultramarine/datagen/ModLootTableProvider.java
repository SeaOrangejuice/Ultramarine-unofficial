package com.voxelutopia.ultramarine.datagen;

import com.voxelutopia.ultramarine.data.BlockRegistry;
import com.voxelutopia.ultramarine.data.ItemRegistry;
import com.voxelutopia.ultramarine.world.block.BaseBlockProperty;
import com.voxelutopia.ultramarine.world.block.BaseBlockPropertyHolder;
import com.voxelutopia.ultramarine.world.block.BaseWall;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.OreBlock;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class ModLootTableProvider extends BaseLootTableProvider {

    public ModLootTableProvider(DataGenerator pGenerator) {
        super(pGenerator);
    }

    private static final List<RegistryObject<Block>> NON_SIMPLE_BLOCKS = new ArrayList<>();

    static {
        BlockRegistry.BLOCKS.getEntries().stream()
                .filter(blockRegistryObject -> {
                    var block = blockRegistryObject.get();
                    if (block instanceof OreBlock){
                        return true;
                    }
                    if (block instanceof BaseBlockPropertyHolder baseBlock){
                        return baseBlock.getProperty().getMaterial() == BaseBlockProperty.BlockMaterial.PORCELAIN;
                    }
                    else return false;
                })
                .forEach(NON_SIMPLE_BLOCKS::add);
    }
    @Override
    protected void addTables() {
        BlockRegistry.BLOCKS.getEntries().stream()
                .filter(blockRegistryObject -> !NON_SIMPLE_BLOCKS.contains(blockRegistryObject))
                .forEach(this::simple);
        ore(BlockRegistry.JADE_ORE, ItemRegistry.JADE);

        porcelain(BlockRegistry.BLUE_AND_WHITE_PORCELAIN_VASE, ItemRegistry.BLUE_AND_WHITE_PORCELAIN_PIECE, ItemRegistry.BLUE_AND_WHITE_PORCELAIN_SHARDS);
        porcelain(BlockRegistry.BIG_BLUE_AND_WHITE_PORCELAIN_VASE, ItemRegistry.BLUE_AND_WHITE_PORCELAIN_PIECE, ItemRegistry.BLUE_AND_WHITE_PORCELAIN_SHARDS);
        //lootTables.put(BlockRegistry.JADE_ORE.get(), createOreTable(BlockRegistry.JADE_ORE.getId().getPath(), BlockRegistry.JADE_ORE.get(), ItemRegistry.JADE.get()));
    }

    void simple(RegistryObject<? extends Block> block) {
        lootTables.put(block.get(), createSimpleTable(block.getId().getPath(), block.get()));
    }

    void ore(RegistryObject<? extends Block> block, RegistryObject<? extends Item> item){
        lootTables.put(block.get(), createOreDrop(block.getId().getPath(), block.get(), item.get()));
    }

    void porcelain(RegistryObject<? extends Block> block, RegistryObject<? extends Item> piece, RegistryObject<? extends Item> shards){
        lootTables.put(block.get(), createPorcelainDrop(block.getId().getPath(), block.get(), piece.get(), shards.get()));
    }


}
